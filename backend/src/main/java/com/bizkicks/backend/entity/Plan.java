package com.bizkicks.backend.entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Plan {

    @Id
    @Column(name = "plan_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;

    private Integer totalTime;

    private Integer usedTime;

    private String status;

    private LocalDate startDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="customer_company_id")
    public CustomerCompany customerCompany;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="brand_id")
    public KickboardBrand kickboardBrand;

    public void setRelationWithCustomerCompany(CustomerCompany customerCompany){
        this.customerCompany = customerCompany;
    }

    public void setRelationWithKickboardBrand(KickboardBrand kickboardBrand){
        this.kickboardBrand = kickboardBrand;
    }

}
