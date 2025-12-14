package com.yuno.youknow.db.orm;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;


@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "eventos_pago")
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

    @Column(name = "currency", nullable = false)
    private String currency;

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

    @PrePersist
    public void prePersist() {
        if (this.eventId == null) {
            this.eventId = UUID.randomUUID().toString();
        }
        if (this.timestamp == null) {
            this.timestamp = LocalDateTime.now();
        }
    }

}
