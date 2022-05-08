package com.netflix.hexagonal.domain.exception.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ApiErrorDTO {
    private Date timestamp;
    private Integer status;
    private String code;
    private List<ErrorDTO> errors;

}
