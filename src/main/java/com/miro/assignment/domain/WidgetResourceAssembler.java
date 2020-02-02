package com.miro.assignment.domain;

import com.miro.assignment.controller.WidgetController;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class WidgetResourceAssembler implements RepresentationModelAssembler<Widget, EntityModel<Widget>> {
    @Override
    public EntityModel<Widget> toModel(Widget entity) {
        return  new EntityModel<>(entity, getSingleItemLinks(entity.getId()));

    }

    @Override
    public CollectionModel<EntityModel<Widget>> toCollectionModel(Iterable<? extends Widget> entities) {
        return new CollectionModel<>(StreamSupport.stream(entities.spliterator(), false).map(this::toModel).collect(Collectors.toList()));
    }

    private List<Link> getSingleItemLinks(int id) {

        return Arrays.asList(linkTo(methodOn(WidgetController.class).getWidget(id)).withSelfRel(),
                linkTo(methodOn(WidgetController.class).updateWidget(id, new Widget())).withRel("update"),
                linkTo(methodOn(WidgetController.class).deleteWidget(id)).withRel("delete"),
                linkTo(methodOn(WidgetController.class).getAllWidgets(PageRequest.of(1, 5), null, new WidgetFilterCriteria(0,0,0,0))).withRel("widgets"));
    }
}
