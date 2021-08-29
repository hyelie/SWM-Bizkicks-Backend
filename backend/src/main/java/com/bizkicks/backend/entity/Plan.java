package com.bizkicks.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Plan {

    @Id
    @Column(name = "plan_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;

    private Integer TotalTime;

    private Integer UsedTime;

    private LocalDate startDate;

    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="customer_company_id")
    public CustomerCompany customerCompany;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="brand_id")
    public KickboardBrand kickboardBrand;
}
