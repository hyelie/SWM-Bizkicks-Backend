package com.bizkicks.backend.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@IdClass(AlarmId.class)
@NoArgsConstructor
public class Alarm {
    @Id
    @ManyToOne
    @JoinColumn(name="customer_company_name")
    public CustomerCompany customerCompany;

    @Id
    @Column(length=45, nullable=false)
    private String type;

    @Id
    @Column(nullable=false)
    private Integer value;

    public void setRelationWithCustomerCompany(CustomerCompany customerCompany){
        this.customerCompany = customerCompany;
    }

    public Alarm(String type, Integer value){
        this.type = type;
        this.value = value;
    }

    public boolean equals(Object obj){
        if(obj == this) return true;
        Alarm a = (Alarm) obj;
        return a.type.equals(this.type)
                && a.value.equals(this.value)
                && a.customerCompany.equals(this.customerCompany);
    }

}
