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

    public void deleteAllAlarmsInCustomerCompany(CustomerCompany customerCompany){
        String DeleteCustomerCompanyAlarmsQuery = "DELETE from Alarm a where a.customerCompany = :customer_company";
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
        String selectCustomerCompanyAlarmsQuery = "SELECT a FROM Alarm a WHERE a.customerCompany = :customer_company";
        // FETCH JOIN으로 바꾸어야 할 듯.
        List<Alarm> alarms = em.createQuery(selectCustomerCompanyAlarmsQuery , Alarm.class)
                               .setParameter("customer_company", customerCompany)
                               .getResultList();
        return alarms;
    }
}


// String findCustomerCompanyAlarmsQuery = "SELECT a FROM Alarm a JOIN a.customerCompany acc WHERE acc.companyName= :customer_company_name"; 
// String findCustomerCompanyAlarmsQuery = "SELECT a FROM Alarm a WHERE a.customerCompany.companyName = :customer_company_name";