package com.springboard.backend.service;

import com.springboard.backend.dto.HTMLDTO;
import com.springboard.backend.mapper.HtmlMapper;
import com.springboard.backend.model.HtmlcodeDrive;
import com.springboard.backend.repository.HtmlcodeJpaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional
@Service
public class HtmlService {

    @Autowired
    HtmlcodeJpaRepository htmlcodeJpaRepository;

    private HTMLDTO.Response toResource(HtmlcodeDrive htmlcodeDrive) {
        return HtmlMapper.HTML_MAPPER.toDto(htmlcodeDrive);
    }

    public HTMLDTO.Response htmlcodeInsert(HTMLDTO.Create dto) {
        HtmlcodeDrive htmlcodeDrive = HtmlcodeDrive.builder().htmlcode(dto.getHtmlcode()).build();
        return toResource(htmlcodeJpaRepository.save(htmlcodeDrive));
    }

    public HTMLDTO.Response htmlcodeSend(Long id) {
        return toResource(htmlcodeJpaRepository.findById(id).orElseThrow(() -> new AuthenticationCredentialsNotFoundException(
                "Authentication credentials not found exception " + id)));
    }
}
