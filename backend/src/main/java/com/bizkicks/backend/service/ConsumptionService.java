package com.bizkicks.backend.service;

import java.util.List;

import javax.transaction.Transactional;

import com.bizkicks.backend.entity.Consumption;
import com.bizkicks.backend.entity.Coordinate;
import com.bizkicks.backend.entity.KickboardBrand;
import com.bizkicks.backend.entity.User;
import com.bizkicks.backend.exception.CustomException;
import com.bizkicks.backend.exception.ErrorCode;
import com.bizkicks.backend.filter.DateFilter;
import com.bizkicks.backend.filter.PagingFilter;
import com.bizkicks.backend.repository.ConsumptionRepository;
import com.bizkicks.backend.repository.CoordinateRepository;
import com.bizkicks.backend.repository.KickboardBrandRepository;
import com.bizkicks.backend.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor
public class ConsumptionService {
    @Autowired ConsumptionRepository consumptionRepository;
    @Autowired CoordinateRepository coordinateRepository;
    @Autowired KickboardBrandRepository kickboardRepository;
    @Autowired UserRepository userRepository;

    @Transactional
    public void saveConsumptionWithCoordinates(Long userId, String brandName, Consumption consumption, List<Coordinate> coordinates){
        KickboardBrand kickboardBrand = kickboardRepository.findByBrandName(brandName);
        if(kickboardBrand == null) throw new CustomException(ErrorCode.KICKBOARD_BRAND_NOT_EXIST);
        consumption.setRelationWithKickboardBrand(kickboardBrand);

        User user = userRepository.findById(userId);
        if(user == null) throw new CustomException(ErrorCode.USER_NOT_EXIST);
        consumption.setRelationWithUser(user);

        consumptionRepository.save(consumption);
        coordinateRepository.saveAllCoordinatesInConsumption(consumption, coordinates);
    }

    // @Transactional
    // public void showConsumptionWithCoordinate(DateFilter dateFilter, PagingFilter pagingFilter){

    // }
}
