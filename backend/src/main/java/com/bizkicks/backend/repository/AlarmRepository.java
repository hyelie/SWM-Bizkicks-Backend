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
        String deleteCustomerCompanyAlarmsQuery = "DELETE from Alarm a where a.customerCompany = :customer_company";
        em.createQuery(deleteCustomerCompanyAlarmsQuery)
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
        String selectCustomerCompanyAlarmsQuery = "SELECT a FROM Alarm a JOIN FETCH a.customerCompany WHERE a.customerCompany = :customer_company";
        List<Alarm> alarms = em.createQuery(selectCustomerCompanyAlarmsQuery , Alarm.class)
                               .setParameter("customer_company", customerCompany)
                               .getResultList();
        return alarms;
    }
}