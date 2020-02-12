package com.miro.assignment.service;

import com.miro.assignment.domain.Widget;
import com.miro.assignment.domain.WidgetFilterCriteria;
import com.miro.assignment.domain.WidgetRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigInteger;
import java.util.Optional;

public interface WidgetService {

    Widget createWidget(WidgetRequest widget);
    Page<Widget> getAllWidgets(Pageable pageable, Optional<WidgetFilterCriteria> criteria);
    Optional<Widget> getWidget(BigInteger id);
    Optional<Widget> updateWidget(BigInteger id, WidgetRequest widget);
    void deleteWidget(BigInteger id);
}
