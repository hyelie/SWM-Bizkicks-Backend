package com.bizkicks.backend.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class CustomerCompany {
    @Id
    @Column(name = "customer_company_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length=45, nullable = false, unique=true)
    private String companyName;

    @Column(length=45, nullable=false, unique=true)
    private String companyCode;

    public CustomerCompany(String companyName){
        this.companyName = companyName;
    }

    public CustomerCompany(String companyName, String companyCode){
        this.companyName = companyName;
        this.companyCode = companyCode;
    }

    public void setCompanyName(String companyName){
        this.companyName = companyName;
    }
}
