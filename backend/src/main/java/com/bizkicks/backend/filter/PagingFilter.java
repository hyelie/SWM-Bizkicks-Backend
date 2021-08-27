package com.bizkicks.backend.filter;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PagingFilter {
    Integer page;
    Integer unit;
}
