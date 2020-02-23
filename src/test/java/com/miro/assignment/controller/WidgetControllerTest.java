package com.miro.assignment.controller;

import com.miro.assignment.domain.Widget;
import com.miro.assignment.domain.WidgetFilterCriteria;
import com.miro.assignment.domain.WidgetRequest;
import com.miro.assignment.domain.WidgetResourceAssembler;
import com.miro.assignment.service.WidgetService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class WidgetControllerTest {

    private final String WIDGET_OUTPUT = "{" +
            "'id': 123," +
            "'x': 50," +
            "'y': 50," +
            "'width': 100," +
            "'height': 100," +
            "'zindex': 10," +
            "'lastModifiedDate': '2020-02-17T23:49:27.055'," +
            "'_links': {" +
            "  'self': {" +
            "    'href': 'http://localhost/widget/123'" +
            "  }," +
            "  'update': {" +
            "    'href': 'http://localhost/widget/123'" +
            "  }," +
            "  'delete': {" +
            "    'href': 'http://localhost/widget/123'" +
            "  }," +
            "  'widgets': {" +
            "    'href': 'http://localhost/widgets'" +
            "  }" +
            "}" +
            "}";
    @Autowired
    WidgetResourceAssembler widgetResourceAssembler;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private WidgetService widgetService;

    @Test
    void testCreateWidget_HappyPath() throws Exception {
        when(widgetService.createWidget(any(WidgetRequest.class))).thenReturn(getWidget());
        this.mockMvc.perform(post("/widget")
                .content("{\"x\":50,\"y\":50,\"width\":100,\"height\":100,\"zindex\":10}")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content()
                        .json(WIDGET_OUTPUT
                        ));
    }

    @Test
    void testCreateWidget_BadRequest() throws Exception {
        when(widgetService.createWidget(any(WidgetRequest.class))).thenReturn(getWidget());
        this.mockMvc.perform(post("/widget")
                .content("{\"x\":50,\"y\":50,\"width\":-100,\"height\":100,\"zindex\":10}")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
        this.mockMvc.perform(post("/widget")
                .content("{\"x\":50,\"y\":50,\"width\":100,\"height\":-100,\"zindex\":10}")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void getAllWidgets_HappyPath() throws Exception {

        when(widgetService.getWidgetsByFilterCriteria(any(Pageable.class), any(WidgetFilterCriteria.class))).thenReturn(getPage());
        this.mockMvc.perform(get("/widgets?page=1&size=10&lowerX=0&lowerY=0&upperX=200&upperY=150"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content()
                        .json("{'_embedded':{'widgetList':[{'id':123,'x':50,'y':50,'width':100,'height':100,'zindex':10,'lastModifiedDate':'2020-02-17T23:49:27.055','_links':{'self':{'href':'http://localhost/widget/123'},'update':{'href':'http://localhost/widget/123'},'delete':{'href':'http://localhost/widget/123'},'widgets':{'href':'http://localhost/widgets'}}}]},'_links':{'self':{'href':'http://localhost/widgets?page=1&size=10&sort=zindex,asc'}},'page':{'size':10,'totalElements':1,'totalPages':1,'number':1}}"
                        ));
    }

    @Test
    void testGetWidget_HappyPath() throws Exception {

        when(widgetService.getWidget(any(BigInteger.class))).thenReturn(Optional.of(getWidget()));
        this.mockMvc.perform(get("/widget/123"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content()
                        .json(WIDGET_OUTPUT
                        ));
    }

    @Test
    void testGetWidget_NotFound() throws Exception {

        when(widgetService.getWidget(any(BigInteger.class))).thenReturn(Optional.empty());
        this.mockMvc.perform(get("/widget/111"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetWidget_BadRequest() throws Exception {

        when(widgetService.getWidget(any(BigInteger.class))).thenReturn(Optional.empty());
        this.mockMvc.perform(get("/widget/12x"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateWidget_HappyPath() throws Exception {

        when(widgetService.updateWidget(any(BigInteger.class), any(WidgetRequest.class))).thenReturn(Optional.of(getWidget()));
        this.mockMvc.perform(patch("/widget/123").content("{\"x\":50,\"y\":50,\"width\":100,\"height\":100,\"zindex\":20}")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content()
                        .json(WIDGET_OUTPUT
                        ));
    }

    @Test
    void testUpdateWidget_NotFound() throws Exception {

        when(widgetService.updateWidget(any(BigInteger.class), any(WidgetRequest.class))).thenReturn(Optional.empty());
        this.mockMvc.perform(patch("/widget/111")
                .content("{\"x\":50,\"y\":50,\"width\":100,\"height\":100,\"zindex\":10}")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void testUpdateWidget_BadRequest() throws Exception {

        when(widgetService.updateWidget(any(BigInteger.class), any(WidgetRequest.class))).thenReturn(Optional.empty());
        this.mockMvc.perform(patch("/widget/111")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void deleteWidget_HappyPath() throws Exception {
        this.mockMvc.perform(delete("/widget/123"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteWidget_NotFound() throws Exception {
        this.mockMvc.perform(delete("/widget/123"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteWidget_BadRequest() throws Exception {
        this.mockMvc.perform(delete("/widget/12x"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    private Widget getWidget() {
        Widget widget = new Widget();
        widget.setId(BigInteger.valueOf(123l));
        widget.setX(50);
        widget.setY(50);
        widget.setZindex(10);
        widget.setHeight(BigDecimal.valueOf(100));
        widget.setWidth(BigDecimal.valueOf(100));
        widget.setLastModifiedDate(LocalDateTime.parse("2020-02-17T23:49:27.055"));
        return widget;
    }

    private Page<Widget> getPage() {
        Sort sort = Sort.by(Sort.Direction.ASC, "zindex");
        Pageable pageable = PageRequest.of(0, 10, sort);
        return new PageImpl<>(Arrays.asList(getWidget()), pageable, 0);
    }
}