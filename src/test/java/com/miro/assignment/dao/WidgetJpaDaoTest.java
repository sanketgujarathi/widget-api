package com.miro.assignment.dao;

import com.miro.assignment.domain.Widget;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("sql")
public class WidgetJpaDaoTest extends TestHelper {

    @Autowired
    WidgetJpaDao widgetJpaDao;

    @Test
    public void testGetAllWidgetsByFilterCriteria() {

        widgetJpaDao.save(getWidget(50, 50, 10));
        widgetJpaDao.save(getWidget(50, 100, 20));
        widgetJpaDao.save(getWidget(100, 100, 30));

        Page<Widget> page = widgetJpaDao.findByFilterCriteria(PageRequest.of(1, 10), 0, 0, 100, 150);
        Assert.assertThat(page.getTotalElements(), Matchers.is(2L));

        widgetJpaDao.save(getWidget(-50, -50, 50));
        Page<Widget> anotherPage = widgetJpaDao.findByFilterCriteria(PageRequest.of(1, 10), -100, -150, 0, 0);
        Assert.assertThat(anotherPage.getTotalElements(), Matchers.is(1L));
    }

}