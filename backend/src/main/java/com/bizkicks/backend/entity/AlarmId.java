package com.bizkicks.backend.entity;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor @AllArgsConstructor
public class AlarmId implements Serializable{
    private CustomerCompany customerCompany;
    private String type;
    private Integer value;

    public boolean equals(Object obj){
        if(obj == this) return true;
        AlarmId aid = (AlarmId) obj;
        return aid.type.equals(this.type)
                && aid.value.equals(this.value)
                && aid.customerCompany.equals(this.customerCompany);
    }
}
