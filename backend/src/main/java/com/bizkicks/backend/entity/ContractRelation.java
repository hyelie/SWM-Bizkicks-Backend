package com.bizkicks.backend.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter @Setter
public class ContractRelation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime duedate;

    private String type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_company_id")
    private CustomerCompany customerCompany; //주문회원

    //연관관계 메서드//
    public void setRelationWithCustomerCompany(CustomerCompany customerCompany){
        this.customerCompany = customerCompany;
        customerCompany.getContractRelations().add(this);
    }

    public ContractRelation(LocalDateTime duedate, String type){

        this.duedate = duedate;
        this.type = type;

    }

}
