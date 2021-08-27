package com.bizkicks.backend;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import com.bizkicks.backend.entity.Consumption;
import com.bizkicks.backend.entity.Coordinate;
import com.bizkicks.backend.entity.CustomerCompany;
import com.bizkicks.backend.entity.KickboardBrand;
import com.bizkicks.backend.entity.User;
import com.bizkicks.backend.repository.ConsumptionRepository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.NoArgsConstructor;

@SpringBootTest
@Transactional
@NoArgsConstructor
class ConsumptionTest {
    @PersistenceContext EntityManager em;
    @Autowired ConsumptionRepository consumptionRepository;

    CustomerCompany customerCompany;
    User user;
    Consumption consumption;
    Coordinate coordinate1;
    Coordinate coordinate2;
    List<Coordinate> coordinates;
    KickboardBrand kickboardBrand;

    @BeforeEach
    void init(){
        this.customerCompany = CustomerCompany.builder()
                                                .companyCode("asdf")
                                                .companyName("삼성")
                                                .build();
        System.out.println(this.customerCompany.getCompanyName() + this.customerCompany.getCompanyCode());
        em.persist(this.customerCompany);

        this.user = User.builder()
                        .userId("userId")
                        .password("password")
                        .type("manager")
                        .build();
        this.user.setRelationWithCustomerCompany(this.customerCompany);
        System.out.println(this.user.getUserId() + this.user.getPassword());
        em.persist(this.user);

        this.kickboardBrand = KickboardBrand.builder()
                                            .brandName("킥고잉")
                                            .build();
        em.persist(this.kickboardBrand);

        this.consumption = Consumption.builder()
                                .departTime(LocalDateTime.of(2021,8,29,03,50,10))
                                .arriveTime(LocalDateTime.of(2021,8,29,04,10,10))
                                .cycle(10)
                                .build();
        consumption.setRelationWithUser(this.user);
        consumption.setRelationWithKickboardBrand(this.kickboardBrand);
        System.out.println(this.consumption.getDepartTime() + " " + this.consumption.getArriveTime() + " " + this.consumption.getUser().getUserId() + " " + this.consumption.getKickboardBrand().getBrandName());
    }

    @Test
    void insert_and_get_id(){
        // when
        Consumption savedConsumption = consumptionRepository.save(this.consumption);

        // then
        Assertions.assertThat(savedConsumption.getArriveTime()).isEqualTo(consumption.getArriveTime());
        Assertions.assertThat(savedConsumption.getDepartTime()).isEqualTo(consumption.getDepartTime());
        Assertions.assertThat(savedConsumption.getUser().getUserId()).isEqualTo(consumption.getUser().getUserId());
        Assertions.assertThat(savedConsumption.getKickboardBrand().getBrandName()).isEqualTo(consumption.getKickboardBrand().getBrandName());
        System.out.println(savedConsumption.getId());
    }
}
