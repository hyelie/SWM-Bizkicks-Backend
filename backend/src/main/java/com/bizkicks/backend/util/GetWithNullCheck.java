package com.bizkicks.backend.util;


import com.bizkicks.backend.entity.CustomerCompany;
import com.bizkicks.backend.entity.KickboardBrand;
import com.bizkicks.backend.entity.Member;
import com.bizkicks.backend.exception.CustomException;
import com.bizkicks.backend.exception.ErrorCode;
import com.bizkicks.backend.repository.CustomerCompanyRepository;
import com.bizkicks.backend.repository.KickboardBrandRepository;
import com.bizkicks.backend.repository.MemberRepository;

import org.springframework.stereotype.Component;

@Component
public class GetWithNullCheck {
    public Member getMember(MemberRepository memberRepository, Long memberId){
        Member member = memberRepository.findById(memberId);
        if(member == null) throw new CustomException(ErrorCode.MEMBER_NOT_EXIST);
        return member;
    }

    public KickboardBrand getKickboardBrand(KickboardBrandRepository kickboardBrandRepository, String brandName){
        KickboardBrand kickboardBrand = kickboardBrandRepository.findByBrandName(brandName);
        if(kickboardBrand == null) throw new CustomException(ErrorCode.KICKBOARD_BRAND_NOT_EXIST);
        return kickboardBrand;
    }

    public CustomerCompany getCustomerCompany(CustomerCompanyRepository customerCompanyRepository, String customerCompanyName){
        CustomerCompany customerCompany = customerCompanyRepository.findByCustomerCompanyName(customerCompanyName);
        if(customerCompany == null) throw new CustomException(ErrorCode.COMPANY_NOT_EXIST);
        return customerCompany;
    }
    

   


}