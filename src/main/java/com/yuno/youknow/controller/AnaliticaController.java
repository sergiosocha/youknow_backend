package com.yuno.youknow.controller;

import com.yuno.youknow.controller.dto.eventoDTO;
import com.yuno.youknow.controller.dto.FiltroRespuestaDTO;
import com.yuno.youknow.logic.AnaliticaService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api")
public class AnaliticaController {

    private final AnaliticaService service;

    public AnaliticaController(AnaliticaService service) {
        this.service = service;
    }

    @GetMapping("/overview")
    public FiltroRespuestaDTO overview(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to
    ) {
        return service.getOverview(from, to);
    }

    @GetMapping("/eventos")
    public List<eventoDTO> issues(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to
    ) {
        return service.getIssues(from, to);
    }
}

