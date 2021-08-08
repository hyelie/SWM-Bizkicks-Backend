package com.bizkicks.backend.dto;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AlarmDto {

    public void AlarmDto(String type, Integer value){

        this.type = type;
        this.value = value;
    }

    private String type;
    private Integer value;

}
