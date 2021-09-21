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
}