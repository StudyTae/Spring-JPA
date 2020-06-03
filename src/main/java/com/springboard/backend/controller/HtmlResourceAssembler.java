package com.springboard.backend.controller;


import com.springboard.backend.dto.HTMLDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class HtmlResourceAssembler  implements RepresentationModelAssembler<HTMLDTO.Response, EntityModel<HTMLDTO.Response>> {

    @Override
    public EntityModel<HTMLDTO.Response> toModel(HTMLDTO.Response entity) {
        return  new EntityModel<>(
                entity,
                linkTo(methodOn(HtmlController.class).insertHtmlcode(HTMLDTO.Create.builder().build())).withSelfRel()
        );
    }
}
