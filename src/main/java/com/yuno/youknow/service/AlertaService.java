package com.yuno.youknow.service;

import com.yuno.youknow.controller.dto.AlertaDTO;
import com.yuno.youknow.db.repository.EventoPagoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AlertaService {

    private final EventoPagoRepository eventosPagoRepo;

    public AlertaService(EventoPagoRepository eventosPagoRepo) {
        this.eventosPagoRepo = eventosPagoRepo;
    }

    public List<AlertaDTO> getAlertas(LocalDateTime from, LocalDateTime to) {
        if (from == null || to == null) throw new IllegalArgumentException("from y to son obligatorios");
        if (!from.isBefore(to)) throw new IllegalArgumentException("from debe ser anterior a to");

        return eventosPagoRepo.findAlertasBase(from, to)
                .stream()
                .map(r -> new AlertaDTO(
                        r.getMerchantId(),
                        r.getMerchantName(),
                        r.getCountryCode(),
                        r.getProvider(),
                        r.getIncidentTag(),
                        r.getCategory(),
                        r.getLastSeen()

                ))
                .toList();
    }

    public void saveAlert(AlertaDTO alertaDTO){

    }


}
