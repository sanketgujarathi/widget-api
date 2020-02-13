package com.miro.assignment.service;

import com.miro.assignment.dao.WidgetJpaDao;
import com.miro.assignment.domain.Widget;
import com.miro.assignment.domain.WidgetFilterCriteria;
import com.miro.assignment.domain.WidgetRequest;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.Optional;

@Service
@Profile("sql")
public class WidgetServiceJpaImpl implements WidgetService {

    private final WidgetJpaDao widgetDao;

    public WidgetServiceJpaImpl(WidgetJpaDao widgetDao) {
        this.widgetDao = widgetDao;
    }

    @Override
    public Widget createWidget(WidgetRequest widgetRequest) {
        return widgetDao.save(mapToWidget(widgetRequest));
    }

    @Override
    public Page<Widget> getAllWidgets(Pageable pageable, Optional<WidgetFilterCriteria> criteria) {
        return criteria.isPresent() ? widgetDao.findByFilterCriteria(pageable, criteria.get()
                .getX(), criteria.get()
                .getY()) : widgetDao.findAll(pageable);

    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Optional<Widget> getWidget(BigInteger id) {
        return widgetDao.findById(id);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Optional<Widget> updateWidget(BigInteger id, WidgetRequest widgetRequest) {
        Optional<Widget> existingWidget = getWidget(id);
        return existingWidget.map(value -> widgetDao.save(mapToWidget(widgetRequest, value)));
    }

    @Override
    public void deleteWidget(BigInteger id) {
        widgetDao.deleteById(id);
    }

    private Widget mapToWidget(WidgetRequest widgetRequest) {
        return mapToWidget(widgetRequest, new Widget());
    }

    private Widget mapToWidget(WidgetRequest source, Widget destination) {
        destination.setX(source.getX());
        destination.setY(source.getY());
        destination.setHeight(source.getHeight());
        destination.setWidth(source.getWidth());
        destination.setZindex(source.getZindex());
        destination.setZindex(source.getZindex());
        return destination;

    }
}
