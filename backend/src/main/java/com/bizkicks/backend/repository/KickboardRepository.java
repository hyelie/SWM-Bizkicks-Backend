package com.bizkicks.backend.repository;

import com.bizkicks.backend.entity.Alarm;
import com.bizkicks.backend.entity.CustomerCompany;
import com.bizkicks.backend.entity.Kickboard;
import com.bizkicks.backend.entity.KickboardBrand;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class KickboardRepository {

    @PersistenceContext
    private EntityManager em;

    public List<Kickboard> findByBrand(KickboardBrand kickboardBrand){
// 가능하면 query가 어떤 동작을 할 건지 변수명으로 나타내면 좋을 듯.
        String query = "select k from Kickboard k where k.kickboardBrand = :kickboardBrand";
        List<Kickboard> kickboards = em.createQuery(query, Kickboard.class)
                .setParameter("kickboardBrand", kickboardBrand)
                .getResultList();

        return kickboards;

    }

    public List<Kickboard> findKickboardByCustomerCompany(CustomerCompany customerCompany) {

// 가능하면 query가 어떤 동작을 할 건지 변수명으로 나타내면 좋을 듯.
        String query = "select kb from KickboardBrand kb " +
                "join Plan p " +
                "on p.kickboardBrand = kb where " +
                "p.customerCompany = :customerCompany";
        List<KickboardBrand> resultList = em.createQuery(query, KickboardBrand.class)
                .setParameter("customerCompany", customerCompany)
                .getResultList();

        String query2 = "SELECT k from Kickboard k WHERE k.kickboardBrand IN :brands";
        List<Kickboard> resultList2 = em.createQuery(query2, Kickboard.class)
                .setParameter("brands", resultList)
                .getResultList();

        return resultList2;
    }

    public List<Kickboard> findAllKickboards() {

        // 가능하면 query가 어떤 동작을 할 건지 변수명으로 나타내면 좋을 듯.
        String query = "select k from Kickboard k";
        List<Kickboard> resultList = em.createQuery(query, Kickboard.class)
                .getResultList();

        return resultList;

    }
}
