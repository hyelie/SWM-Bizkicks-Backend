package com.bizkicks.backend.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class CustomerCompany {
    @Id
    @Column(length=45, name="customer_company_name")
    private String companyName;

    @Column(length=45, nullable=false)
    private String companyCode;

    public CustomerCompany(String companyName){
        this.companyName = companyName;
    }

    public CustomerCompany(String companyName, String companyCode){
        this.companyName = companyName;
        this.companyCode = companyCode;
    }
}
