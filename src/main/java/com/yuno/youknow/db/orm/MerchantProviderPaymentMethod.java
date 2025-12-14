package com.yuno.youknow.db.orm;

import com.fasterxml.jackson.annotation.JsonIgnore;  // ← IMPORTAR
import jakarta.persistence.*;

@Entity
@Table(
        name = "merchant_provider_payment_method",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_provider_config_method",
                columnNames = {"provider_config_id", "payment_method"}
        )
)
public class MerchantProviderPaymentMethod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "provider_config_id", nullable = false)
    @JsonIgnore  // ← TAMBIÉN AQUÍ
    private MerchantProviderConfig providerConfig;

    @Column(name = "payment_method", nullable = false, length = 40)
    private String paymentMethod;

    public MerchantProviderPaymentMethod() {}

    public MerchantProviderPaymentMethod(MerchantProviderConfig providerConfig, String paymentMethod) {
        this.providerConfig = providerConfig;
        this.paymentMethod = paymentMethod;
    }

    public Long getId() { return id; }
    public MerchantProviderConfig getProviderConfig() { return providerConfig; }
    public void setProviderConfig(MerchantProviderConfig providerConfig) { this.providerConfig = providerConfig; }
    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }
}

