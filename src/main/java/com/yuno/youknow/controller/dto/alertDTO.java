package com.yuno.youknow.controller.dto;

import java.time.Instant;

public record alertDTO(
        String level,
        String message,
        Instant timestamp
) {}

