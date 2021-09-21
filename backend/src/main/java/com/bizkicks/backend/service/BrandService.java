package com.bizkicks.backend.service;

import com.bizkicks.backend.entity.KickboardBrand;
import com.bizkicks.backend.repository.KickboardBrandRepository;
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

    public List<KickboardBrand> findAllBrand(){
        return kickboardBrandRepository.findAll();
    }
}