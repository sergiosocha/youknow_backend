package com.yuno.youknow.controller.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public record FiltroRespuestaDTO(
        LocalDateTime from,
        LocalDateTime to,

        long totalEvents,
        long approvedEvents,
        long failedEvents,

        double conversion,
        double errorRate,

        Double avgLatencyMs,
        BigDecimal failedAmount,

        int activeIssuesCount,
        List<eventoDTO> activeIssues,

        Map<String, Long> errorCategoryCounts
) {}
