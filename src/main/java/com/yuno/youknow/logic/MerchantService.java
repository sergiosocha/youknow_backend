package com.yuno.youknow.logic;

import com.yuno.youknow.controller.dto.MerchantDto;
import com.yuno.youknow.controller.dto.MerchantProviderDto;
import com.yuno.youknow.db.orm.Merchant;
import com.yuno.youknow.db.orm.MerchantProviderConfig;
import com.yuno.youknow.db.orm.MerchantProviderPaymentMethod;
import com.yuno.youknow.db.repository.MerchantRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class MerchantService {

    private final MerchantRepository merchantRepo;

    public MerchantService(MerchantRepository merchantRepo) {
        this.merchantRepo = merchantRepo;
    }

    @Transactional
    public void upsertMany(List<MerchantDto> dtos) {
        if (dtos == null) return;
        for (MerchantDto dto : dtos) {
            upsertOne(dto);
        }
    }

    @Transactional
    public void upsertOne(MerchantDto dto) {
        Merchant m = merchantRepo.findById(dto.merchantId()).orElseGet(Merchant::new);

        m.setMerchantId(dto.merchantId());
        m.setMerchantName(dto.merchantName());
        m.setCountryCode(dto.countryCode());
        m.setCurrency(dto.currency());

        // ★ CAMBIO CLAVE: Sincronizar providers en lugar de reemplazarlos
        List<MerchantProviderDto> incomingProviders = dto.providers() == null ? List.of() : dto.providers();
        syncProviders(m, incomingProviders);

        merchantRepo.save(m);
    }

    public List<Merchant> getAll() {
        return merchantRepo.findAll();
    }

    public Merchant getById(String id) {
        return merchantRepo.findById(id).orElse(null);
    }

    /**
     * Sincroniza los providers del merchant sin crear duplicados
     */
    private void syncProviders(Merchant merchant, List<MerchantProviderDto> incomingProviders) {
        // Crear un mapa de providers entrantes por nombre
        Map<String, MerchantProviderDto> incomingMap = incomingProviders.stream()
                .collect(Collectors.toMap(
                        MerchantProviderDto::provider,
                        p -> p,
                        (a, b) -> b // En caso de duplicados, tomar el último
                ));

        // Crear un mapa de providers existentes por nombre
        Map<String, MerchantProviderConfig> existingMap = merchant.getProviders().stream()
                .collect(Collectors.toMap(
                        MerchantProviderConfig::getProvider,
                        p -> p
                ));

        // 1. Eliminar providers que ya no están en el DTO
        merchant.getProviders().removeIf(existing ->
                !incomingMap.containsKey(existing.getProvider())
        );

        // 2. Actualizar o crear providers
        for (MerchantProviderDto incomingDto : incomingProviders) {
            MerchantProviderConfig config = existingMap.get(incomingDto.provider());

            if (config == null) {
                // Crear nuevo provider
                config = new MerchantProviderConfig();
                config.setMerchant(merchant);
                config.setProvider(incomingDto.provider());
                merchant.getProviders().add(config);
            }

            // Actualizar propiedades
            config.setEnabled(Boolean.TRUE.equals(incomingDto.enabled()));

            // Sincronizar payment methods
            syncPaymentMethods(config, incomingDto.paymentMethods());
        }
    }

    /**
     * Sincroniza los métodos de pago sin crear duplicados
     */
    private void syncPaymentMethods(MerchantProviderConfig cfg, List<String> methods) {
        Set<String> incoming = methods == null ? Set.of() : new HashSet<>(methods);

        // Eliminar métodos que ya no están
        cfg.getPaymentMethods().removeIf(pm -> !incoming.contains(pm.getPaymentMethod()));

        // Obtener métodos existentes
        Set<String> existing = cfg.getPaymentMethods().stream()
                .map(MerchantProviderPaymentMethod::getPaymentMethod)
                .collect(Collectors.toSet());

        // Agregar métodos nuevos
        for (String m : incoming) {
            if (!existing.contains(m)) {
                cfg.getPaymentMethods().add(new MerchantProviderPaymentMethod(cfg, m));
            }
        }
    }
}