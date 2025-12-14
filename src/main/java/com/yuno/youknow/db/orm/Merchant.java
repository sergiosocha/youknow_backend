package com.yuno.youknow.db.orm;

import com.fasterxml.jackson.annotation.JsonManagedReference;  // ← AGREGAR
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "merchant")
@Data
@Getter @Setter
public class Merchant {

    @Id
    @Column(name = "merchant_id", nullable = false, length = 64)
    private String merchantId;

    @Column(name = "merchant_name", nullable = false, length = 120)
    private String merchantName;

    @Column(name = "country_code", nullable = false, length = 8)
    private String countryCode;

    @Column(name = "currency", nullable = false, length = 8)
    private String currency;

    @OneToMany(
            mappedBy = "merchant",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonManagedReference
    private List<MerchantProviderConfig> providers = new ArrayList<>();
}