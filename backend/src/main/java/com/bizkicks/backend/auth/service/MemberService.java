package com.bizkicks.backend.auth.service;

import com.bizkicks.backend.auth.entity.Member;
import com.bizkicks.backend.auth.repository.MemberRepository;
import com.bizkicks.backend.auth.security.util.SecurityUtil;
import com.bizkicks.backend.entity.CustomerCompany;
import com.bizkicks.backend.util.GetWithNullCheck;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service

public class MemberService {
    @Autowired private MemberRepository memberRepository;
    @Autowired private GetWithNullCheck getWithNullCheck;

    @Transactional(readOnly = true)
    public Member getCurrentMemberInfo(){
        return getWithNullCheck.getMemberByMemberIdWithCustomerCompany(memberRepository, SecurityUtil.getCurrentMemberId());
    }

    public List<Member> findAllMemberByCompany(CustomerCompany customerCompany){

        return memberRepository.findByCustomerCompany(customerCompany);
    }
}
