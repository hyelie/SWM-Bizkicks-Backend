package com.bizkicks.backend.entity;

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

import com.bizkicks.backend.auth.utility.UserRole;

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

    @Column(length=45, nullable = false)
    private String password;

    @Column(length=9, nullable = false)
    private String type;

    @Column
    private Boolean license;

    @Column(length=15)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="customer_company_id")
    private CustomerCompany customerCompany;

    public void setRelationWithCustomerCompany(CustomerCompany customerCompany){
        this.customerCompany = customerCompany;
    }

    @Builder
    public Member(String memberId, String password, String type, Boolean license, String phoneNumber){
        this.memberId = memberId;
        this.password = password;
        this.type = type;
        this.license = license;
        this.phoneNumber = phoneNumber;
    }
}
