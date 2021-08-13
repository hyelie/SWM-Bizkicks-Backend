package com.bizkicks.backend;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.ActiveProfiles;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import com.bizkicks.backend.entity.CustomerCompany;
import com.bizkicks.backend.repository.CustomerCompanyRepository;

import lombok.NoArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;

@SpringBootTest
@Transactional
@NoArgsConstructor
public class CustomerCompanyTest {
    @PersistenceContext
    private EntityManager em;
    @Autowired CustomerCompanyRepository customerCompanyRepository;
    CustomerCompany samsung;
    CustomerCompany lg;
    CustomerCompany space;


    @BeforeEach
	void initialize(){
		samsung = CreateCustomerCompany("asdfg", "삼성");
        lg = CreateCustomerCompany("qwerty", "LG");
        space = CreateCustomerCompany("qwerty1", "spa ce");
	}

    public CustomerCompany CreateCustomerCompany(String companyCode, String companyName){
		CustomerCompany customerCompany = new CustomerCompany(companyName, companyCode);
        em.persist(customerCompany);
		return customerCompany;
	}


    @Test
	void is_company_exist(){
        // when
        CustomerCompany isSamsungExist = customerCompanyRepository.findByCustomerCompanyName("삼성");
        CustomerCompany isLgExist = customerCompanyRepository.findByCustomerCompanyName("LG");
        CustomerCompany isSpaceExist = customerCompanyRepository.findByCustomerCompanyName("spa ce");
        CustomerCompany isAsdfExist = customerCompanyRepository.findByCustomerCompanyName("asdf");

		// then
		Assertions.assertThat(isSamsungExist).isEqualTo(samsung);
		Assertions.assertThat(isLgExist).isEqualTo(lg);
		Assertions.assertThat(isAsdfExist).isEqualTo(null);
        Assertions.assertThat(isSpaceExist).isEqualTo(space);
	}
}
