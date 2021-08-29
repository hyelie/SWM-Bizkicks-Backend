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
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConsumptionDto {
    private Integer page;
    private Integer unit;
    private LocalTime total_time;
    private List<Detail> history;

    @Getter
    @Setter
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
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Location{
        private Double latitude;
        private Double longitude;
    }
}