package com.springboard.backend.dto;

import com.springboard.backend.model.UserRole;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class HTMLDTO {

    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    @Builder
    public static class Create {

        @NotEmpty
        private String htmlcode;

    }

    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    @Builder
    public static class Response {

        private Integer id;

        @NotEmpty
        private String htmlcode;
    }
}
