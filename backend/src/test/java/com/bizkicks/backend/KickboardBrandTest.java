package com.bizkicks.backend;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import com.bizkicks.backend.entity.KickboardBrand;

import com.bizkicks.backend.repository.KickboardBrandRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.NoArgsConstructor;

@SpringBootTest
@Transactional
@NoArgsConstructor
class KickboardBrandRepositoryTest {
    @PersistenceContext EntityManager em;
    @Autowired KickboardBrandRepository kickboardBrandRepository;

    KickboardBrand kickboardBrand;

    @BeforeEach
    void init(){
        this.kickboardBrand = KickboardBrand.builder()
                                            .brandName("킥고잉")
                                            .build();
        em.persist(this.kickboardBrand);
    }

    @Test
    void find_by_brand_name(){
        // when
        KickboardBrand selectedKickboardBrand = kickboardBrandRepository.findByBrandName("킥고잉");

        // then
        Assertions.assertThat(selectedKickboardBrand.getBrandName()).isEqualTo(this.kickboardBrand.getBrandName());
    }
}
