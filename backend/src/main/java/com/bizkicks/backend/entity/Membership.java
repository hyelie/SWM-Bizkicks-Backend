package com.bizkicks.backend.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Membership {

    @Id
    @Column(name = "membership_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;

    private LocalDate startDate;

    private LocalDate duedate;

    private Integer usedTime;

    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="customer_company_id")
    public CustomerCompany customerCompany;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="brand_id")
    public KickboardBrand kickboardBrand;

}
