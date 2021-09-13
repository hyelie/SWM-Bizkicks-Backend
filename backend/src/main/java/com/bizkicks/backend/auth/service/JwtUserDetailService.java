package com.bizkicks.backend.auth.service;

import java.util.ArrayList;
import java.util.List;

import com.bizkicks.backend.auth.utility.UserRole;
import com.bizkicks.backend.entity.Member;
import com.bizkicks.backend.repository.MemberRepository;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.jackson2.SimpleGrantedAuthorityMixin;

public class JwtUserDetailService implements UserDetailsService{
    
    @Autowired
    private MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String memberId) throws UsernameNotFoundException{
        Member member = memberRepository.findByMemberId(memberId);

        List<GrantedAuthority> roles = new ArrayList<>();

        if(member == null){
            throw new UsernameNotFoundException("user not found");
        }
        
        roles.add(new SimpleGrantedAuthority(member.getUserRole().toString()));
        return new User(member.getMemberId(), member.getPassword(), roles);
    }
}
