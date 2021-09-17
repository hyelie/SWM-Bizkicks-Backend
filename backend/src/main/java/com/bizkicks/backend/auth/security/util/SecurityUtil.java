package com.bizkicks.backend.auth.security.util;

import com.bizkicks.backend.auth.entity.Member;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {
    public SecurityUtil() {}

    public static String getCurrentMemberId(){
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication == null || authentication.getName() == null){
            throw new RuntimeException("security context에 인증 정보 없음");
        }
        return authentication.getName();
    }
}
