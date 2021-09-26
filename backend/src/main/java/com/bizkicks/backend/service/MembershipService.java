package com.bizkicks.backend.service;

import com.bizkicks.backend.dto.ContractDto;
import com.bizkicks.backend.entity.CustomerCompany;
import com.bizkicks.backend.entity.KickboardBrand;
import com.bizkicks.backend.entity.Membership;
import com.bizkicks.backend.entity.Plan;
import com.bizkicks.backend.exception.CustomException;
import com.bizkicks.backend.exception.ErrorCode;
import com.bizkicks.backend.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class MembershipService {

    @Autowired CustomerCompanyRepository customerCompanyRepository;
    @Autowired PlanRepository planRepository;
    @Autowired KickboardBrandRepository kickboardBrandRepository;
    @Autowired MembershipRepository membershipRepository;

    @Transactional
    public void saveMembership(CustomerCompany customerCompany, ContractDto contractMembership){
        if(customerCompany == null) throw new CustomException(ErrorCode.COMPANY_NOT_EXIST);
        List<KickboardBrand> all = kickboardBrandRepository.findAll();

        customerCompanyRepository.updateTypeMembership(customerCompany.getCompanyName());

        List<Membership> memberships = new ArrayList<>();
        for (KickboardBrand kickboardBrand : all) {

            Membership membership = Membership.builder()
                    .type(contractMembership.getType())
                    .startDate(contractMembership.getStartdate())
                    .duedate(contractMembership.getDuedate())
                    .usedTime(0)
                    .status("Active")
                    .customerCompany(customerCompany)
                    .kickboardBrand(kickboardBrand)
                    .build();
            memberships.add(membership);
        }

        membershipRepository.saveAll(memberships);

    }

    @Transactional
    public void updateMembership(CustomerCompany customerCompany, ContractDto contractMembership) {
        if(customerCompany == null) throw new CustomException(ErrorCode.COMPANY_NOT_EXIST);
        membershipRepository.update(customerCompany, contractMembership.getStartdate(), contractMembership.getDuedate());
    }

    @Transactional
    public void delete(CustomerCompany customerCompany) {
        if(customerCompany == null) throw new CustomException(ErrorCode.COMPANY_NOT_EXIST);
        membershipRepository.delete(customerCompany);
    }

    @Transactional
    public List<Membership> findMembership(CustomerCompany customerCompany) {
        if(customerCompany == null) throw new CustomException(ErrorCode.COMPANY_NOT_EXIST);
        return membershipRepository.membershipFindByCustomerCompany(customerCompany);
    }

    @Transactional
    public void addUsedTime(CustomerCompany customerCompany, String brandname, Long betweenTime) {

        KickboardBrand kickboardBrand = kickboardBrandRepository.findByBrandName(brandname);
        int betweenTimetoInt = betweenTime.intValue();
        membershipRepository.addUsedTime(customerCompany, kickboardBrand, betweenTimetoInt);

    }
}

