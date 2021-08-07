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

        CustomerCompany temp = new CustomerCompany(customerCompanyName, "asdfas");
        // 아래, createQuery문에서 Alarm 는 Entity -> a.customerCompany의 자료형은 CustomerCompany class.
        // 그런데 기존에는 a.customerCompany=customerCompanyName으로 작성했음.
        // 여기서, customerCompanyName은 String.
        // 그래서 a.customerCompany의 자료형과 customerCompanyName의 자료형이 맞지 않아서 오류가 출력되었던 것임. customercompany 하나를 만들고 나서 넣으니 아주 잘 됨...

        List<Alarm> alarms = em.createQuery("select a from Alarm a where a.customerCompany =:customer_company_name" , Alarm.class)
                                .setParameter("customer_company_name", temp)
                                .getResultList();
        return alarms;
    }

}
