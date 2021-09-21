package com.bizkicks.backend.auth.security.util;

import com.bizkicks.backend.auth.entity.Member;
import com.bizkicks.backend.exception.CustomException;
import com.bizkicks.backend.exception.ErrorCode;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {
    public SecurityUtil() {}

    public static String getCurrentMemberId(){
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication == null || authentication.getName() == null){
            throw new CustomException(ErrorCode.NO_UNAUTHORIZED);
        }
        return authentication.getName();
    }
}
