package com.yuno.youknow.controller;

import com.yuno.youknow.controller.dto.AlertaDTO;
import com.yuno.youknow.controller.dto.EventoPagoCreateDto;
import com.yuno.youknow.db.orm.EventoPago;
import com.yuno.youknow.service.AlertaFinalService;
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
    private final AlertaFinalService alertaFinalService;

    public InputController(InputService service, EmailUtils emailUtils, TransactionService transactionService, EmailUtils emailUtils1, AlertaFinalService alertaFinalService) {
        this.service = service;
        this.transactionService = transactionService;
        this.emailUtils = emailUtils1;
        this.alertaFinalService = alertaFinalService;
    }

    @PostMapping("/events")
    public void createEvents(@RequestBody List<EventoPagoCreateDto> events) {
        service.ingest(events);
    }

    @PostMapping("/alerts")
    public void createAlerts(
            @RequestBody AlertaDTO events,
            @RequestParam String email
    ) {
        alertaFinalService.create(events);
        emailUtils.sendEmail(
                "ALERTA",
                events.merchantName() + " presentó un error con id "+ events.incidentTag() + " el día " + events.lastSeen() + " un error que es causado por el " + events.category(),
                email
        );
    }





}
