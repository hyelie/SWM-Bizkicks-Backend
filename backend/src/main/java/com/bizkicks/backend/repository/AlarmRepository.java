package com.bizkicks.backend.repository;

import com.bizkicks.backend.entity.Alarm;
import com.bizkicks.backend.entity.CustomerCompany;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class AlarmRepository {

    @PersistenceContext
    private EntityManager em;

    public CustomerCompany isCustomerCompanyExist(String customerCompanyName){
        String verifyCustomerCompanyQuery = "select cc from CustomerCompany cc where cc.companyName = :customer_company_name";
        List<CustomerCompany> customerCompany = em.createQuery(verifyCustomerCompanyQuery, CustomerCompany.class)
                                            .setParameter("customer_company_name", customerCompanyName)
                                            .getResultList();
        if(customerCompany.size() == 0) return null;
        else return customerCompany.get(0); 
    }

    public void deleteAllAlarmsInCustomerCompany(CustomerCompany customerCompany){
        String DeleteCustomerCompanyAlarmsQuery = "delete from Alarm a where a.customerCompany = :customer_company";
        em.createQuery(DeleteCustomerCompanyAlarmsQuery)
          .setParameter("customer_company", customerCompany)
          .executeUpdate();
    }

    public void saveAllAlarmsInCustomerCompany(CustomerCompany customerCompany, List<Alarm> alarms){
        for (Alarm alarm : alarms) {
            alarm.setRelationWithCustomerCompany(customerCompany);
            em.persist(alarm);
        }
    }

    public List<Alarm> findByCustomerCompany(CustomerCompany customerCompany){
        String selectCustomerCompanyAlarmsQuery = "select a from Alarm a where a.customerCompany = :customer_company";
        List<Alarm> alarms = em.createQuery(selectCustomerCompanyAlarmsQuery , Alarm.class)
                               .setParameter("customer_company", customerCompany)
                               .getResultList();
        return alarms;
    }

}
