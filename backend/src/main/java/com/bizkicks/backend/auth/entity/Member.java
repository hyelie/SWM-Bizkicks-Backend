package com.bizkicks.backend.auth.entity;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.bizkicks.backend.entity.CustomerCompany;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(
    indexes = @Index(name="member_index", columnList = "member_set_id")
)
public class Member {
    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="member_set_id", length=20, nullable = false, unique = true)
    private String memberId;

    @Column(length=100, nullable = false)
    private String password;

    @Column(length=30)
    private String name;

    @Column
    private Boolean license;

    @Column(length=40)
    private String email;

    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="customer_company_id")
    private CustomerCompany customerCompany;

    public void setPassword(String password){
        this.password = password;
    }

    public void setRelationWithCustomerCompany(CustomerCompany customerCompany){
        this.customerCompany = customerCompany;
    }

    @Builder
    public Member(String memberId, String password, Boolean license, String email, UserRole userRole, String name){
        this.memberId = memberId;
        this.password = password;
        this.license = license;
        this.email = email;
        this.userRole = userRole;
        this.name = name;
    }

    public UsernamePasswordAuthenticationToken toAuthentication(){
        return new UsernamePasswordAuthenticationToken(memberId, password);
    }
}
