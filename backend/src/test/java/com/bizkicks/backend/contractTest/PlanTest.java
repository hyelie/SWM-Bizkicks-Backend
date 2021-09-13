package com.bizkicks.backend.contractTest;

import com.bizkicks.backend.entity.CustomerCompany;
import com.bizkicks.backend.entity.KickboardBrand;
import com.bizkicks.backend.entity.Plan;
import com.bizkicks.backend.entity.User;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@SpringBootTest
@Transactional
@NoArgsConstructor
public class PlanTest {

    @PersistenceContext
    private EntityManager em;
    private Object customerCompany;

    @Test
    public void PlanGetTest(){

        User user = em.find(User.class, 1L);
        CustomerCompany customerCompany = em.find(CustomerCompany.class, 1L);

        //String query = "select b from Plan p join fetch KickboardBrand b where p.customerCompany = :customerCompany";
        //String query = "select p from Plan p where p.customerCompany.id = :customerCompany";
        String query = "SELECT p FROM Plan p JOIN FETCH p.kickboardBrand WHERE p.customerCompany = :customerCompany";
        List<Plan> resultList = em.createQuery(query, Plan.class)
                .setParameter("customerCompany", user.getCustomerCompany())
                .getResultList();
        for(Plan plan : resultList){
            KickboardBrand k = plan.getKickboardBrand();
        }

        System.out.println(resultList);
    }

}
