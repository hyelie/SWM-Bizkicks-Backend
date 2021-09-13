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
public class ContractDto<T>{

    private String type;
    private LocalDate startdate;
    private List<T> list;
    private LocalDate duedate;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class PlanGetDto{
        private String company_name;
        private LocalDate start_date;
        private Integer price_per_hour;
        private Set<String> districts = new HashSet<>();
        private boolean insurance;
        private boolean helmet;
        private Integer used_time;
        private Integer total_time;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class MembershipGetDto{

        private String company_name;
        private Set<String> districts = new HashSet<>();
        private boolean insurance;
        private boolean helmet;
        private Integer used_time;

    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class PlanPostDto{
        private String brandname;
        private Integer totaltime;
    }

}
