package com.bizkicks.backend.dto;

import java.util.List;

import lombok.Getter;

@Getter
public class ListDto<T> {
    private List<T> list;

    public void ListDto(List<T> list){
        this.list = list;
    }

}
