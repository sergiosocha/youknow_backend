package com.yuno.youknow.controller.dto;

import java.time.LocalDateTime;

public record AlertaDTO(
        String merchantId,
        String merchantName,
        String countryCode,
        String provider,
        String incidentTag,
        String category,
        LocalDateTime lastSeen
) {}
