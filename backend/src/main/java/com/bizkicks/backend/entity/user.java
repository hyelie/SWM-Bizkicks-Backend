package com.bizkicks.backend.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(
    indexes = @Index(name="user_index", columnList = "user_set_id")
)
public class User {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="user_set_id", length=20, nullable = false, unique = true)
    private String userId;

    @Column(length=45, nullable = false)
    private String password;

    @Column(length=9, nullable = false)
    private String type;

    @Column
    private boolean license;

    @Column(length=15)
    private String phoneNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="customer_company_id")
    private CustomerCompany customerCompany;

    public void setRelationWithCustomerCompany(CustomerCompany customerCompany){
        this.customerCompany = customerCompany;
    }

    @Builder
    public User(String userId, String password, String type, boolean license, String phoneNumber){
        this.userId = userId;
        this.password = password;
        this.type = type;
        this.license = license;
        this.phoneNumber = phoneNumber;
    }
}
