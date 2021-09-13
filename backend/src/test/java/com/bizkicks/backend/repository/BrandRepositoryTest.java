package com.bizkicks.backend.repository;

import com.bizkicks.backend.entity.KickboardBrand;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@NoArgsConstructor
class BrandRepositoryTest {

    @PersistenceContext
    private EntityManager em;
    private Object customerCompany;

    @Test
    void findAll() {
    }

    @Test
    void findAllBrandName(){
        List<KickboardBrand> resultList = em.createQuery("select k.brandName from KickboardBrand k", KickboardBrand.class)
                .getResultList();

        System.out.println("resultList = " + resultList);
    }



    @Test
    void findByBrandName() {
    }
}