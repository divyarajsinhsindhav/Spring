package com.tss.springcore.basics.service;

import com.tss.springcore.basics.model.Harddisk;
import org.springframework.stereotype.Service;

@Service
public class HarddiskServiceImpl implements HarddiskService {

    private Harddisk harddisk;

    public HarddiskServiceImpl(Harddisk harddisk) {
        this.harddisk = harddisk;
    }

    public Harddisk getHarddisk() {
        return new Harddisk();
    }
}
