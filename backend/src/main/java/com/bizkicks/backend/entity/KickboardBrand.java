package com.bizkicks.backend.entity;

import lombok.Builder;
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

    private Boolean helmet;

    private Boolean insurance;

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

    @Builder
    public KickboardBrand(boolean helmet, boolean insurance, Integer pricePerHour, String image1, String image2, String image3, String Text, String brandName){
        this.helmet = helmet;
        this.insurance = insurance;
        this.pricePerHour = pricePerHour;
        this.image1 = image1;
        this.image2 = image2;
        this.image3 = image3;
        this.Text = Text;
        this.brandName = brandName;
    }

}
