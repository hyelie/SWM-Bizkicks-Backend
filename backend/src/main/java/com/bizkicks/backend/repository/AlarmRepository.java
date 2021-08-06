package com.bizkicks.backend.repository;

import com.bizkicks.backend.entity.Alarm;
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
        return (List<Alarm>)em.createQuery("select a from Alarm a where a.customerCompany = abc" , Alarm.class)
                //.setParameter("customer_company_name", customerCompanyName)
                .getResultList();
    }

}
