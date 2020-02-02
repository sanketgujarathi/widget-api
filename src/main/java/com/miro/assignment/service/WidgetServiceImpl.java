package com.miro.assignment.service;

import com.miro.assignment.dao.WidgetDao;
import com.miro.assignment.domain.Widget;
import com.miro.assignment.domain.WidgetFilterCriteria;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WidgetServiceImpl implements WidgetService {

    private final WidgetDao widgetDao;

    public WidgetServiceImpl(WidgetDao widgetDao) {
        this.widgetDao = widgetDao;
    }

    @Override
    public Widget createWidget(Widget widget) {
        return widgetDao.save(widget);
    }

    @Override
    public Page<Widget> getAllWidgets(Pageable pageable, WidgetFilterCriteria criteria) {
        return widgetDao.findAll(pageable);

    }

    @Override
    public Optional<Widget> getWidget(int id) {
        return widgetDao.findById(id);
    }

    @Override
    public Optional<Widget> updateWidget(int id, Widget widget) {
        Optional<Widget> existingWidget = widgetDao.findById(id);
        return existingWidget.map(value -> widgetDao.save(update(widget, value)));
    }

    private Widget update(Widget source, Widget destination){//TODO names
        destination.setX(source.getX());
        return  destination;

    }

    @Override
    public void deleteWidget(int id) {
         widgetDao.deleteById(id);
    }
}
