package com.bizkicks.backend.dto;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ConsumptionDto {
    private Integer unit;
    private LocalTime total_time;
    private List<Detail> history;

    @Getter
    @AllArgsConstructor
    @Builder
    public static class Detail{
        private String brand;
        private LocalDateTime depart_time;
        private LocalDateTime arrive_time;
        private List<Location> location_list;
        private Integer interval;

        // public List<Coordinate> toCoordinateEntity(){
        //     List<Coordinate> coordinates = ArrayList<>();
        //     for(Location location : location_list){
        //         coordinates.add(new Coordinate(location.getLatitude(), location.getLongitude()));
        //     }
        //     return coordinates;
        // }

        // public Consumption toConsumptionEntity(){
        //     return new Consumption()
        // }
    }

    @Getter
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
SELECT con, cor FROM Consumption con LEFT JOIN coordinate cor
ORDER BY con.arrive_time desc cor.sequence ASC
LIMIT 0, 10

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


2. post

post에서 consumptionDto.Detail을 받음
service에서 list는 쪼갤 수 있음.

coordinate의 경우에도 usage_id가 있고 -> 이거는 entity의 id값을 get해와서 toEntity()를 하면 될 것 같다.
consumption의 경우에도 brand_id가 있음 -> 이거는 brand_name으로 찾아야 하고 찾을 걸 넣어주면 될 것 같다.

*/