package com.bizkicks.backend.auth.dto;

import com.bizkicks.backend.auth.entity.Member;
import com.bizkicks.backend.entity.UserRole;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberDto {
    private String memberId;
    private String name;
    private String password;
    private Boolean license;
    private UserRole userRole;
    private String phoneNumber;
    private String customerCompanyName;
    private String customerCompanyCode;

    public Member toEntity(PasswordEncoder passwordEncoder){
        return Member.builder()
                        .memberId(memberId)
                        .password(passwordEncoder.encode(password))
                        .name(name)
                        .license(license)
                        .phoneNumber(phoneNumber)
                        .userRole(userRole)
                        .build();
    }

    public UsernamePasswordAuthenticationToken toAuthentication(){
        return new UsernamePasswordAuthenticationToken(memberId, password);
    }
}
