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
public class PlanDto {

    private String type;
    private LocalDate startdate;
    private List<Detail> kickboardBrand;
    private LocalDate duedate;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Detail{

        private String companyName;
        private Integer pricerPerHour;
        private Set<String> districts = new HashSet<>();
        private boolean insurance;
        private boolean helmet;
        private Integer usedTime;
        private Integer totalTime;

    }


}
