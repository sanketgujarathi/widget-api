package com.miro.assignment.dao;

import com.miro.assignment.domain.Widget;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WidgetDao extends PagingAndSortingRepository<Widget, Integer> {
}
