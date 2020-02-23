package com.miro.assignment.controller;

import com.miro.assignment.dao.WidgetJpaDao;
import com.miro.assignment.domain.Widget;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static com.miro.assignment.dao.TestHelper.getWidget;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("sql")
class WidgetControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WidgetJpaDao widgetJpaDao;

    @AfterEach
    void tearDown() {
        widgetJpaDao.deleteAll();
    }

    @Test
    void testCreateWidget() throws Exception {
        this.mockMvc.perform(post("/widget")
                .content("{\"x\":50,\"y\":50,\"width\":100,\"height\":100,\"zindex\":10}")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content()
                        .json("{'x':50,'y':50,'width':100,'height':100,'zindex':10}", false
                        ));
    }

    @Test
    void testGetWidget() throws Exception {
        Widget save = widgetJpaDao.save(getWidget());
        this.mockMvc.perform(get("/widget/" + save.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content()
                        .json("{'x':50,'y':50,'width':100,'height':100,'zindex':10}", false
                        ));

    }

    @Test
    void testGetAllWidgetsFilterCriteria() throws Exception {
        widgetJpaDao.save(getWidget(50, 50, 10));
        widgetJpaDao.save(getWidget(100, 100, 30));

        this.mockMvc.perform(get("/widgets?lowerX=0&lowerY=0&upperX=100&upperY=150"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content()
                        .json("{'_embedded':{'widgetList':[{'x':50,'y':50,'width':100.00,'height':100.00,'zindex':10}]},'_links':{'self':{'href':'http://localhost/widgets?page=1&size=10&sort=zindex,asc'}},'page':{'size':10,'totalElements':1,'totalPages':1,'number':1}}", false));

    }

    @Test
    void testGetAllWidgetsPagination() throws Exception {
        widgetJpaDao.save(getWidget(50, 50, 10));
        widgetJpaDao.save(getWidget(50, 100, 20));
        widgetJpaDao.save(getWidget(100, 100, 30));

        this.mockMvc.perform(get("/widgets?page=1&size=1&lowerX=0&lowerY=0&upperX=150&upperY=150"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content()
                        .json("{'_embedded':{'widgetList':[{'x':50,'y':50,'width':100.00,'height':100.00,'zindex':10}]},'_links':{'first':{'href':'http://localhost/widgets?page=1&size=1&sort=zindex,asc'},'self':{'href':'http://localhost/widgets?page=1&size=1&sort=zindex,asc'},'next':{'href':'http://localhost/widgets?page=2&size=1&sort=zindex,asc'},'last':{'href':'http://localhost/widgets?page=3&size=1&sort=zindex,asc'}},'page':{'size':1,'totalElements':3,'totalPages':3,'number':1}}", false));
        this.mockMvc.perform(get("/widgets?page=2&size=1&lowerX=0&lowerY=0&upperX=150&upperY=150"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content()
                        .json("{'_embedded':{'widgetList':[{'x':50,'y':100,'width':100.00,'height':100.00,'zindex':20}]},'_links':{'first':{'href':'http://localhost/widgets?page=1&size=1&sort=zindex,asc'},'self':{'href':'http://localhost/widgets?page=2&size=1&sort=zindex,asc'},'next':{'href':'http://localhost/widgets?page=3&size=1&sort=zindex,asc'},'last':{'href':'http://localhost/widgets?page=3&size=1&sort=zindex,asc'}},'page':{'size':1,'totalElements':3,'totalPages':3,'number':2}}", false
                        ));
        this.mockMvc.perform(get("/widgets?page=3&size=1&lowerX=0&lowerY=0&upperX=150&upperY=150"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content()
                        .json("{'_embedded':{'widgetList':[{'x':100,'y':100,'width':100.00,'height':100.00,'zindex':30}]},'_links':{'first':{'href':'http://localhost/widgets?page=1&size=1&sort=zindex,asc'},'self':{'href':'http://localhost/widgets?page=3&size=1&sort=zindex,asc'},'last':{'href':'http://localhost/widgets?page=3&size=1&sort=zindex,asc'}},'page':{'size':1,'totalElements':3,'totalPages':3,'number':3}}", false
                        ));
    }

    @Test
    void testUpdateWidget() throws Exception {
        Widget save = widgetJpaDao.save(getWidget());
        this.mockMvc.perform(patch("/widget/" + save.getId()).content("{\"x\":100,\"y\":100,\"width\":150,\"height\":150,\"zindex\":20}")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content()
                        .json("{'x':100,'y':100,'width':150,'height':150,'zindex':20}", false
                        ));
    }

    @Test
    void testDeleteWidget() throws Exception {
        Widget save = widgetJpaDao.save(getWidget());
        this.mockMvc.perform(delete("/widget/" + save.getId()))
                .andDo(print())
                .andExpect(status().isNoContent());

        Assert.assertFalse(widgetJpaDao.findById(save.getId())
                .isPresent());
    }


}