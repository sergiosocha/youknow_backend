package com.yuno.youknow.controller;

import com.yuno.youknow.controller.dto.MerchantDto;
import com.yuno.youknow.db.orm.Merchant;
import com.yuno.youknow.logic.MerchantService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/merchants")
public class MerchantController {

    private final MerchantService service;

    public MerchantController(MerchantService service) {
        this.service = service;
    }

    @PostMapping
    public void upsert(@RequestBody List<MerchantDto> merchants) {
        service.upsertMany(merchants);
    }

    @GetMapping
    public List<Merchant> getAll() {
        return service.getAll();
    }

    @GetMapping("/{merchantId}")
    public Merchant getById(@PathVariable String merchantId) {
        return service.getById(merchantId);
    }
}