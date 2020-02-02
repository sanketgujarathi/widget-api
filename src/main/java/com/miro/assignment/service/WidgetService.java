package com.miro.assignment.service;

import com.miro.assignment.domain.Widget;
import com.miro.assignment.domain.WidgetFilterCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface WidgetService {

    Widget createWidget(Widget widget);
    Page<Widget> getAllWidgets(Pageable pageable, WidgetFilterCriteria criteria);
    Optional<Widget> getWidget(int id);
    Optional<Widget> updateWidget(int id, Widget widget);
    void deleteWidget(int id);
}
