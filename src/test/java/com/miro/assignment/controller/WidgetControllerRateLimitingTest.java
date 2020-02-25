package com.miro.assignment.controller;

import com.miro.assignment.domain.Widget;
import com.miro.assignment.domain.WidgetFilterCriteria;
import com.miro.assignment.domain.WidgetRequest;
import com.miro.assignment.domain.WidgetResourceAssembler;
import com.miro.assignment.service.WidgetService;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigInteger;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("ratelimit")
@Ignore("Run separately in isolation")
class WidgetControllerRateLimitingTest {

    @Autowired
    WidgetResourceAssembler widgetResourceAssembler;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private WidgetService widgetService;

    @Test
    void testCreateWidget_RateLimit() throws Exception {
        when(widgetService.createWidget(any(WidgetRequest.class))).thenReturn(new Widget());
        this.mockMvc.perform(post("/widget")
                .content("{\"x\":50,\"y\":50,\"width\":100,\"height\":100,\"zindex\":10}")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
        this.mockMvc.perform(post("/widget")
                .content("{\"x\":50,\"y\":50,\"width\":100,\"height\":100,\"zindex\":10}")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isTooManyRequests())
                .andExpect(header().exists("X-Rate-Limit-Retry-After-Seconds"));
    }

    @Test
    void getAllWidgets_RateLimit() throws Exception {
        when(widgetService.getWidgetsByFilterCriteria(any(Pageable.class), any(WidgetFilterCriteria.class))).thenReturn(Page.empty());
        this.mockMvc.perform(get("/widgets?page=1&size=10&lowerX=0&lowerY=0&upperX=200&upperY=150"))
                .andDo(print())
                .andExpect(status().isOk());
        this.mockMvc.perform(get("/widgets?page=1&size=10&lowerX=0&lowerY=0&upperX=200&upperY=150"))
                .andDo(print())
                .andExpect(status().isTooManyRequests())
                .andExpect(header().exists("X-Rate-Limit-Retry-After-Seconds"));
    }

    @Test
    void updateWidget_RateLimit() throws Exception {

        when(widgetService.updateWidget(any(BigInteger.class), any(WidgetRequest.class))).thenReturn(Optional.of(new Widget()));
        this.mockMvc.perform(patch("/widget/123").content("{\"x\":50,\"y\":50,\"width\":100,\"height\":100,\"zindex\":20}")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().longValue("X-Rate-Limit-Remaining", 0));
        this.mockMvc.perform(patch("/widget/123").content("{\"x\":50,\"y\":50,\"width\":100,\"height\":100,\"zindex\":20}")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isTooManyRequests())
                .andExpect(header().exists("X-Rate-Limit-Retry-After-Seconds"));
    }
    
    @Test
    void deleteWidget_RateLimit() throws Exception {
        this.mockMvc.perform(delete("/widget/123"))
                .andDo(print())
                .andExpect(status().isNoContent())
                .andExpect(header().longValue("X-Rate-Limit-Remaining", 0));
        this.mockMvc.perform(delete("/widget/123"))
                .andDo(print())
                .andExpect(status().isTooManyRequests())
                .andExpect(header().exists("X-Rate-Limit-Retry-After-Seconds"));
    }

}