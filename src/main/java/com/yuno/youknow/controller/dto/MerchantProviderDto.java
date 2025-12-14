package com.yuno.youknow.controller.dto;

import java.util.List;

public record MerchantProviderDto(
        String provider,
        Boolean enabled,
        List<String> paymentMethods
) {}
