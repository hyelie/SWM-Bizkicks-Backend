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

    public void deleteAll(){
        em.createQuery("delete * from Alarm");
    }

    public void saveAll(List<Alarm> alarms){
        // TODO 예외처리
        for (Alarm alarm : alarms) {
            em.persist(alarm);
        }
    }

    public List<Alarm> findByCustomerCompanyName(String customerCompanyName){
        System.out.println("repository : " + customerCompanyName);
        String selectCustomerCompanyAlarmsQuery = "select a from Alarm a where a.customerCompany.companyName = :variable";
        List<Alarm> alarms = em.createQuery(selectCustomerCompanyAlarmsQuery , Alarm.class)
                               .setParameter("variable", customerCompanyName)
                               .getResultList();

        return alarms;
    }

}
