package com.miro.assignment.dao;

import com.miro.assignment.domain.Widget;

import java.math.BigDecimal;

public class TestHelper {
    public static Widget getWidget(int x, int y, int zindex) {
        Widget widget = new Widget();
        widget.setX(x);
        widget.setY(y);
        widget.setZindex(zindex);
        widget.setHeight(BigDecimal.valueOf(100));
        widget.setWidth(BigDecimal.valueOf(100));
        return widget;
    }

    public static Widget getWidget() {
        Widget widget = new Widget();
        widget.setX(50);
        widget.setY(50);
        widget.setZindex(10);
        widget.setHeight(BigDecimal.valueOf(100));
        widget.setWidth(BigDecimal.valueOf(100));
        return widget;
    }
}
