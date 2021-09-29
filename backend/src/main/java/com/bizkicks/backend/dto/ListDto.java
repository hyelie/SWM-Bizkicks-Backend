package com.bizkicks.backend.dto;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
public class ListDto<T> {
    private List<T> list;

    public ListDto(List<T> list){
        this.list = list;
    }

}
