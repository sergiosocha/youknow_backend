package com.yuno.youknow.service;


import com.yuno.youknow.db.orm.EventoPago;
import com.yuno.youknow.db.repository.EventoPagoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final EventoPagoRepository repository;
    private final ApplicationEventPublisher publisher;
    private final AlertService alertService;

    @Transactional
    public EventoPago create(EventoPago tx) {
        EventoPago saved = repository.save(tx);
        alertService.evaluate(saved);
        return saved;
    }



}