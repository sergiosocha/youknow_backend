package com.yuno.youknow.controller;

import com.yuno.youknow.service.AlertaService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api")
public class AlertasController {

    private final AlertaService alertasService;

    public AlertasController(AlertaService alertasService) {
        this.alertasService = alertasService;
    }

    @GetMapping("/alertas")
    public Object getAlertas(@RequestParam String from, @RequestParam String to) {
        LocalDateTime fromDt = LocalDateTime.parse(from.trim());
        LocalDateTime toDt = LocalDateTime.parse(to.trim());
        return alertasService.getAlertas(fromDt, toDt);
    }
}
