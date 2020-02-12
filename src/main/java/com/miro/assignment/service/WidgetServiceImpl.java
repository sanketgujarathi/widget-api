package com.miro.assignment.service;

import com.miro.assignment.dao.WidgetDao;
import com.miro.assignment.domain.Widget;
import com.miro.assignment.domain.WidgetFilterCriteria;
import com.miro.assignment.domain.WidgetRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Optional;

@Service
public class WidgetServiceImpl implements WidgetService {

    private final WidgetDao widgetDao;

    public WidgetServiceImpl(WidgetDao widgetDao) {
        this.widgetDao = widgetDao;
    }

    @Override
    public Widget createWidget(WidgetRequest widgetRequest) {
        return widgetDao.save(mapToWidget(widgetRequest));
    }

    @Override
    public Page<Widget> getAllWidgets(Pageable pageable, Optional<WidgetFilterCriteria> criteria) {
        return criteria.isPresent() ? widgetDao.findByFilterCriteria(pageable, criteria.get().getX(), criteria.get().getY()) : widgetDao.findAll(pageable);

    }

    @Override
    public Optional<Widget> getWidget(BigInteger id) {
        return widgetDao.findById(id);
    }

    @Override
    public Optional<Widget> updateWidget(BigInteger id, WidgetRequest widgetRequest) {
        Optional<Widget> existingWidget = widgetDao.findById(id);
        return existingWidget.map(value -> widgetDao.save(mapToWidget(widgetRequest, value)));
    }

    @Override
    public void deleteWidget(BigInteger id) {
         widgetDao.deleteById(id);
    }

    private Widget mapToWidget(WidgetRequest widgetRequest) {
        Widget widget = new Widget();
        mapToWidget(widgetRequest, new Widget());
        return widget;
    }

    private Widget mapToWidget(WidgetRequest source, Widget destination){//TODO names
        destination.setX(source.getX());
        destination.setY(source.getY());
        destination.setHeight(source.getHeight());
        destination.setWidth(source.getWidth());
        destination.setZindex(source.getZindex());
        destination.setZindex(source.getZindex());
        return  destination;

    }
}
