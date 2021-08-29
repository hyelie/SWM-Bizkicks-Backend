package com.bizkicks.backend.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.bizkicks.backend.entity.CustomerCompany;

import org.springframework.stereotype.Repository;

@Repository
public class CustomerCompanyRepository {
    @PersistenceContext
    private EntityManager em;

    public void save(CustomerCompany customerCompany){
        em.persist(customerCompany);
    }


    public CustomerCompany findByCustomerCompanyName(String customerCompanyName){
        String verifyCustomerCompanyQuery = "select cc from CustomerCompany cc where cc.companyName = :customer_company_name";
        List<CustomerCompany> customerCompany = em.createQuery(verifyCustomerCompanyQuery, CustomerCompany.class)
                                            .setParameter("customer_company_name", customerCompanyName)
                                            .getResultList();
        if(customerCompany.isEmpty()) return null;
        else return customerCompany.get(0); 
    }
    
}
