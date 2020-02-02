package com.miro.assignment.domain;

public class WidgetFilterCriteria {

    private int lowerLeftX;
    private int lowerLeftY;
    private int upperRightX;
    private int upperRightY;

    public WidgetFilterCriteria(int lowerLeftX, int lowerLeftY, int upperRightX, int upperRightY) {
        this.lowerLeftX = lowerLeftX;
        this.lowerLeftY = lowerLeftY;
        this.upperRightX = upperRightX;
        this.upperRightY = upperRightY;
    }

    public int getLowerLeftX() {
        return lowerLeftX;
    }

    public int getLowerLeftY() {
        return lowerLeftY;
    }

    public int getUpperRightX() {
        return upperRightX;
    }

    public int getUpperRightY() {
        return upperRightY;
    }
}
