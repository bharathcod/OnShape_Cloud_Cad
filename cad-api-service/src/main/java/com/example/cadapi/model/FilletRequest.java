package com.example.cadapi.model;

public class FilletRequest {
    private Long modelId;
    private double radius;

    // Getters and Setters
    public Long getModelId() { return modelId; }
    public void setModelId(Long modelId) { this.modelId = modelId; }

    public double getRadius() { return radius; }
    public void setRadius(double radius) { this.radius = radius; }
}
