package com.springboard.backend.mapper;

import com.springboard.backend.dto.HTMLDTO;
import com.springboard.backend.model.HtmlcodeDrive;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface HtmlMapper extends EntityMapper<HTMLDTO.Response, HtmlcodeDrive> {

    HtmlMapper HTML_MAPPER = Mappers.getMapper(HtmlMapper.class);
}
