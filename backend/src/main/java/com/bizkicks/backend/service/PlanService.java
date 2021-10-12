package com.bizkicks.backend.service;

import com.bizkicks.backend.dto.ContractDto;
import com.bizkicks.backend.entity.*;
import com.bizkicks.backend.exception.CustomException;
import com.bizkicks.backend.exception.ErrorCode;
import com.bizkicks.backend.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Autowired KickboardRepository kickboardRepository;

    @Transactional
    public List<Plan> findPlan(CustomerCompany customerCompany){
        if(customerCompany == null) throw new CustomException(ErrorCode.COMPANY_NOT_EXIST);
        return planRepository.findPlanByCustomerCompany(customerCompany);
    }

    @Transactional
    public void savePlan(CustomerCompany customerCompany, ContractDto<ContractDto.PlanPostDto> planDto){
        if(customerCompany == null) throw new CustomException(ErrorCode.COMPANY_NOT_EXIST);

        customerCompanyRepository.updateCompanyTypeToPlan(customerCompany.getCompanyName());

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
    public void updatePlan(CustomerCompany customerCompany, ContractDto<ContractDto.PlanPostDto> planDto) {
        if(customerCompany == null) throw new CustomException(ErrorCode.COMPANY_NOT_EXIST);

        for (ContractDto.PlanPostDto planPutDto: planDto.getList()) {
            String brandname = planPutDto.getBrandname();
            KickboardBrand kickboardBrand = kickboardBrandRepository.findByBrandName(brandname);
            planRepository.updatePlanInCustomerCompany(customerCompany, kickboardBrand,planPutDto.getTotaltime());
        }
    }

    // list가 어떤 것의 list인지 있었으면 좋을 듯.
    @Transactional
    public void delete(CustomerCompany customerCompany, List list) {
        if(customerCompany == null) throw new CustomException(ErrorCode.COMPANY_NOT_EXIST);

        for (Object companyName : list) {
            KickboardBrand kickboardBrand = kickboardBrandRepository.findByBrandName((String) companyName);
            planRepository.deleteInCustomerCompany(customerCompany ,kickboardBrand);
        }
    }

    @Transactional
    public void addUsedTime(CustomerCompany customerCompany, String brandname, Long betweenTime) {
        // 여기도 예외처리(company 없는경우)
        KickboardBrand kickboardBrand = kickboardBrandRepository.findByBrandName(brandname);
        int betweenTimetoInt = betweenTime.intValue();
        planRepository.addUsedTimeInCustomerCompany(customerCompany, kickboardBrand, betweenTimetoInt);
    }


    public Plan findPlanByBrandAndCompany(CustomerCompany customerCompany, Long kickboardId) {

        Kickboard kickboard = kickboardRepository.findById(kickboardId);
        KickboardBrand kickboardBrand = kickboard.getKickboardBrand();
        Plan plan = planRepository.findPlanByBrandAndCompany(customerCompany, kickboardBrand);

        return plan;

    }
}
