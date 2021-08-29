package com.bizkicks.backend.filter;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class DateFilter {
    LocalDateTime startDate;
    LocalDateTime endDate;
}
