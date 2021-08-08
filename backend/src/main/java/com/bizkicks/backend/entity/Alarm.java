package com.bizkicks.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Alarm {

    @Id @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private Integer value;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_company_name")
    private CustomerCompany customerCompany;

    public void setRelationWithCustomerCompany(CustomerCompany customerCompany){
        this.customerCompany = customerCompany;
        customerCompany.getAlarms().add(this);
    }

    public Alarm(String type, Integer value){
        this.type = type;
        this.value = value;
    }
}
