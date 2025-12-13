package com.yuno.youknow.controller.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record EventoPagoCreateDto(
        String eventId,
        LocalDateTime timestamp,
        String merchantId,
        String merchantName,
        String countryCode,
        String provider,
        String paymentMethod,
        String status,
        String errorType,
        Integer latencyMs,
        BigDecimal amount,
        String currency,
        String incidentTag,
        String impactLevel,
        String suggestedActionType
) {}
