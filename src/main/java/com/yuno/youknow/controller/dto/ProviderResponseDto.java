package com.yuno.youknow.controller.dto;

import java.util.Set;

public record ProviderResponseDto(
        Long id,
        String provider,
        boolean enabled,
        Set<PaymentMethodResponseDto> paymentMethods
) {}