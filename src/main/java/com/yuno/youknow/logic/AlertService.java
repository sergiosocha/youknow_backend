package com.yuno.youknow.logic;


import com.yuno.youknow.db.orm.EventoPago;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@RequiredArgsConstructor
public class AlertService {

    private final SimpMessagingTemplate ws;

    public void evaluate(EventoPago tx) {
        ws.convertAndSend(
                "/topic/alerts",
                tx
        );
    }
}