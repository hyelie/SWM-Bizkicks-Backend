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
        String findByCustomerCompanyNameQuery = "select cc from CustomerCompany cc where cc.companyName = :customer_company_name";
        List<CustomerCompany> customerCompany = em.createQuery(findByCustomerCompanyNameQuery, CustomerCompany.class)
                                            .setParameter("customer_company_name", customerCompanyName)
                                            .getResultList();
        if(customerCompany.isEmpty()) return null;
        else return customerCompany.get(0); 
    }

    public CustomerCompany findByCustomerCompanyCode(String customerCompanyCode){
        String findByCustomerCompanyCodeQuery = "select cc from CustomerCompany cc where cc.companyCode = :customer_company_code";
        List<CustomerCompany> customerCompany = em.createQuery(findByCustomerCompanyCodeQuery, CustomerCompany.class)
                                            .setParameter("customer_company_code", customerCompanyCode)
                                            .getResultList();
        if(customerCompany.isEmpty()) return null;
        else return customerCompany.get(0); 
    }
    
    public void updateTypeMembership(String companyName) {

        // 가능하면 query가 어떤 동작을 할 건지 변수명으로 나타내면 좋을 듯.
        String qlString = "update CustomerCompany c " +
                "set c.type = :type "+
                "where c.companyName = :companyName";
        em.createQuery(qlString)
                .setParameter("type", "membership")
                .setParameter("companyName", companyName)
                .executeUpdate();

    }

    public void updateTypePlan(String companyName) {

        // 가능하면 query가 어떤 동작을 할 건지 변수명으로 나타내면 좋을 듯.
        String qlString = "update CustomerCompany c " +
                "set c.type = :type " +
                "where c.companyName = :companyName";
        em.createQuery(qlString)
                .setParameter("type", "plan")
                .setParameter("companyName", companyName)
                .executeUpdate();

    }
}
