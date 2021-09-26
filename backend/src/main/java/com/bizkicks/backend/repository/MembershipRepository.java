package com.bizkicks.backend.repository;

import com.bizkicks.backend.entity.CustomerCompany;
import com.bizkicks.backend.entity.KickboardBrand;
import com.bizkicks.backend.entity.Membership;
import com.bizkicks.backend.entity.Plan;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.List;

@Repository
public class MembershipRepository {

    @PersistenceContext
    private EntityManager em;

    public void saveAll(List<Membership> memberships) {

        for (Membership membership : memberships) {
            em.persist(membership);
        }
    }

    public void update(CustomerCompany customerCompany, LocalDate startdate, LocalDate duedate){

        String qlString = "update Membership m " +
                "set m.startDate = :startDate " +
                "where m.customerCompany =:customerCompany and "+
                "m.status = :status";
        em.createQuery(qlString)
                .setParameter("startDate", startdate)
                .setParameter("customerCompany", customerCompany)
                .setParameter("status", "Active")
                .executeUpdate();

        String qlString2 = "update Membership m " +
                "set m.duedate = :duedate " +
                "where m.customerCompany =:customerCompany and " +
                "m.status = :status";
        em.createQuery(qlString2)
                .setParameter("duedate", duedate)
                .setParameter("customerCompany", customerCompany)
                .setParameter("status", "Active")
                .executeUpdate();

    }

    public void delete(CustomerCompany customerCompany) {

        String qlString = "update Membership m " +
                "set m.status = :status " +
                "where m.customerCompany =:customerCompany and "+
                "m.status = :status2";
        em.createQuery(qlString)
                .setParameter("status", "revoke")
                .setParameter("customerCompany", customerCompany)
                .setParameter("status2", "Active")
                .executeUpdate();

    }

    public List<Plan> planfindByCustomerCompany(CustomerCompany customerCompany){

        String query = "SELECT p FROM Plan p JOIN FETCH p.kickboardBrand WHERE p.customerCompany = :customerCompany";
        List<Plan> resultList = em.createQuery(query, Plan.class)
                .setParameter("customerCompany", customerCompany)
                .getResultList();
        for(Plan plan : resultList){
            KickboardBrand k = plan.getKickboardBrand();
        }

        return resultList;
    }


    public List<Membership> membershipFindByCustomerCompany(CustomerCompany customerCompany) {

        String query = "select m from Membership m join fetch m.kickboardBrand where m.customerCompany = :customerCompany";
        List<Membership> resultList = em.createQuery(query, Membership.class)
                .setParameter("customerCompany", customerCompany)
                .getResultList();
        return resultList;
    }

    public void addUsedTime(CustomerCompany customerCompany, KickboardBrand kickboardBrand, int betweenTime) {

        String qlString1 = "update Membership m " +
                "set m.usedTime = m.usedTime + :betweenTime " +
                "where m.customerCompany = :customerCompany and " +
                "m.kickboardBrand = :kickboardBrand and " +
                "m.status = :status";

        em.createQuery(qlString1)
                .setParameter("betweenTime", betweenTime)
                .setParameter("customerCompany", customerCompany)
                .setParameter("kickboardBrand", kickboardBrand)
                .setParameter("status", "Active")
                .executeUpdate();
    }
}
