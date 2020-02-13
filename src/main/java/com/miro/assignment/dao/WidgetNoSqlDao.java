package com.miro.assignment.dao;

import com.miro.assignment.domain.Widget;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
@Profile("nosql")
public interface WidgetNoSqlDao extends MongoRepository<Widget, BigInteger> {

    @Query(value = "{$where: 'this.x - this.width/2 >= 0 && this.x + this.width/2 <= ?0 && this.y - this.height/2 >= 0 && this.y + this.height/2 <= ?1'}")
    Page<Widget> findByFilterCriteria(Pageable pageable, int upperX, int upperY);
}
