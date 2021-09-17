package com.bizkicks.backend.util;


import java.util.Optional;

import com.bizkicks.backend.auth.entity.Member;
import com.bizkicks.backend.auth.repository.MemberRepository;
import com.bizkicks.backend.entity.CustomerCompany;
import com.bizkicks.backend.entity.KickboardBrand;
import com.bizkicks.backend.exception.CustomException;
import com.bizkicks.backend.exception.ErrorCode;
import com.bizkicks.backend.repository.CustomerCompanyRepository;
import com.bizkicks.backend.repository.KickboardBrandRepository;

import org.springframework.stereotype.Component;

@Component
public class GetWithNullCheck {
    public Member getMemberById(MemberRepository memberRepository, Long id){
        Optional<Member> member = memberRepository.findById(id);
        if(!member.isPresent()) throw new CustomException(ErrorCode.MEMBER_NOT_EXIST);
        return member.get();
    }

    public Member getMemberByMemberId(MemberRepository memberRepository, String memberId){
        Optional<Member> member = memberRepository.findByMemberId(memberId);
        if(!member.isPresent()) throw new CustomException(ErrorCode.MEMBER_NOT_EXIST);
        return member.get();
    }

    public Member getMemberByMemberIdWithCustomerCompany(MemberRepository memberRepository, String memberId){
        Optional<Member> member = memberRepository.findByMemberIdWithCustomerCompany(memberId);
        if(!member.isPresent()) throw new CustomException(ErrorCode.MEMBER_NOT_EXIST);
        return member.get();
    }

    public Member getMemberByPhoneNumber(MemberRepository memberRepository, String phoneNumber){
        Optional<Member> member = memberRepository.findByPhoneNumber(phoneNumber);
        if(!member.isPresent()) throw new CustomException(ErrorCode.MEMBER_NOT_EXIST);
        return member.get();
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