package com.bizkicks.backend.auth.service;

import java.util.Collections;

import javax.transaction.Transactional;

import com.bizkicks.backend.auth.entity.Member;
import com.bizkicks.backend.auth.repository.MemberRepository;
import com.bizkicks.backend.util.GetWithNullCheck;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService{
    @Autowired private MemberRepository memberRepository;
    @Autowired private GetWithNullCheck getWithNullCheck;

    private UserDetails createUserDetails(Member member){
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(member.getUserRole().toString());
        System.out.println(grantedAuthority.getAuthority());
        return new User(String.valueOf(member.getMemberId()), member.getPassword(), Collections.singleton(grantedAuthority));
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        return memberRepository.findByMemberIdWithCustomerCompany(username)
                                    .map(this::createUserDetails)
                                    .orElseThrow(()-> new UsernameNotFoundException(username + " -> db에 없음"));
        
    }
}
