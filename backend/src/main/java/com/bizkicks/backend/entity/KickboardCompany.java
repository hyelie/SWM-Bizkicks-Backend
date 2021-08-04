package com.bizkicks.backend.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class KickboardCompany {

    String company_name;

    Long price_per_Hour;

    List<String> service_location;

    boolean insurance;

    boolean helmet;

}
