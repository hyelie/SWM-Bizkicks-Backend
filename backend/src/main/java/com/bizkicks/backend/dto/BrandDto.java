package com.bizkicks.backend.dto;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BrandDto {
    private boolean helmet;
    private boolean insurance;
    private Integer price_per_hour;
    private List<String> images;
    private String text;
    private String brandName;
    private Set<String> service_location = new HashSet<>();
}