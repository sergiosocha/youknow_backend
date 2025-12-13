package com.yuno.youknow.logic;

import com.yuno.youknow.controller.dto.EventoPagoCreateDto;
import com.yuno.youknow.db.orm.EventoPago;
import com.yuno.youknow.db.repository.EventoPagoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class InputService {

    private final EventoPagoRepository repo;

    public InputService(EventoPagoRepository repo) {
        this.repo = repo;
    }

    @Transactional
    public void ingest(List<EventoPagoCreateDto> dtos) {
        List<EventoPago> entities = dtos.stream()
                .map(this::toEntity)
                .toList();

        repo.saveAll(entities);
    }

    private EventoPago toEntity(EventoPagoCreateDto d) {
        EventoPago e = new EventoPago(); // usa NoArgsConstructor

        e.setEventId(d.eventId());
        e.setTimestamp(d.timestamp());
        e.setMerchantId(d.merchantId());
        e.setMerchantName(d.merchantName());
        e.setCountryCode(d.countryCode());
        e.setProvider(d.provider());
        e.setPayment_method(d.paymentMethod());
        e.setStatus(d.status());

        e.setErrorType(d.errorType());
        e.setLatencyMs(d.latencyMs());

        e.setAmount(d.amount());
        e.setCurrency(d.currency());

        e.setIncidentTag(d.incidentTag());
        e.setImpactLevel(d.impactLevel());
        e.setSuggestedActionType(d.suggestedActionType());

        return e;
    }
}
