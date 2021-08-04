package com.bizkicks.backend.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class KickboardCompany {

    String companyname;

    Long pricePerHour;

    List<String> servicelocation;

    boolean license;

    boolean helmet;

}
