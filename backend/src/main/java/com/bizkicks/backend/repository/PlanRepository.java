package com.bizkicks.backend.repository;

import com.bizkicks.backend.entity.*;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class PlanRepository {

    @PersistenceContext
    private EntityManager em;

    public List<Plan> findPlanByCustomerCompany(CustomerCompany customerCompany){

        String query = "SELECT p FROM Plan p JOIN FETCH p.kickboardBrand WHERE p.customerCompany = :customerCompany";
        List<Plan> resultList = em.createQuery(query, Plan.class)
                .setParameter("customerCompany", customerCompany)
                .getResultList();

        return resultList;
    }

    public List<Plan> findAllWithBrandCompany(){
        return em.createQuery(
                "select p from Plan p " +
                        "join fetch p.customerCompany c " +
                        "join fetch p.kickboardBrand", Plan.class)
                .getResultList();

    }


    public void saveAllPlan(List<Plan> plans){
        for (Plan plan : plans) {
            em.persist(plan);
        }
    }

    public void updatePlanInCustomerCompany(CustomerCompany customerCompany, KickboardBrand kickboardBrand, Integer totaltime) {

        String qlString = "update Plan p " +
                "set p.totalTime = :totalTime " +
                "where p.customerCompany = :customerCompany and " +
                "p.kickboardBrand = :kickboardBrand and " +
                "p.status = :status";;
        em.createQuery(qlString)
                .setParameter("totalTime", totaltime)
                .setParameter("kickboardBrand", kickboardBrand)
                .setParameter("customerCompany", customerCompany)
                .setParameter("status", "Active")
                .executeUpdate();

    }

    public void deleteInCustomerCompany(CustomerCompany customerCompany, KickboardBrand kickboardBrand) {

        String qlString = "update Plan p "+
                "set p.status = :status " +
                "where p.customerCompany = :customerCompany and " +
                "p.kickboardBrand = :kickboardBrand and " +
                "p.status = :status2";
        em.createQuery(qlString)
                .setParameter("status", "revoke")
                .setParameter("kickboardBrand", kickboardBrand)
                .setParameter("customerCompany", customerCompany)
                .setParameter("status2", "Active")
                .executeUpdate();

    }

    public void addUsedTimeInCustomerCompany(CustomerCompany customerCompany, KickboardBrand kickboardBrand, Integer betweenTime) {

        String updateUsedTime = "update Plan p " +
                "set p.usedTime = p.usedTime + :betweenTime " +
                "where p.customerCompany = :customerCompany and " +
                "p.kickboardBrand = :kickboardBrand and " +
                "p.status = :status";

        em.createQuery(updateUsedTime)
                .setParameter("betweenTime", betweenTime)
                .setParameter("customerCompany", customerCompany)
                .setParameter("kickboardBrand", kickboardBrand)
                .setParameter("status", "Active")
                .executeUpdate();

    }

    public Plan findPlanByBrandAndCompany(CustomerCompany customerCompany, KickboardBrand kickboardBrand) {

        String findQuery = "SELECT p FROM Plan p " +
                "WHERE p.customerCompany = :customerCompany and " +
                "p.kickboardBrand = :kickboardBrand and " +
                "p.status = :status";

        Plan plan = em.createQuery(findQuery, Plan.class)
                .setParameter("customerCompany", customerCompany)
                .setParameter("kickboardBrand", kickboardBrand)
                .setParameter("status", "Active")
                .getSingleResult();

        return plan;

    }
}
