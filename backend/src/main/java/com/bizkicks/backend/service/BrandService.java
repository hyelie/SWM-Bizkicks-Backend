package com.bizkicks.backend.service;

import com.bizkicks.backend.entity.KickboardBrand;
import com.bizkicks.backend.exception.CustomException;
import com.bizkicks.backend.exception.ErrorCode;
import com.bizkicks.backend.repository.KickboardBrandRepository;
import com.bizkicks.backend.util.GetWithNullCheck;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;

import java.util.Base64;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BrandService {

    @Autowired
    final KickboardBrandRepository kickboardBrandRepository;
    @Autowired private GetWithNullCheck getWithNullCheck;

    public KickboardBrand findBrand(String brandName){
        KickboardBrand kickboardBrand = getWithNullCheck.getKickboardBrand(kickboardBrandRepository, brandName);
        if(kickboardBrand == null) throw new CustomException(ErrorCode.KICKBOARD_BRAND_NOT_EXIST);
        return kickboardBrand;
    }

    public List<KickboardBrand> findAllBrand(){
        return kickboardBrandRepository.findAll();
    }

    public List<String> findEncodedImages(KickboardBrand kickboardBrand) throws IOException{
        List<String> images = new ArrayList<>();

        String basePath = "./images/brand/" + kickboardBrand.getBrandName() + "/";
        for(int i = 1; i<=3; i++){
            String currentPath = basePath + i +".jpg";
            File savedImage = new File(currentPath);
            byte[] fileContent = FileCopyUtils.copyToByteArray(savedImage);
            String encodedString = Base64.getEncoder().encodeToString(fileContent);  
            images.add(encodedString);
        }

        return images;
    }
}