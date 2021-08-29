package com.bizkicks.backend.api;

import com.bizkicks.backend.dto.AlarmDto;
import com.bizkicks.backend.dto.ListDto;
import com.bizkicks.backend.entity.KickboardBrand;
import com.bizkicks.backend.exception.CustomException;
import com.bizkicks.backend.exception.ErrorCode;
import com.bizkicks.backend.service.BrandService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ProductApi {

    private final BrandService brandService;

    @GetMapping("/manage/products")
    public Result allBrand(){
        List<KickboardBrand> kickboardBrands = brandService.findAllBrand();
        List<BrandDto> collect = kickboardBrands.stream()
                .map(m -> new BrandDto(m.getId(),m.getHelmet(),m.getInsurance(),m.getPricePerHour(),m.getImage1(),m.getImage2(),m.getImage3(),m.getText(),m.getBrandName(),m.getDistricts()))
                .collect(Collectors.toList());

        return new Result(collect);
    }

    @GetMapping("/manage/products1")
    public List<KickboardBrand> kick1(){
        return brandService.findAllBrand();
    }

    @Data
    @AllArgsConstructor
    class Result<T>{
        private T list;
    }


    @Data
    @AllArgsConstructor
    class BrandDto {
        private Long id;
        private boolean helmet;
        private boolean insurance;
        private Integer pricePerHour;
        private String image1;
        private String image2;
        private String image3;
        private String Text;
        private String brandName;
        private Set<String> districts = new HashSet<>();
    }

}