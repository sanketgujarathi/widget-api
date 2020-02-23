package com.miro.assignment.domain;

import javax.validation.constraints.Positive;
import java.math.BigDecimal;

public class WidgetRequest {

    private int x;
    private int y;
    @Positive
    private BigDecimal width;
    @Positive
    private BigDecimal height;
    private int zindex = Integer.MAX_VALUE;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public BigDecimal getWidth() {
        return width;
    }

    public void setWidth(BigDecimal width) {
        this.width = width;
    }

    public BigDecimal getHeight() {
        return height;
    }

    public void setHeight(BigDecimal height) {
        this.height = height;
    }

    public int getZindex() {
        return zindex;
    }

    public void setZindex(int zindex) {
        this.zindex = zindex;
    }
}
