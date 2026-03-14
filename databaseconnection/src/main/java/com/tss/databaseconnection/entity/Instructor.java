package com.tss.databaseconnection.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "instructors")
public class Instructor {

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int instructorId;

    @Column
    private String name;

    public Instructor() {

    }

    public Instructor(int instructorId, String name) {
        this.instructorId = instructorId;
        this.name = name;
    }

    public int getInstructorId() {
        return instructorId;
    }

    public void setInstructorId(int instructorId) {
        this.instructorId = instructorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
