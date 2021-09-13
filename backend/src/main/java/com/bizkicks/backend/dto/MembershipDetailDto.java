package com.bizkicks.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MembershipDetailDto {

    private String companyName;
    private Set<String> districts = new HashSet<>();
    private boolean insurance;
    private boolean helmet;
    private Integer usedTime;

}
