package com.miro.assignment.dao;

import com.miro.assignment.domain.Widget;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public interface WidgetDao extends PagingAndSortingRepository<Widget, BigInteger> {

    @Query(value = "select w from Widget w where (w.x - w.width/2) >= 0 and (w.x + w.width/2) <= :upperX and (w.y - w.height/2) >= 0 and (w.y + w.height/2) <= :upperY")
    //@Query(value = "select w from Widget w")
    Page<Widget> findByFilterCriteria(Pageable pageable, int upperX, int upperY);

}
