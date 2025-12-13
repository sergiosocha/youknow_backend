package com.yuno.youknow.controller.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record eventoDTO(
        String incidentTag,

        String merchantId,
        String merchantName,
        String countryCode,
        String provider,
        String paymentMethod,

        String impactLevel,
        String suggestedActionType,

        LocalDateTime firstSeen,
        LocalDateTime lastSeen,

        long totalEvents,
        long failedEvents,
        double errorRate,
        Double avgLatencyMs,

        String mainErrorType,
        BigDecimal failedAmount,

        String title,
        String description

) {
}
