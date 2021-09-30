package com.bizkicks.backend.api;

import com.bizkicks.backend.dto.BrandDto;
import com.bizkicks.backend.dto.ListDto;
import com.bizkicks.backend.entity.KickboardBrand;
import com.bizkicks.backend.exception.CustomException;
import com.bizkicks.backend.exception.ErrorCode;
import com.bizkicks.backend.service.BrandService;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductApi {

    @Autowired private BrandService brandService;

    @GetMapping("/manage/products")
    public ResponseEntity<Object> showAllBrand(){
        List<KickboardBrand> kickboardBrands = brandService.findAllBrand();
        List<BrandDto> brandDtos = new ArrayList<>();
        for(KickboardBrand kickboardBrand : kickboardBrands){
            BrandDto brandDto = BrandDto.builder()
                                        .brandName(kickboardBrand.getBrandName())
                                        .price_per_hour(kickboardBrand.getPricePerHour())
                                        .service_location(kickboardBrand.getDistricts())
                                        .insurance(kickboardBrand.getInsurance())
                                        .helmet(kickboardBrand.getHelmet())
                                        .build();
            brandDtos.add(brandDto);
        }
        ListDto<BrandDto> result = ListDto.<BrandDto>builder().list(brandDtos).build();

        // List<BrandDto> collect = kickboardBrands.stream()
        //         .map(m -> new BrandDto(m.getId(),m.getHelmet(),m.getInsurance(),m.getPricePerHour(),m.getImage1(),m.getImage2(),m.getImage3(),m.getText(),m.getBrandName(),m.getDistricts()))
        //         .collect(Collectors.toList());

        return new ResponseEntity<Object>(result, HttpStatus.OK);
    }

    @GetMapping("/manage/products/{brandName}")
    public ResponseEntity<Object> showBrand(@PathVariable(value="brandName", required = false) String brandName) throws IOException {
        KickboardBrand kickboardBrand = brandService.findBrand(brandName);
        if(kickboardBrand==null) throw new CustomException(ErrorCode.KICKBOARD_BRAND_NOT_EXIST);

        List<String> encodedImages = brandService.findEncodedImages(kickboardBrand);

        BrandDto brandDto = BrandDto.builder()
                                    .brandName(kickboardBrand.getBrandName())
                                    .text(kickboardBrand.getText())
                                    .helmet(kickboardBrand.getHelmet())
                                    .insurance(kickboardBrand.getInsurance())
                                    .price_per_hour(kickboardBrand.getPricePerHour())
                                    .service_location(kickboardBrand.getDistricts())
                                    .images(encodedImages)
                                    .build();

        return new ResponseEntity<Object>(brandDto, HttpStatus.OK);
    }
}