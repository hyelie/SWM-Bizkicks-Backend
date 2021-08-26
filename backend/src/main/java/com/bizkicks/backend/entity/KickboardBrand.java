package com.bizkicks.backend.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class KickboardBrand {

    @Id
    @Column(name = "brand_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean helmet;

    private boolean insurance;

    private Integer pricePerHour;

    private String image1;

    private String image2;

    private String image3;

    private String Text;

    private String brandName;

    @ElementCollection
    @CollectionTable(name = "SERVICE_LOCATION",
            joinColumns = @JoinColumn(name = "brand_id"))
    @Column(name="DISTRICT")
    private Set<String> districts = new HashSet<>();

}
