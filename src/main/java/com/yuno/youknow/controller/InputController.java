package com.yuno.youknow.controller;

import com.yuno.youknow.controller.dto.EventoPagoCreateDto;
import com.yuno.youknow.db.orm.EventoPago;
import com.yuno.youknow.logic.InputService;
import com.yuno.youknow.logic.TransactionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class InputController {

    private final InputService service;
    private final TransactionService transactionService;

    public InputController(InputService service, TransactionService transactionService) {
        this.service = service;
        this.transactionService = transactionService;

    }

    @PostMapping("/events")
    public void createEvents(@RequestBody List<EventoPagoCreateDto> events) {
        service.ingest(events);
    }

    @PostMapping("/try")
    public EventoPago create(@RequestBody EventoPago tx) {
        return transactionService.create(tx);
    }
}
