package com.bizkicks.backend.auth.dto;

import com.bizkicks.backend.auth.entity.Member;
import com.bizkicks.backend.auth.entity.UserRole;

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
    private String id;
    private String name;
    private String password;
    private Boolean license;
    private UserRole user_role;
    private String phone_number;
    private String company_name;
    private String company_code;

    public Member toEntity(PasswordEncoder passwordEncoder){
        return Member.builder()
                        .memberId(id)
                        .password(passwordEncoder.encode(password))
                        .name(name)
                        .license(license)
                        .phoneNumber(phone_number)
                        .userRole(user_role)
                        .build();
    }

    public UsernamePasswordAuthenticationToken toAuthentication(){
        return new UsernamePasswordAuthenticationToken(id, password);
    }
}
