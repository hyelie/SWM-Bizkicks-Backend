package com.bizkicks.backend.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor
public class CustomerCompany {

//    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "customer_company_id")
//    private Long id;

    @Id
    @Column(name = "customer_company_name", length = 45, nullable = false)
    private String companyName;

    @Column(length = 45, nullable = false)
    private String companyCode;

    @OneToMany(mappedBy = "customerCompany")
    private List<ContractRelation> contractRelations = new ArrayList<>();

    @OneToMany(mappedBy = "customerCompany")
    private List<Alarm> alarms = new ArrayList<>();

    public CustomerCompany(String companyCode, String companyName){
        this.companyCode = companyCode;
        this.companyName = companyName;
    }

    public CustomerCompany(String companyName){
        this.companyName = companyName;
    }

}


