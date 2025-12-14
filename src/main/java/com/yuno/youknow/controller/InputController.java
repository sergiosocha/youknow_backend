package com.yuno.youknow.controller;

import com.yuno.youknow.controller.dto.EventoPagoCreateDto;
import com.yuno.youknow.db.orm.EventoPago;
import com.yuno.youknow.service.InputService;
import com.yuno.youknow.service.TransactionService;
import com.yuno.youknow.utils.EmailUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class InputController {

    private final InputService service;
    private final TransactionService transactionService;
    private final EmailUtils emailUtils;

    public InputController(InputService service, EmailUtils emailUtils, TransactionService transactionService, EmailUtils emailUtils1) {
        this.service = service;
        this.transactionService = transactionService;
        this.emailUtils = emailUtils1;
    }

    @PostMapping("/events")
    public void createEvents(@RequestBody List<EventoPagoCreateDto> events) {
        service.ingest(events);
    }

    @PostMapping("/alerts")
    public void createAlerts(@RequestBody EventoPago events) {
        transactionService.create(events);
        emailUtils.sendEmail("ALERTA", "ALGO SE JODIOOO");
    }





}
