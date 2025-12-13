package com.yuno.youknow.db.orm;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;

@Entity
@Table(name = "eventos_pago")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventoPago {

    @Id
    @Column(name = "event_id", nullable = false)
    private String eventId;

    @Column(name = "timestamp", nullable = false)
    private LocalDateTime timestamp;

    @Column(name = "merchant_id", nullable = false)
    private String merchantId;

    @Column(name = "merchant_name", nullable = false)
    private String merchantName;

    @Column(name = "country_code", length = 2, nullable = false)
    private String countryCode;

    @Column(name = "provider", nullable = false)
    private String provider;

    @Column(name = "payment_method", nullable = false)
    private String payment_method;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "error_type")
    private String errorType;

    @Column(name = "latency_ms")
    private Integer latencyMs;

    @Column(name = "incident_tag")
    private String incidentTag;

    @Column(name = "impact_level")
    private String impactLevel;

    @Column(name = "suggested_action_type")
    private String suggestedActionType;
}
