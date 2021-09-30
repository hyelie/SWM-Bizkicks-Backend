package com.bizkicks.backend.service;

import com.bizkicks.backend.entity.Kickboard;
import com.bizkicks.backend.entity.KickboardBrand;
import com.bizkicks.backend.repository.KickboardBrandRepository;
import com.bizkicks.backend.repository.KickboardRepository;
import com.univocity.parsers.common.record.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class CsvService {

    @Autowired KickboardBrandRepository kickboardBrandRepository;
    @Autowired KickboardRepository kickboardRepository;

    @Transactional
    public void save(List<Record> parseAllRecords) {

        List<Kickboard> kickboards = new ArrayList<>();

        for (Record record : parseAllRecords) {
            Map<Integer, String> latMap = record.toIndexMap(1);
            Map<Integer, String> lngMap = record.toIndexMap(2);
            Map<Integer, String> batteryMap = record.toIndexMap(3);
            Map<Integer, String> modelMap = record.toIndexMap(4);
            Map<Integer, String> pastMap = record.toIndexMap(5);
            Map<Integer, String> nameMap = record.toIndexMap(6);

            String lat1 = latMap.get(1);
            String lng1 = lngMap.get(2);
            String battery1 = batteryMap.get(3);
            String model = modelMap.get(4);
            String pastPicture = pastMap.get(5);
            String companyName = nameMap.get(6);

            KickboardBrand kickboardBrand = kickboardBrandRepository.findByBrandName(companyName);

            int battery = Integer.parseInt(battery1);
            double lat = Double.parseDouble(lat1);
            double lng = Double.parseDouble(lng1);

            Kickboard kickboard = Kickboard.builder()
                    .kickboardBrand(kickboardBrand)
                    .pastPicture(pastPicture)
                    .model(model)
                    .battery(battery)
                    .lat(lat)
                    .lng(lng)
                    .build();

            kickboards.add(kickboard);
        }

        kickboardRepository.saveAll(kickboards);


    }
}
