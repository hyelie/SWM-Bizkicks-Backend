package com.bizkicks.backend.entity;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class KickboardLocation {

    private String company_name;

    private double lat;

    private double lng;

    private Long battery;

    private String model;

    private String img_url;

}
