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

    // 작명을 조금 바꾸면 좋을 듯. '갱신'인 update가 아니라 계약 갱신인 renewal 이런 것으로?
    // 그리고 customerCompany로 검색하니까 updateByCustomerCompany 이런 식으로 바꾸면 좋을 듯?
    // update만 보고서는 '무엇을', '어떤 조건을' 을 직관적으로 알기 힘듬.
    public void update(CustomerCompany customerCompany, LocalDate startdate, LocalDate duedate){

        // 이런 변수도 query string이 아니라 어떤 역할을 하는 query인지 적어주면 더 좋을 듯.
        String qlString = "update Membership m " +
                "set m.startDate = :startDate " +
                "where m.customerCompany =:customerCompany and "+
                "m.status = :status";
        em.createQuery(qlString)
                .setParameter("startDate", startdate)
                .setParameter("customerCompany", customerCompany)
                .setParameter("status", "Active")
                .executeUpdate();

        // 이런 변수도 query string이 아니라 어떤 역할을 하는 query인지 적어주면 더 좋을 듯.
        // 여기는 다른 것보다 특수한 게, query를 2개 쓰기 때문에 각각의 역할을 기재해야 함수 동작이 되는지 알아볼 수 있을 듯.
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

    // delete만 보고서는 '어떤 조건을 가지는' row를 바꾸는지 잘 모르겠음. deleteByCustomerCompany 이런 식은 어떤가?
    public void delete(CustomerCompany customerCompany) {
// 가능하면 query가 어떤 동작을 할 건지 변수명으로 나타내면 좋을 듯.
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

    // 작명을 바꾸면 좋을 듯? findPlanByCustomerCompany가 좀 더 직관적인 듯.
    // 그리고 이건 plan을 찾는 건데 왜 membership에 있는지 잘 모르겠음.
    public List<Plan> planfindByCustomerCompany(CustomerCompany customerCompany){
// 가능하면 query가 어떤 동작을 할 건지 변수명으로 나타내면 좋을 듯.
        String query = "SELECT p FROM Plan p JOIN FETCH p.kickboardBrand WHERE p.customerCompany = :customerCompany";
        List<Plan> resultList = em.createQuery(query, Plan.class)
                .setParameter("customerCompany", customerCompany)
                .getResultList();
        for(Plan plan : resultList){
            KickboardBrand k = plan.getKickboardBrand();
        }

        return resultList;
    }

    // 작명을 바꾸면 좋을 듯? findMembershipByCustomerCompany가 좀 더 직관적인 듯.
    public List<Membership> membershipFindByCustomerCompany(CustomerCompany customerCompany) {

        String query = "select m from Membership m join fetch m.kickboardBrand where m.customerCompany = :customerCompany";
        List<Membership> resultList = em.createQuery(query, Membership.class)
                .setParameter("customerCompany", customerCompany)
                .getResultList();
        return resultList;
    }

    // 마찬가지로 addUserTimeInCustomerCompany 이런 식으로 바꾸는 게 좀 더 직관적일 것 같음.
    public void addUsedTime(CustomerCompany customerCompany, KickboardBrand kickboardBrand, int betweenTime) {
// 가능하면 query가 어떤 동작을 할 건지 변수명으로 나타내면 좋을 듯.
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
