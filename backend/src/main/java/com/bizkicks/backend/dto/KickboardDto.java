package com.bizkicks.backend.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KickboardDto<T> {

    private List<T> list;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class LocationGetDto{
        private String company_name;
        private Double lat;
        private Double lng;
        private Integer battery;
        private String model;
        private String pastPicture;
    }

}
