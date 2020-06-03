package com.springboard.backend.controller;

import com.springboard.backend.dto.HTMLDTO;
import com.springboard.backend.dto.UsersDTO;
import com.springboard.backend.model.HtmlcodeDrive;
import com.springboard.backend.service.HtmlService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URISyntaxException;

@Api(tags = {"Html API"})
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/htmlcode", produces = MediaTypes.HAL_JSON_VALUE)
public class HtmlController {

    @Autowired
    private HtmlService htmlService;

    @Autowired
    private HtmlResourceAssembler htmlResourceAssembler;

    @PostMapping
    public ResponseEntity<EntityModel<HTMLDTO.Response>> insertHtmlcode(@RequestBody @Valid HTMLDTO.Create htmldto)  {
        HTMLDTO.Response  response = htmlService.htmlcodeInsert(htmldto);
        EntityModel<HTMLDTO.Response> resource = htmlResourceAssembler.toModel(response);
        return ResponseEntity.ok(resource);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<HTMLDTO.Response>> getHtmlcode(@PathVariable final Long id) {
        HTMLDTO.Response response = htmlService.htmlcodeSend(id);
        EntityModel<HTMLDTO.Response> resource = htmlResourceAssembler.toModel(response);
        return ResponseEntity.ok(resource);
    }
}
