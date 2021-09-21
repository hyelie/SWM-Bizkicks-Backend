package com.bizkicks.backend.service;

import com.bizkicks.backend.dto.ContractDto;
import com.bizkicks.backend.entity.*;
import com.bizkicks.backend.exception.CustomException;
import com.bizkicks.backend.exception.ErrorCode;
import com.bizkicks.backend.repository.BrandRepository;
import com.bizkicks.backend.repository.CustomerCompanyRepository;
import com.bizkicks.backend.repository.MembershipRepository;
import com.bizkicks.backend.repository.PlanRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class PlanService {

    @Autowired CustomerCompanyRepository customerCompanyRepository;
    @Autowired PlanRepository planRepository;
    @Autowired BrandRepository brandRepository;
    @Autowired MembershipRepository membershipRepository;

    @Transactional
    public List<Plan> findPlan(CustomerCompany customerCompany){
        if(customerCompany == null) throw new CustomException(ErrorCode.COMPANY_NOT_EXIST);
        return planRepository.planfindByCustomerCompany(customerCompany);
    }

    @Transactional
    public void savePlan(CustomerCompany customerCompany, ContractDto<ContractDto.PlanPostDto> planDto){
        if(customerCompany == null) throw new CustomException(ErrorCode.COMPANY_NOT_EXIST);

        List<Plan> plans = new ArrayList<>();
        for (ContractDto.PlanPostDto planPostDto: planDto.getList()) {
            KickboardBrand kickboardBrand = brandRepository.findByBrandName(planPostDto.getBrandname());
            Plan plan = Plan.builder()
                    .type(planDto.getType())
                    .totalTime(planPostDto.getTotaltime())
                    .usedTime(0)
                    .status("Active")
                    .startDate(planDto.getStartdate())
                    .customerCompany(customerCompany)
                    .kickboardBrand(kickboardBrand)
                    .build();
            plans.add(plan);

        }
        planRepository.saveAllPlan(plans);
    }


    @Transactional
    public void updatePlan(CustomerCompany customerCompany, ContractDto<ContractDto.PlanPostDto> planDto) {
        if(customerCompany == null) throw new CustomException(ErrorCode.COMPANY_NOT_EXIST);

        for (ContractDto.PlanPostDto planPutDto: planDto.getList()) {
            String brandname = planPutDto.getBrandname();
            KickboardBrand kickboardBrand = brandRepository.findByBrandName(brandname);
            planRepository.updatePlan(customerCompany, kickboardBrand,planPutDto.getTotaltime());
        }

    }

    @Transactional
    public void delete(CustomerCompany customerCompany, List list) {
        if(customerCompany == null) throw new CustomException(ErrorCode.COMPANY_NOT_EXIST);

        for (Object companyName : list) {
            KickboardBrand kickboardBrand = brandRepository.findByBrandName((String) companyName);
            planRepository.delete(customerCompany ,kickboardBrand);

        }

    }
}
