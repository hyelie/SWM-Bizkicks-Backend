package com.bizkicks.backend.dto;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Builder
public class ResourceDto {
    Resource resource;
    HttpHeaders httpHeaders;
    HttpStatus httpStatus;
}
