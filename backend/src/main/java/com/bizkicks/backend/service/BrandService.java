package com.bizkicks.backend.service;

import com.bizkicks.backend.entity.KickboardBrand;
import com.bizkicks.backend.repository.BrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BrandService {

    @Autowired final BrandRepository brandRepository;


    public List<KickboardBrand> findAllBrand(){
        return brandRepository.findAll();
    }
}