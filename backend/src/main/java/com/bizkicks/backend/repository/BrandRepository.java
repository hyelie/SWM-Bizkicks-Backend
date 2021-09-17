package com.bizkicks.backend.repository;

import com.bizkicks.backend.entity.Alarm;
import com.bizkicks.backend.entity.CustomerCompany;
import com.bizkicks.backend.entity.KickboardBrand;
import com.bizkicks.backend.entity.Plan;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class BrandRepository {

    private final EntityManager em;


    public List<KickboardBrand> findAll() {

        return em.createQuery("select k from KickboardBrand k", KickboardBrand.class)
                .getResultList();

    }

    public List<KickboardBrand> findAllBrandName(){
        return em.createQuery("select k.brandName from KickboardBrand k", KickboardBrand.class)
                .getResultList();
    }

    public KickboardBrand findByBrandName(String brandName) {
        String verifyCustomerCompanyQuery = "select kb from KickboardBrand kb where kb.brandName = :brandName";
        List<KickboardBrand> kickboardBrands = em.createQuery(verifyCustomerCompanyQuery, KickboardBrand.class)
                .setParameter("brandName", brandName)
                .getResultList();
        if (kickboardBrands.isEmpty()) return null;
        else return kickboardBrands.get(0);
    }
}
