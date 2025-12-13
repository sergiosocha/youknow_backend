package com.yuno.youknow.controller.dto;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record FiltroRespuestaDTO(
        LocalDateTime from,
        LocalDateTime to,

        long totalEvents,
        long totalSuccess,
        long totalFailed,

        double conversionRate,
        double errorRate,

        Double avgLatencyMs,
        BigDecimal failedAmount,

        int activeIssuesCount,
        List<eventoDTO> activeIssues
) {}
