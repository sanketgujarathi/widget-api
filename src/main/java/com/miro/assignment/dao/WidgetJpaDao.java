package com.miro.assignment.dao;

import com.miro.assignment.domain.Widget;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
@Profile("sql")
public interface WidgetJpaDao extends PagingAndSortingRepository<Widget, BigInteger> {

    @Query(value = "select w from Widget w where (w.x - w.width/2) >= 0 and (w.x + w.width/2) <= :upperX and (w.y - w.height/2) >= 0 and (w.y + w.height/2) <= :upperY")
    Page<Widget> findByFilterCriteria(Pageable pageable, int upperX, int upperY);

}
