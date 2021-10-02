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

    public void updateByCustomerCompany(CustomerCompany customerCompany, LocalDate startdate, LocalDate duedate){

        String updateStartDate = "update Membership m " +
                "set m.startDate = :startDate " +
                "where m.customerCompany =:customerCompany and "+
                "m.status = :status";
        em.createQuery(updateStartDate)
                .setParameter("startDate", startdate)
                .setParameter("customerCompany", customerCompany)
                .setParameter("status", "Active")
                .executeUpdate();

        String updateDuedate = "update Membership m " +
                "set m.duedate = :duedate " +
                "where m.customerCompany =:customerCompany and " +
                "m.status = :status";
        em.createQuery(updateDuedate)
                .setParameter("duedate", duedate)
                .setParameter("customerCompany", customerCompany)
                .setParameter("status", "Active")
                .executeUpdate();

    }

    public void deleteByCustomerCompany(CustomerCompany customerCompany) {

        String activeToRevoke = "update Membership m " +
                "set m.status = :status " +
                "where m.customerCompany =:customerCompany and "+
                "m.status = :status2";
        em.createQuery(activeToRevoke)
                .setParameter("status", "revoke")
                .setParameter("customerCompany", customerCompany)
                .setParameter("status2", "Active")
                .executeUpdate();

    }

    public List<Membership> findMembershipByCustomerCompany(CustomerCompany customerCompany) {

        String query = "select m from Membership m join fetch m.kickboardBrand where m.customerCompany = :customerCompany";
        List<Membership> resultList = em.createQuery(query, Membership.class)
                .setParameter("customerCompany", customerCompany)
                .getResultList();
        return resultList;
    }

    // 마찬가지로 addUserTimeInCustomerCompany 이런 식으로 바꾸는 게 좀 더 직관적일 것 같음.
    public void addUserTimeInCustomerCompany(CustomerCompany customerCompany, KickboardBrand kickboardBrand, int betweenTime) {

        String updateUsedTime = "update Membership m " +
                "set m.usedTime = m.usedTime + :betweenTime " +
                "where m.customerCompany = :customerCompany and " +
                "m.kickboardBrand = :kickboardBrand and " +
                "m.status = :status";

        em.createQuery(updateUsedTime)
                .setParameter("betweenTime", betweenTime)
                .setParameter("customerCompany", customerCompany)
                .setParameter("kickboardBrand", kickboardBrand)
                .setParameter("status", "Active")
                .executeUpdate();
    }
}
