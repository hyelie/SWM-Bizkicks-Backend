package com.bizkicks.backend.service;

import com.bizkicks.backend.entity.Alarm;
import com.bizkicks.backend.entity.CustomerCompany;
import com.bizkicks.backend.entity.Kickboard;
import com.bizkicks.backend.entity.KickboardBrand;
import com.bizkicks.backend.exception.CustomException;
import com.bizkicks.backend.exception.ErrorCode;
import com.bizkicks.backend.repository.CustomerCompanyRepository;
import com.bizkicks.backend.repository.KickboardRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class KickboardService {

    @Autowired KickboardRepository kickboardRepository;
    @Autowired CustomerCompanyRepository customerCompanyRepository;

    public List<Kickboard> findKickboards(CustomerCompany customerCompany){
        return kickboardRepository.findKickboardByCustomerCompany(customerCompany);
    }

    public List<Kickboard> findAllKickboards() {

        return kickboardRepository.findAllKickboards();

    }
}
