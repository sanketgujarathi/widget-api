package com.miro.assignment.controller;


import com.miro.assignment.domain.Widget;
import com.miro.assignment.domain.WidgetFilterCriteria;
import com.miro.assignment.domain.WidgetRequest;
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

import java.math.BigInteger;
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

    @PostMapping("/widgets")
    public ResponseEntity<EntityModel<Widget>> createWidget(@RequestBody WidgetRequest widget) {
        log.info("Received request to create widget at {}, {}", widget.getX(), widget.getY());
        Widget response = widgetService.createWidget(widget);
        log.info("Created new widget with id: {}", response.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(resourceAssembler.toModel(response));
    }

    @GetMapping("/widgets")
    public ResponseEntity<PagedModel<EntityModel<Widget>>> getAllWidgets(@PageableDefault(page = 1, size = 10)
                                                                         @SortDefault.SortDefaults({
                                                                                 @SortDefault(sort = "zindex", direction = Sort.Direction.ASC)}) Pageable pageable
            , PagedResourcesAssembler<Widget> assembler
            , WidgetFilterCriteria criteria) {
        log.info("Received request to get widgets for page: {}", pageable.getPageNumber());
        Page<Widget> page = widgetService.getAllWidgets(pageable, Optional.of(criteria));
        log.info("Retrieved {} widgets for page: {}", page.getNumberOfElements(), pageable.getPageNumber());
        return ResponseEntity.ok(assembler.toModel(page, resourceAssembler));
    }

    @GetMapping("/widget/{id}")
    public ResponseEntity<EntityModel<Widget>> getWidget(@PathVariable BigInteger id) {
        log.info("Received request to get widget details for id: {}", id);
        Optional<Widget> foundWidget = widgetService.getWidget(id);
        return foundWidget
                .map(resourceAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound()
                        .build());
    }

    @PatchMapping("/widget/{id}")
    public ResponseEntity<EntityModel<Widget>> updateWidget(@PathVariable("id") BigInteger id, @RequestBody WidgetRequest widgetRequest) {
        log.info("Received request to update widget details for id: {}", id);
        Optional<Widget> updateWidget = widgetService.updateWidget(id, widgetRequest);
        return updateWidget
                .map(resourceAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound()
                        .build());
    }

    @DeleteMapping("/widget/{id}")
    public ResponseEntity deleteWidget(@PathVariable BigInteger id) {
        log.info("Received request to delete widget for id: {}", id);
        widgetService.deleteWidget(id);
        return ResponseEntity.noContent().build();
    }

}
