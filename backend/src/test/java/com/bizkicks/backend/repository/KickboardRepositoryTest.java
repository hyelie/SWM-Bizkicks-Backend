package com.bizkicks.backend.repository;

import com.bizkicks.backend.entity.CustomerCompany;
import com.bizkicks.backend.entity.Kickboard;
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
class KickboardRepositoryTest {

    @PersistenceContext
    private EntityManager em;


    void findByBrand(KickboardBrand kickboardBrand){

        String query = "select k from Kickboard k where k.kickboardBrand = :kickboardBrand";
        List<Kickboard> kickboards = em.createQuery(query, Kickboard.class)
                .setParameter("kickboardBrand", kickboardBrand)
                .getResultList();


    }

}