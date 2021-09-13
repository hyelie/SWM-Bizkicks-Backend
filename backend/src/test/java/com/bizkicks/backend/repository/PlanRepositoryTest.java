package com.bizkicks.backend.repository;

import com.bizkicks.backend.entity.CustomerCompany;
import com.bizkicks.backend.entity.KickboardBrand;
import com.bizkicks.backend.entity.Plan;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@NoArgsConstructor
class PlanRepositoryTest {

    @PersistenceContext
    private EntityManager em;
    private Object customerCompany;
    
    @Test
    void planfindByCustomerCompany() {

        CustomerCompany customerCompany = em.find(CustomerCompany.class, 1L);

        String query = "SELECT p FROM Plan p JOIN FETCH p.kickboardBrand WHERE p.customerCompany = :customerCompany";
        List<Plan> resultList = em.createQuery(query, Plan.class)
                .setParameter("customerCompany", customerCompany)
                .getResultList();
        for(Plan plan : resultList){
            KickboardBrand k = plan.getKickboardBrand();
        }

        System.out.println("resultList = " + resultList);
        
    }
}