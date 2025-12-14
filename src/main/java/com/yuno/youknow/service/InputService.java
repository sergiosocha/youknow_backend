package com.yuno.youknow.service;

import com.yuno.youknow.controller.dto.EventoPagoCreateDto;
import com.yuno.youknow.db.orm.ErrorCategory;
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

    private ErrorCategory resolveCategory(String status, String errorType) {

        if (status != null && status.equalsIgnoreCase("APPROVED")) {
            return null;
        }


        if (errorType == null || errorType.isBlank()) {
            return null;
        }

        return switch (errorType) {

            case "MISSING_REQUIRED_FIELDS",
                 "INVALID_AMOUNT",
                 "INVALID_BENEFICIARY_DATA",
                 "INVALID_BANK_ACCOUNT",
                 "INSUFFICIENT_BALANCE",
                 "ACCOUNT_CLOSED",
                 "ACCOUNT_BLOCKED",
                 "AUTHORIZATION_REQUIRED",
                 "AUTHORIZATION_EXPIRED"
                    -> ErrorCategory.USER;


            case "INVALID_CURRENCY",
                 "PAYOUT_NOT_ENABLED",
                 "PAYOUT_LIMIT_EXCEEDED",
                 "DAILY_PAYOUT_LIMIT"
                    -> ErrorCategory.MERCHANT;


            default -> ErrorCategory.PROVIDER;
        };
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

        if (d.status() != null && d.status().equalsIgnoreCase("APPROVED")) {
            e.setErrorType(null);
            e.setErrorCategory(null);
        } else {
            e.setErrorType(d.errorType());
            e.setErrorCategory(resolveCategory(d.status(), d.errorType()));
        }
        e.setLatencyMs(d.latencyMs());

        e.setAmount(d.amount());
        e.setCurrency(d.currency());

        e.setIncidentTag(d.incidentTag());
        e.setImpactLevel(d.impactLevel());
        e.setSuggestedActionType(d.suggestedActionType());

        return e;
    }
}
