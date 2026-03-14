package com.tss.springcore.basics.controller;

import com.tss.springcore.basics.model.Harddisk;
import com.tss.springcore.basics.service.HarddiskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app")
public class HarddiskController {

    private HarddiskService harddiskService;

    public HarddiskController(HarddiskService harddiskService) {
        this.harddiskService = harddiskService;
    }

    @GetMapping("/harddisk")
    public Harddisk getHarddisk() {
        return harddiskService.getHarddisk();
    }
}
