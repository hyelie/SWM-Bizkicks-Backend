package com.bizkicks.backend.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class CustomerCompany {

//    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "customer_company_id")
//    private Long id;

    @Id
    @Column(name = "customer_company_name", length = 45, nullable = false)
    private String company_name;

    @Column(length = 45, nullable = false)
    private String company_code;

    @OneToMany(mappedBy = "customerCompany")
    private List<ContractRelation> contractRelations = new ArrayList<>();

    @OneToMany(mappedBy = "customerCompany")
    private List<Alarm> alarms = new ArrayList<>();

    public CustomerCompany(String company_code, String company_name){
        System.out.println("entity contsructor : " + company_name);
        this.company_code = company_code;
        this.company_name = company_name;
    }

}


