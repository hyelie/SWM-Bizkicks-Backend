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

    public boolean existsById(Long kickboardId) {
        String findQuery = "SELECT k FROM Kickboard k WHERE k.id = :kickboard_id";
        List<Kickboard> kickboards = em.createQuery(findQuery, Kickboard.class)
                .setParameter("kickboard_id", kickboardId)
                .getResultList();
        if (kickboards.isEmpty()) return false;
        else return true;
    }

    public Kickboard findById(Long kickboardId) {
        String findQuery = "SELECT k FROM Kickboard k WHERE k.id = :kickboard_id";
        Kickboard kickboard = em.createQuery(findQuery, Kickboard.class)
                .setParameter("kickboard_id", kickboardId)
                .getSingleResult();
        return kickboard;
    }

    public List<Kickboard> findByBrand(KickboardBrand kickboardBrand) {

        String findByBrandQuery = "select k from Kickboard k where k.kickboardBrand = :kickboardBrand";
        List<Kickboard> kickboards = em.createQuery(findByBrandQuery, Kickboard.class)
                .setParameter("kickboardBrand", kickboardBrand)
                .getResultList();

        return kickboards;

    }

    public List<Kickboard> findKickboardByCustomerCompany(CustomerCompany customerCompany) {

        String joinQuery = "select kb from KickboardBrand kb " +
                "join Plan p " +
                "on p.kickboardBrand = kb where " +
                "p.customerCompany = :customerCompany";
        List<KickboardBrand> resultList = em.createQuery(joinQuery, KickboardBrand.class)
                .setParameter("customerCompany", customerCompany)
                .getResultList();

        String findByKickboardBrandQuery = "SELECT k from Kickboard k WHERE k.kickboardBrand IN :brands";
        List<Kickboard> resultList2 = em.createQuery(findByKickboardBrandQuery, Kickboard.class)
                .setParameter("brands", resultList)
                .getResultList();

        return resultList2;
    }

    public List<Kickboard> findAllKickboards() {

        String findAllQuery = "select k from Kickboard k";
        List<Kickboard> resultList = em.createQuery(findAllQuery, Kickboard.class)
                .getResultList();

        return resultList;

    }

    public void updatePastPicture(Long kickboardId, String path) {
        String updateQuery = "UPDATE Kickboard k SET k.pastPicture = :path WHERE k.id = :kickboard_id";
        em.createQuery(updateQuery)
                .setParameter("kickboard_id", kickboardId)
                .setParameter("path", path)
                .executeUpdate();
    }

    public void saveAll(List<Kickboard> kickboards) {
        for (Kickboard kickboard : kickboards) {
            em.persist(kickboard);
        }
    }


}