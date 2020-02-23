package com.miro.assignment.service;

import com.miro.assignment.domain.Widget;
import com.miro.assignment.domain.WidgetRequest;

import java.time.LocalDateTime;

abstract class AbstractWidgetService {

    protected Widget mapToWidget(WidgetRequest widgetRequest) {
        return mapToWidget(widgetRequest, new Widget());
    }

    protected Widget mapToWidget(WidgetRequest source, Widget destination) {
        destination.setX(source.getX());
        destination.setY(source.getY());
        destination.setHeight(source.getHeight());
        destination.setWidth(source.getWidth());
        destination.setZindex(source.getZindex());
        destination.setLastModifiedDate(LocalDateTime.now());
        return destination;

    }
}
