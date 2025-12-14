package com.yuno.youknow.service;


import com.yuno.youknow.controller.dto.AlertaDTO;
import com.yuno.youknow.db.orm.Alerta;
import com.yuno.youknow.db.orm.EventoPago;
import com.yuno.youknow.db.repository.AlertaRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class AlertaFinalService {

    private final AlertaRepository alertaRepository;
    private final AlertService alertService;

    public AlertaFinalService(AlertaRepository alertaRepository,
                              AlertService alertService) {
        this.alertaRepository = alertaRepository;
        this.alertService = alertService;
    }

    @Transactional
    public AlertaDTO create(AlertaDTO dto) {

        Alerta alerta = new Alerta();
        alerta.setMerchantId(dto.merchantId());
        alerta.setMerchantName(dto.merchantName());
        alerta.setCountryCode(dto.countryCode());
        alerta.setProvider(dto.provider());
        alerta.setIncidentTag(dto.incidentTag());
        alerta.setCategory(dto.category());
        alerta.setLastSeen(dto.lastSeen());

        Alerta saved = alertaRepository.save(alerta);

        alertService.evaluate(saved);

        return dto;
    }
}
