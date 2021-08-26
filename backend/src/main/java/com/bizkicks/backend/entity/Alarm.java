package com.bizkicks.backend.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(
    indexes = @Index(name="customer_company_index", columnList = "customer_company_id"),
    uniqueConstraints={
        @UniqueConstraint(columnNames = {"type", "value", "customer_company_id"})
    }

)
public class Alarm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="type", length=5, nullable=false)
    private String type;

    @Column(name="value", nullable=false)
    private Integer value;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="customer_company_id")
    public CustomerCompany customerCompany;

    public void setRelationWithCustomerCompany(CustomerCompany customerCompany){
        this.customerCompany = customerCompany;
    }

    public Alarm(String type, Integer value){
        this.type = type;
        this.value = value;
    }
    
    public Alarm(String type, Integer value, CustomerCompany customerCompany){
        this.type = type;
        this.value = value;
        this.customerCompany = customerCompany;
    }
}
