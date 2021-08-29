package com.bizkicks.backend.repository;

import com.bizkicks.backend.entity.KickboardBrand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class BrandRepository{

    private final EntityManager em;


    public List<KickboardBrand> findAll(){
        return em.createQuery("select k from KickboardBrand k", KickboardBrand.class)
                .getResultList();
    }

}
