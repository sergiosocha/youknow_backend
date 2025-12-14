package com.yuno.youknow.controller;

import com.yuno.youknow.controller.dto.eventoDTO;
import com.yuno.youknow.controller.dto.FiltroRespuestaDTO;
import com.yuno.youknow.service.AnaliticaService;
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
            @RequestParam String from,
            @RequestParam String to
    ) {
        LocalDateTime fromDt = parseDt(from);
        LocalDateTime toDt = parseDt(to);
        return service.getOverview(fromDt, toDt);
    }

    @GetMapping("/eventos")
    public List<eventoDTO> issues(
            @RequestParam String from,
            @RequestParam String to
    ) {
        LocalDateTime fromDt = parseDt(from);
        LocalDateTime toDt = parseDt(to);
        return service.getIssues(fromDt, toDt);
    }

    private LocalDateTime parseDt(String raw) {
        if (raw == null) throw new IllegalArgumentException("datetime param is null");
        String cleaned = raw.trim().replace("\n", "").replace("\r", "");
        return LocalDateTime.parse(cleaned);
    }
}
