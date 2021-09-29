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

    // planfind보다 findPlanBy~가 좀 더 직관적일 듯.
    public List<Plan> planfindByCustomerCompany(CustomerCompany customerCompany){
// 가능하면 query가 어떤 동작을 할 건지 변수명으로 나타내면 좋을 듯.
        String query = "SELECT p FROM Plan p JOIN FETCH p.kickboardBrand WHERE p.customerCompany = :customerCompany";
        List<Plan> resultList = em.createQuery(query, Plan.class)
                .setParameter("customerCompany", customerCompany)
                .getResultList();

        return resultList;
    }

    // findAllWith ~ (오타)

    public List<Plan> findAllwithBrandCompany(){
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

    // 이것도 '어떤 조건을 가진 plan'을 update하는지 모르니까 updatePlanInCustomerCompany나 updatePlanBy로 바꾸면 좋을 듯.
    public void updatePlan(CustomerCompany customerCompany, KickboardBrand kickboardBrand, Integer totaltime) {
// 가능하면 query가 어떤 동작을 할 건지 변수명으로 나타내면 좋을 듯.
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

    // 마찬가지, deleteByCustomerCompany나 deleteInCustomerCompany로 바꾸면 좋을 듯.
    public void delete(CustomerCompany customerCompany, KickboardBrand kickboardBrand) {
// 가능하면 query가 어떤 동작을 할 건지 변수명으로 나타내면 좋을 듯.
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

    // 마찬가지, addUsedTimeInCustomerCompany로 바꾸면 좋을 듯.
    public void addUsedTime(CustomerCompany customerCompany, KickboardBrand kickboardBrand, Integer betweenTime) {

        // query가 2개니까 각각 query가 어떤 동작을 꾀하는지를 변수명으로 나타내주면 좋을 듯.
        String qlString1 = "update Plan p " +
                "set p.usedTime = p.usedTime + :betweenTime " +
                "where p.customerCompany = :customerCompany and " +
                "p.kickboardBrand = :kickboardBrand and " +
                "p.status = :status";

        em.createQuery(qlString1)
                .setParameter("betweenTime", betweenTime)
                .setParameter("customerCompany", customerCompany)
                .setParameter("kickboardBrand", kickboardBrand)
                .setParameter("status", "Active")
                .executeUpdate();

        String qlString2 = "update Plan p " +
                "set p.totalTime = p.totalTime - :betweenTime " +
                "where p.customerCompany = :customerCompany and " +
                "p.kickboardBrand = :kickboardBrand and " +
                "p.status = :status";

        em.createQuery(qlString2)
                .setParameter("betweenTime", betweenTime)
                .setParameter("customerCompany", customerCompany)
                .setParameter("kickboardBrand", kickboardBrand)
                .setParameter("status", "Active")
                .executeUpdate();
    }
}
