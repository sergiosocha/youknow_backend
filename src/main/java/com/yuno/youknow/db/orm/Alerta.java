package com.yuno.youknow.db.orm;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "alertas")
@Getter
@Setter
public class Alerta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String merchantId;
    private String merchantName;
    private String countryCode;
    private String provider;
    private String incidentTag;
    private String category;

    private LocalDateTime lastSeen;

    // getters y setters
}
