package com.bizkicks.backend.api;

import com.bizkicks.backend.dto.AlarmDto;
import com.bizkicks.backend.dto.BrandDto;
import com.bizkicks.backend.dto.ListDto;
import com.bizkicks.backend.entity.KickboardBrand;
import com.bizkicks.backend.exception.CustomException;
import com.bizkicks.backend.exception.ErrorCode;
import com.bizkicks.backend.service.BrandService;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ProductApi {

    @Autowired private BrandService brandService;

    @GetMapping("/manage/products")
    public ResponseEntity<Object> showAllBrand(){
        List<KickboardBrand> kickboardBrands = brandService.findAllBrand();
        List<BrandDto> collect = kickboardBrands.stream()
                .map(m -> new BrandDto(m.getId(),m.getHelmet(),m.getInsurance(),m.getPricePerHour(),m.getImage1(),m.getImage2(),m.getImage3(),m.getText(),m.getBrandName(),m.getDistricts()))
                .collect(Collectors.toList());
        ListDto<BrandDto> result = ListDto.<BrandDto>builder().list(collect).build();

        return new ResponseEntity<Object>(result, HttpStatus.OK);
    }

    @GetMapping("/manage/products/{brandName}")
    public ResponseEntity<Object> showBrand(@PathVariable(value="brandName", required = false) String brandName){
        KickboardBrand kickboardBrand = brandService.findBrand(brandName);
        if(kickboardBrand==null) throw new CustomException(ErrorCode.KICKBOARD_BRAND_NOT_EXIST);

        return new ResponseEntity<Object>(kickboardBrand, HttpStatus.OK);
    }
}