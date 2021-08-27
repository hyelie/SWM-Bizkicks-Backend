package com.bizkicks.backend.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.bizkicks.backend.entity.KickboardBrand;

import org.springframework.stereotype.Repository;

@Repository
public class KickboardBrandRepository {
    @PersistenceContext
    private EntityManager em;

    public KickboardBrand findByBrandName(String brandName){
        String selectBrandNameQuery = "SELECT b FROM KickboardBrand b WHERE b.brandName = :brand_name";
        List<KickboardBrand> kickboardBrands = em.createQuery(selectBrandNameQuery, KickboardBrand.class)
                                                    .setParameter("brand_name", brandName)
                                                    .getResultList();
        if(kickboardBrands.isEmpty()) return null;
        else return kickboardBrands.get(0);
    }
}
