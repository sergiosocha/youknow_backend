package com.yuno.youknow.controller.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


public record AlertaDTO(
        String merchantId,
        String merchantName,
        String countryCode,
        String provider,
        String incidentTag,
        String category,
        LocalDateTime lastSeen



) {
    @Override
    public String merchantId() {
        return merchantId;
    }

    @Override
    public String merchantName() {
        return merchantName;
    }

    @Override
    public String countryCode() {
        return countryCode;
    }

    @Override
    public String provider() {
        return provider;
    }

    @Override
    public String incidentTag() {
        return incidentTag;
    }

    @Override
    public String category() {
        return category;
    }

    @Override
    public LocalDateTime lastSeen() {
        return lastSeen;
    }

}
