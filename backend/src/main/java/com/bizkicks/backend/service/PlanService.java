package com.bizkicks.backend.service;

import com.bizkicks.backend.dto.ContractDto;
import com.bizkicks.backend.entity.*;
import com.bizkicks.backend.exception.CustomException;
import com.bizkicks.backend.exception.ErrorCode;
import com.bizkicks.backend.repository.CustomerCompanyRepository;
import com.bizkicks.backend.repository.KickboardBrandRepository;
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
    @Autowired KickboardBrandRepository kickboardBrandRepository;
    @Autowired MembershipRepository membershipRepository;

    @Transactional
    public List<Plan> findPlan(String customerCompanyName){
        CustomerCompany customerCompany = customerCompanyRepository.findByCustomerCompanyName(customerCompanyName);
        if(customerCompany == null) throw new CustomException(ErrorCode.COMPANY_NOT_EXIST);
        return planRepository.planfindByCustomerCompany(customerCompany);
    }

    @Transactional
    public void savePlan(String companyName, ContractDto<ContractDto.PlanPostDto> planDto){

        CustomerCompany customerCompany = customerCompanyRepository.findByCustomerCompanyName(companyName);
        if(customerCompany == null) throw new CustomException(ErrorCode.COMPANY_NOT_EXIST);

        customerCompanyRepository.updateTypePlan(customerCompany.getCompanyName());

        List<Plan> plans = new ArrayList<>();
        for (ContractDto.PlanPostDto planPostDto: planDto.getList()) {
            KickboardBrand kickboardBrand = kickboardBrandRepository.findByBrandName(planPostDto.getBrandname());
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
    public void updatePlan(String belongCompany, ContractDto<ContractDto.PlanPostDto> planDto) {

        CustomerCompany customerCompany = customerCompanyRepository.findByCustomerCompanyName(belongCompany);
        if(customerCompany == null) throw new CustomException(ErrorCode.COMPANY_NOT_EXIST);

        for (ContractDto.PlanPostDto planPutDto: planDto.getList()) {

            String brandname = planPutDto.getBrandname();
            KickboardBrand kickboardBrand = kickboardBrandRepository.findByBrandName(brandname);
            planRepository.updatePlan(customerCompany, kickboardBrand,planPutDto.getTotaltime());




        }

    }

    @Transactional
    public void delete(String belongCompany, List list) {

        CustomerCompany customerCompany = customerCompanyRepository.findByCustomerCompanyName(belongCompany);
        if(customerCompany == null) throw new CustomException(ErrorCode.COMPANY_NOT_EXIST);

        for (Object companyName : list) {
            KickboardBrand kickboardBrand = kickboardBrandRepository.findByBrandName((String) companyName);
            planRepository.delete(customerCompany ,kickboardBrand);

        }

    }
}
