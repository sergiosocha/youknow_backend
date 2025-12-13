package com.yuno.youknow.controller;

import com.yuno.youknow.controller.dto.EventoPagoCreateDto;
import com.yuno.youknow.logic.InputService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class InputController {

    private final InputService service;

    public InputController(InputService service) {
        this.service = service;
    }

    @PostMapping("/events")
    public void createEvents(@RequestBody List<EventoPagoCreateDto> events) {
        service.ingest(events);
    }
}
