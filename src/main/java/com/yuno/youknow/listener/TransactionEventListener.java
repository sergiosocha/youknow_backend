package com.yuno.youknow.listener;

import com.yuno.youknow.event.TransactionCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TransactionEventListener {

    private final SimpMessagingTemplate ws;

    @EventListener
    public void onTransactionCreated(TransactionCreatedEvent event) {
        ws.convertAndSend(
                "/topic/transactions",
                event.transaction()
        );
    }
}

