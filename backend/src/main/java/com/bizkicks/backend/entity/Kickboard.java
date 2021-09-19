package com.bizkicks.backend.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Kickboard {

    @Id
    @Column(name = "kickboard_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double lat;

    private Double lng;

    private Integer battery;

    private String model;

    private String pastPicture;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    private KickboardBrand kickboardBrand;

}
