package com.bizkicks.backend.dto;

import java.util.HashSet;
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
    private Long id;
    private boolean helmet;
    private boolean insurance;
    private Integer pricePerHour;
    private String image1;
    private String image2;
    private String image3;
    private String Text;
    private String brandName;
    private Set<String> districts = new HashSet<>();
}