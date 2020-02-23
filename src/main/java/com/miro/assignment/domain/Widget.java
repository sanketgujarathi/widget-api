package com.miro.assignment.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;

import static java.lang.Integer.MAX_VALUE;

@Entity
public class Widget {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private BigInteger id;
    private int x;
    private int y;
    private BigDecimal width;
    private BigDecimal height;
    private int zindex;
    private LocalDateTime lastModifiedDate;

    public Widget() {
        this.zindex = MAX_VALUE;
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

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

    public LocalDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(LocalDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

}
