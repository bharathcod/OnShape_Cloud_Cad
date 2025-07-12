package com.example.cadapi.model;

import java.util.concurrent.atomic.AtomicLong;

public class CadModel {
    private static final AtomicLong ID_GENERATOR = new AtomicLong();

    private Long id;
    private String operation;
    private String data;

    public CadModel(String operation, String data) {
        this.id = ID_GENERATOR.incrementAndGet();
        this.operation = operation;
        this.data = data;
    }

    // Getters and Setters
    public Long getId() { return id; }

    public String getOperation() { return operation; }
    public void setOperation(String operation) { this.operation = operation; }

    public String getData() { return data; }
    public void setData(String data) { this.data = data; }
}
