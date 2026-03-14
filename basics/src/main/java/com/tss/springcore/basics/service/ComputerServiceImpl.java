package com.tss.springcore.basics.service;

import com.tss.springcore.basics.model.Computer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ComputerServiceImpl implements ComputerService {

    private final Computer computer;

    public ComputerServiceImpl(Computer computer) {
        this.computer = computer;
    }
    public Computer getComputer() {
        return computer;
    }
}
