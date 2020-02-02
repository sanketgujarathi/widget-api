package com.miro.assignment.controller;


import com.miro.assignment.domain.Widget;
import com.miro.assignment.domain.WidgetFilterCriteria;
import com.miro.assignment.domain.WidgetResourceAssembler;
import com.miro.assignment.service.WidgetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.data.web.SortDefault;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class WidgetController {

    private static Logger log = LoggerFactory.getLogger(WidgetController.class);
    private final WidgetService widgetService;
    private final WidgetResourceAssembler resourceAssembler;

    public WidgetController(WidgetService widgetService, WidgetResourceAssembler resourceAssembler) {
        this.widgetService = widgetService;
        this.resourceAssembler = resourceAssembler;
    }

    @PostMapping("/widget")
    public Widget createWidget(@RequestBody Widget widget) {
        log.info("Received request to create widget at {}, {}", widget.getX(), widget.getY());
        Widget response = widgetService.createWidget(widget);
        log.info("Created widget {}", response.getId());
        return response;
    }

    @GetMapping("/widget/{id}")
    public ResponseEntity<EntityModel<Widget>> getWidget(@PathVariable int id) {
        Optional<Widget> foundWidget = widgetService.getWidget(id);
        return foundWidget
                .map(resourceAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound()
                        .build());
    }

    @GetMapping("/widgets")
    public ResponseEntity<PagedModel<EntityModel<Widget>>> getAllWidgets(@PageableDefault(page = 1, size = 10)
                                                                         @SortDefault.SortDefaults({
                                                                                 @SortDefault(sort = "zindex", direction = Sort.Direction.ASC)}) Pageable pageable, PagedResourcesAssembler<Widget> assembler, @RequestBody(required = false) WidgetFilterCriteria criteria) {
        Page<Widget> page = widgetService.getAllWidgets(pageable, criteria);
        return ResponseEntity.ok(assembler.toModel(page, resourceAssembler));
    }

    @PatchMapping("/widget/{id}")
    public ResponseEntity<EntityModel<Widget>> updateWidget(@PathVariable("id") int id, @RequestBody Widget widget) {
        Optional<Widget> updateWidget = widgetService.updateWidget(id, widget);
        return updateWidget
                .map(resourceAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound()
                        .build());
    }

    @DeleteMapping("/widget/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public ResponseEntity deleteWidget(@PathVariable int id) {
        widgetService.deleteWidget(id);
        return ResponseEntity.noContent()
                .build();
    }

}
