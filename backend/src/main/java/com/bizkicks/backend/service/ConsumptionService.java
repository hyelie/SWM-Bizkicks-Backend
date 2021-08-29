package com.bizkicks.backend.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
import com.bizkicks.backend.util.GetWithNullCheck;

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
    @Autowired GetWithNullCheck getWithNullCheck;

    @Transactional
    public void saveConsumptionWithCoordinates(Long userId, String brandName, Consumption consumption, List<Coordinate> coordinates){
        KickboardBrand kickboardBrand = getWithNullCheck.getKickboardBrand(kickboardRepository, brandName);
        consumption.setRelationWithKickboardBrand(kickboardBrand);

        User user = getWithNullCheck.getUser(userRepository, userId);
        consumption.setRelationWithUser(user);

        consumptionRepository.save(consumption);
        coordinateRepository.saveAllCoordinatesInConsumption(consumption, coordinates);
    }

    @Transactional
    public LinkedHashMap<Consumption, List<Coordinate>> findConsumptionWithCoordinate(Long userId, DateFilter dateFilter, PagingFilter pagingFilter){
        User user = getWithNullCheck.getUser(userRepository, userId);
        List<Consumption> consumptions = consumptionRepository.findByFilter(user, dateFilter, pagingFilter);
        List<Coordinate> coordinates = coordinateRepository.findCoordinatesInConsumptions(consumptions);

        // 찾은 consumptions, coordinate를 삽입
        LinkedHashMap<Long, List<Coordinate>> coordinateMap = new LinkedHashMap<Long, List<Coordinate>>();
        for(Coordinate coordinate : coordinates){
            Long consumptionId = coordinate.getConsumption().getId();
            List<Coordinate> coordinateMapList = coordinateMap.get(consumptionId);
            if(coordinateMapList == null){
                coordinateMapList = new ArrayList<Coordinate>();
                coordinateMapList.add(coordinate);
                coordinateMap.put(consumptionId, coordinateMapList);
            }
            else{
                if(!coordinateMapList.contains(coordinate)) coordinateMapList.add(coordinate);
            }
        }

        LinkedHashMap<Consumption, List<Coordinate>> mapCoordinateToConsumption = new LinkedHashMap<Consumption, List<Coordinate>>();
        for(Consumption consumption : consumptions){
            mapCoordinateToConsumption.put(consumption, coordinateMap.get(consumption.getId()));
        }

        return mapCoordinateToConsumption;
    }
}
