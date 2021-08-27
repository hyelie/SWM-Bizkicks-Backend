package com.bizkicks.backend.dto;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import com.bizkicks.backend.entity.Consumption;
import com.bizkicks.backend.entity.Coordinate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Builder
@AllArgsConstructor
public class ConsumptionDto {
    private Integer unit;
    private LocalTime total_time;
    private List<Detail> history;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Detail{
        private String brand;
        private LocalDateTime depart_time;
        private LocalDateTime arrive_time;
        private List<Location> location_list;
        private Integer cycle;
        
        public List<Coordinate> toCoordinateEntity(){
            List<Coordinate> coordinates = new ArrayList<>();
            Long i = Long.valueOf(0);
            for(Location location : this.location_list){
                coordinates.add(Coordinate.builder()
                                            .latitude(location.getLatitude())
                                            .longitude(location.getLongitude())
                                            .sequence(i)
                                            .build()
                );
                i++;
            }
            return coordinates;
        }
        public Consumption toConsumptionEntity(){
            return Consumption.builder()
                                .departTime(this.depart_time)
                                .arriveTime(this.arrive_time)
                                .cycle(this.cycle)
                                .build();
        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Location{
        private Double latitude;
        private Double longitude;
    }
}

/*

1. get

1) repository에서

SELECT * FROM
  (SELECT * FROM consumption
    WHERE user_id=1
    LIMIT 0, 2) con
  LEFT JOIN
  coordinate cor
  ON con.consumption_id=cor.consumption_id
ORDER BY con.consumption_id ASC, cor.sequence ASC


이런식으로 해서 entity 2개를 가져옴. 리턴 형식은 List<Object[]>

2) service에서
repository에서 받은 List<Object[]>를 파싱
Map<consumption, List<coordinate>>에다가 넣어서
consumption 1개당 list를 가질 수 있도록 함.
그 과정에서 중복을 없애고, 데이터를 다듬는 비즈니스 로직을 수행하는 것임.
리턴 형식은 Map<consumption, List<coordinate>>

3) controller에서
service에서 받은 map을 dto 형식으로 변환함.

비록 변환은 2번이지만 entity에만 의존하게 함.

*/