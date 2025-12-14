package com.yuno.youknow.controller.dto;

import java.util.List;

public record MerchantDto(
        String merchantId,
        String merchantName,
        String countryCode,
        String currency,
        List<MerchantProviderDto> providers
) {}