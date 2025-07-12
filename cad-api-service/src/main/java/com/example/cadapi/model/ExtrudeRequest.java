package com.example.cadapi.model;

public class ExtrudeRequest {
    private Long sketchId;
    private double depth;

    // Getters and Setters
    public Long getSketchId() { return sketchId; }
    public void setSketchId(Long sketchId) { this.sketchId = sketchId; }

    public double getDepth() { return depth; }
    public void setDepth(double depth) { this.depth = depth; }
}
