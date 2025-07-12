package com.example.cadapi.model;

public class SketchRequest {
    private String shapeType; // rectangle, circle, etc.
    private double width;
    private double height;

    private Long documentId; // Add this field

    public Long getDocumentId() { return documentId; }
    public void setDocumentId(Long documentId) { this.documentId = documentId; }

    // Getters and Setters
    public String getShapeType() { return shapeType; }
    public void setShapeType(String shapeType) { this.shapeType = shapeType; }

    public double getWidth() { return width; }
    public void setWidth(double width) { this.width = width; }

    public double getHeight() { return height; }
    public void setHeight(double height) { this.height = height; }
}
