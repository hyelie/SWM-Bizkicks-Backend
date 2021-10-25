package com.bizkicks.backend.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsageDto {

    private Integer page;
    private Integer unit;
    private LocalTime total_time;
    private List<UsageDto.Detail> history;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Detail{
        private Long memberId;
        private List<UsageDto.UseDetail> memberDetail;

    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class UseDetail {
        private String brand;
        private LocalDateTime depart_time;
        private LocalDateTime arrive_time;
        private List<ConsumptionDto.Location> location_list;
        private Integer cycle;
    }

}
