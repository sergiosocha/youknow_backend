package com.yuno.youknow.logic;

import com.yuno.youknow.controller.dto.eventoDTO;
import com.yuno.youknow.controller.dto.FiltroRespuestaDTO;
import com.yuno.youknow.db.orm.EventoPago;
import com.yuno.youknow.db.repository.EventoPagoRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AnaliticaService {

    private final EventoPagoRepository repo;

    public AnaliticaService(EventoPagoRepository repo) {
        this.repo = repo;
    }

    public FiltroRespuestaDTO getOverview(LocalDateTime from, LocalDateTime to) {
        List<EventoPago> events = repo.findByTimestampBetween(from, to);

        long total = events.size();
        long success = countStatus(events, "success");
        long failed = countStatus(events, "failed");

        double conversion = total == 0 ? 0.0 : (double) success / total;
        double errorRate = total == 0 ? 0.0 : (double) failed / total;

        Double avgLatency = avgLatency(events);

        BigDecimal failedAmount = sumAmount(
                events.stream().filter(e -> isStatus(e, "failed")).toList()
        );

        List<eventoDTO> issues = buildIssues(events);
        // para overview muestro pocos (hackatón)
        List<eventoDTO> topIssues = issues.stream().limit(5).toList();

        return new FiltroRespuestaDTO(
                from, to,
                total, success, failed,
                conversion, errorRate,
                avgLatency,
                failedAmount,
                topIssues.size(),
                topIssues
        );
    }

    public List<eventoDTO> getIssues(LocalDateTime from, LocalDateTime to) {
        return buildIssues(repo.findByTimestampBetween(from, to));
    }

    private List<eventoDTO> buildIssues(List<EventoPago> events) {
       //Agrupamos si tenemos algun Incidente si la transaccion se realizó bien, no hay porque agruparla
        Map<String, List<EventoPago>> groups = events.stream()
                .filter(e -> e.getIncidentTag() != null && !e.getIncidentTag().isBlank())
                .collect(Collectors.groupingBy(this::groupKey));

        List<eventoDTO> issues = new ArrayList<>();

        for (List<EventoPago> group : groups.values()) {
            group.sort(Comparator.comparing(EventoPago::getTimestamp));

            EventoPago first = group.get(0);
            EventoPago last = group.get(group.size() - 1);

            long total = group.size();
            long failed = countStatus(group, "failed");
            double errRate = total == 0 ? 0.0 : (double) failed / total;

            Double avgLat = avgLatency(group);

            String mainError = mostCommon(group.stream()
                    .map(EventoPago::getErrorType)
                    .filter(Objects::nonNull)
                    .toList());

            BigDecimal failedAmount = sumAmount(
                    group.stream().filter(e -> isStatus(e, "failed")).toList()
            );


            String title = String.format("%s %s (%s)",
                    safe(first.getProvider()), safe(first.getCountryCode()), safe(first.getPayment_method())
            );

            String description = String.format(
                    "%s tiene %d eventos con el tag '%s'. Fallidos: %d (%.0f%%). Error principal: %s. Acción sugerida: %s.",
                    safe(first.getMerchantName()),
                    total,
                    safe(first.getIncidentTag()),
                    failed,
                    errRate * 100,
                    mainError == null ? "N/A" : mainError,
                    safe(first.getSuggestedActionType())
            );

            issues.add(new eventoDTO(
                    first.getIncidentTag(),

                    first.getMerchantId(),
                    first.getMerchantName(),
                    first.getCountryCode(),
                    first.getProvider(),
                    first.getPayment_method(),

                    first.getImpactLevel(),
                    first.getSuggestedActionType(),

                    first.getTimestamp(),
                    last.getTimestamp(),

                    total,
                    failed,
                    errRate,
                    avgLat,

                    mainError,
                    failedAmount,

                    title,
                    description
            ));
        }

        //La idea es organizar según la prioridad o lo critico del incidente
        issues.sort(Comparator
                .comparing((eventoDTO i) -> severityRank(i.impactLevel()))
                .reversed()
                .thenComparing(eventoDTO::failedEvents, Comparator.reverseOrder()));

        return issues;
    }

    private String groupKey(EventoPago e) {
        // key por entidad + tag
        return String.join("|",
                safe(e.getIncidentTag()),
                safe(e.getMerchantId()),
                safe(e.getCountryCode()),
                safe(e.getProvider()),
                safe(e.getPayment_method())
        );
    }

    private int severityRank(String level) {
        if (level == null) return 0;
        return switch (level.toLowerCase()) {
            case "high" -> 3;
            case "medium" -> 2;
            case "low" -> 1;
            default -> 0;
        };
    }

    private boolean isStatus(EventoPago e, String s) {
        return e.getStatus() != null && e.getStatus().equalsIgnoreCase(s);
    }

    private long countStatus(List<EventoPago> events, String status) {
        return events.stream().filter(e -> isStatus(e, status)).count();
    }

    private Double avgLatency(List<EventoPago> events) {
        List<Integer> values = events.stream()
                .map(EventoPago::getLatencyMs)
                .filter(Objects::nonNull)
                .toList();
        if (values.isEmpty()) return null;
        return values.stream().mapToInt(v -> v).average().orElse(0);
    }

    private BigDecimal sumAmount(List<EventoPago> events) {
        BigDecimal sum = BigDecimal.ZERO;
        boolean any = false;
        for (EventoPago e : events) {
            if (e.getAmount() != null) {
                sum = sum.add(e.getAmount());
                any = true;
            }
        }
        return any ? sum : null;
    }

    private String mostCommon(List<String> items) {
        if (items == null || items.isEmpty()) return null;
        Map<String, Long> freq = items.stream()
                .collect(Collectors.groupingBy(x -> x, Collectors.counting()));
        return freq.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
    }

    private String safe(String s) {
        return s == null ? "" : s.trim();
    }
}
