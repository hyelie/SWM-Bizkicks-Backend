package com.bizkicks.backend.dto;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;
import java.util.List;
import com.bizkicks.backend.entity.Alarm;
import com.bizkicks.backend.entity.CustomerCompany;


@Data
@NoArgsConstructor
public class AlarmDto {
    private String type;
    private Integer value;
    
    public AlarmDto(String type, Integer value){
        this.type = type;
        this.value = value;
    }

    public Alarm toEntity(){
        return new Alarm(this.type, this.value);
    }

}