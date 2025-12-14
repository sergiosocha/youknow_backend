package com.yuno.youknow.service;


import com.yuno.youknow.controller.dto.AlertaDTO;
import com.yuno.youknow.db.orm.Alerta;
import com.yuno.youknow.db.orm.EventoPago;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AlertService {

    private final SimpMessagingTemplate ws;

    public void evaluate(Alerta tx) {
        ws.convertAndSend(
                "/topic/alerts",
                tx
        );
    }
}