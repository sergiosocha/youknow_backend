
package com.yuno.youknow.db.orm;

import com.fasterxml.jackson.annotation.JsonIgnore;  // ← IMPORTAR ESTO
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "merchant_provider_config")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class MerchantProviderConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "merchant_id", nullable = false)
    @JsonIgnore  // ← SIMPLEMENTE IGNORA ESTE CAMPO EN JSON
    private Merchant merchant;

    @Column(name = "provider", nullable = false, length = 64)
    private String provider;

    @Column(name = "enabled", nullable = false)
    private boolean enabled;

    @OneToMany(mappedBy = "providerConfig", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MerchantProviderPaymentMethod> paymentMethods = new HashSet<>();
}