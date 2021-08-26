package com.bizkicks.backend.dto;

import com.bizkicks.backend.entity.Membership;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Data
@RequiredArgsConstructor
public class MembershipDto {

    private String type;
    private LocalDate duedate;
    private LocalDate startDate;
    private Integer usedTime;
}
