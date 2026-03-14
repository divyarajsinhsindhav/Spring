package com.tss.springcore.basics.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component
public class Harddisk {

    private int capacity = 32;

    public Harddisk() {

    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public String toString() {
        return "Harddisk{" +
                "capacity=" + capacity +
                '}';
    }
}
