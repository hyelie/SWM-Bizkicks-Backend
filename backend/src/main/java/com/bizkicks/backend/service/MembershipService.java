package com.bizkicks.backend.service;

import com.bizkicks.backend.dto.ContractDto;
import com.bizkicks.backend.entity.CustomerCompany;
import com.bizkicks.backend.entity.KickboardBrand;
import com.bizkicks.backend.entity.Membership;
import com.bizkicks.backend.entity.Plan;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class MembershipService {

    @Autowired CustomerCompanyRepository customerCompanyRepository;
    @Autowired PlanRepository planRepository;
    @Autowired BrandRepository brandRepository;
    @Autowired MembershipRepository membershipRepository;

    @Transactional
    public void saveMembership(String companyName, ContractDto contractMembership){

        CustomerCompany customerCompany = customerCompanyRepository.findByCustomerCompanyName(companyName);
        if(customerCompany == null) throw new CustomException(ErrorCode.COMPANY_NOT_EXIST);
        List<KickboardBrand> all = brandRepository.findAll();

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
    public void updateMembership(String belongCompany, ContractDto contractMembership) {

        CustomerCompany customerCompany = customerCompanyRepository.findByCustomerCompanyName(belongCompany);
        if(customerCompany == null) throw new CustomException(ErrorCode.COMPANY_NOT_EXIST);
        membershipRepository.update(customerCompany, contractMembership.getStartdate(), contractMembership.getDuedate());

        }

    @Transactional
    public void delete(String belongCompany) {

        CustomerCompany customerCompany = customerCompanyRepository.findByCustomerCompanyName(belongCompany);
        if(customerCompany == null) throw new CustomException(ErrorCode.COMPANY_NOT_EXIST);
        membershipRepository.delete(customerCompany);
    }

    @Transactional
    public List<Membership> findMembership(String customerCompanyName) {

        CustomerCompany customerCompany = customerCompanyRepository.findByCustomerCompanyName(customerCompanyName);
        if(customerCompany == null) throw new CustomException(ErrorCode.COMPANY_NOT_EXIST);
        return membershipRepository.membershipFindByCustomerCompany(customerCompany);

    }
}

