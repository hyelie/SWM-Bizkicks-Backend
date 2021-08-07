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

    public void deleteAllAlarmsInCustomerCompany(String customerCompanyName){
        em.createQuery("delete * from Alarm a where a.customerCompany.companyName = :customer_company_name")
          .setParameter("customer_company_name", customerCompanyName);
    }

    public void saveAll(List<Alarm> alarms){
        // TODO 예외처리
        for (Alarm alarm : alarms) {
            em.persist(alarm);
        }
    }

    public List<Alarm> findByCustomerCompanyName(String customerCompanyName){
        System.out.println("repository : " + customerCompanyName);
        String selectCustomerCompanyAlarmsQuery = "select a from Alarm a where a.customerCompany.companyName = :customer_company_name";
        List<Alarm> alarms = em.createQuery(selectCustomerCompanyAlarmsQuery , Alarm.class)
                               .setParameter("customer_company_name", customerCompanyName)
                               .getResultList();
        return alarms;
    }

}
