package com.bizkicks.backend;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.test.context.ActiveProfiles;

import com.bizkicks.backend.entity.Consumption;
import com.bizkicks.backend.entity.Coordinate;
import com.bizkicks.backend.entity.CustomerCompany;
import com.bizkicks.backend.entity.KickboardBrand;
import com.bizkicks.backend.entity.User;
import com.bizkicks.backend.filter.DateFilter;
import com.bizkicks.backend.filter.PagingFilter;
import com.bizkicks.backend.repository.ConsumptionRepository;
import com.bizkicks.backend.repository.CoordinateRepository;
import com.bizkicks.backend.service.AlarmService;
import com.bizkicks.backend.service.ConsumptionService;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.NoArgsConstructor;


@SpringBootTest
@Transactional
@NoArgsConstructor
class ConsumptionRepositoryTest {
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
    void insert(){
        // when
        Consumption savedConsumption = consumptionRepository.save(this.consumption);

        // then
        Assertions.assertThat(savedConsumption.getArriveTime()).isEqualTo(consumption.getArriveTime());
        Assertions.assertThat(savedConsumption.getDepartTime()).isEqualTo(consumption.getDepartTime());
        Assertions.assertThat(savedConsumption.getUser().getUserId()).isEqualTo(consumption.getUser().getUserId());
        Assertions.assertThat(savedConsumption.getKickboardBrand().getBrandName()).isEqualTo(consumption.getKickboardBrand().getBrandName());
    }

   

    
}

@SpringBootTest
@Transactional
@NoArgsConstructor
class ConsumptionTest {
    @PersistenceContext EntityManager em;
    @Autowired ConsumptionService consumptionService;

    CustomerCompany customerCompany;
    KickboardBrand kickboardBrand;
    User user;
    Consumption consumption;
    Coordinate coordinate1;
    Coordinate coordinate2;
    List<Coordinate> coordinates;
    DateFilter dateFilter;
    PagingFilter pagingFilter;

    @BeforeEach
    void init(){
        this.customerCompany = CustomerCompany.builder()
                                                .companyCode("asdf")
                                                .companyName("삼성")
                                                .build();
        System.out.println(this.customerCompany.getCompanyName() + this.customerCompany.getCompanyCode());
        em.persist(this.customerCompany);

        this.kickboardBrand = KickboardBrand.builder()
                                            .brandName("킥고잉")
                                            .build();
        em.persist(this.kickboardBrand);

        this.user = User.builder()
                        .userId("userId")
                        .password("password")
                        .type("manager")
                        .build();
        this.user.setRelationWithCustomerCompany(this.customerCompany);
        System.out.println(this.user.getUserId() + this.user.getPassword());
        em.persist(this.user);

        this.consumption = Consumption.builder()
                                .departTime(LocalDateTime.of(2021,8,29,03,50,10))
                                .arriveTime(LocalDateTime.of(2021,8,29,04,10,10))
                                .cycle(10)
                                .build();

        coordinates = new ArrayList<>();

        this.coordinate1 = Coordinate.builder()
                                .longitude(1.11)
                                .latitude(1.11)
                                .sequence((long)1)
                                .consumption(this.consumption)
                                .build();
        this.coordinates.add(this.coordinate1);

        this.coordinate2 = Coordinate.builder()
                                .longitude(2.22)
                                .latitude(2.22)
                                .sequence((long)2)
                                .consumption(this.consumption)
                                .build();
        this.coordinates.add(this.coordinate2);

        dateFilter = DateFilter.builder()
                                .startDate(LocalDateTime.of(2021,8,10,03,50,10))
                                .endDate(LocalDateTime.of(2021,8,30,03,50,10))
                                .build();
        pagingFilter = PagingFilter.builder()
                                    .unit(10)
                                    .page(1)
                                    .build();
    }

    // @Test
    // void find(){
    //     // given
    //     this.consumption.setRelationWithKickboardBrand(this.kickboardBrand);
    //     this.consumption.setRelationWithUser(this.user);
    //     em.persist(consumption);

    //     this.coordinate1.setRelationWithConsumption(this.consumption);
    //     this.coordinate2.setRelationWithConsumption(this.consumption);

    //     // when
    //     // service.find

    //     // then
    //     // assertions
    // }


    @Test
    void save(){
        // when
        consumptionService.saveConsumptionWithCoordinates(this.user.getId(), this.kickboardBrand.getBrandName(), this.consumption, this.coordinates);
        // service.find

        // then
        // save

        String filterQuery = "SELECT c FROM Consumption c WHERE c.user = :user AND :start_date < c.arriveTime AND c.arriveTime < :end_date ORDER BY c.id ASC";
        List<Consumption> consumptions = em.createQuery(filterQuery, Consumption.class)
                                            .setParameter("user", user)
                                            .setParameter("start_date", dateFilter.getStartDate())
                                            .setParameter("end_date", dateFilter.getEndDate())
                                            .setFirstResult(pagingFilter.getPage()-1)
                                            .setMaxResults(pagingFilter.getUnit())
                                            .getResultList();

        String selectCoordinateQuery = "SELECT c FROM Coordinate c WHERE c.consumption IN :consumptions ORDER BY c.consumption.id ASC, c.sequence ASC";
        List<Coordinate> coordinates = em.createQuery(selectCoordinateQuery, Coordinate.class)
                                            .setParameter("consumptions", consumptions)
                                            .getResultList();

        System.out.println(coordinates);
    }

    @Test
    void service(){
        // when
        consumptionService.saveConsumptionWithCoordinates(this.user.getId(), this.kickboardBrand.getBrandName(), this.consumption, this.coordinates);
        
        HashMap<Consumption, List<Coordinate>> result = consumptionService.findConsumptionWithCoordinate(this.user.getId(), this.dateFilter, this.pagingFilter);

        Assertions.assertThat(result.get(this.consumption)).isEqualTo(this.coordinates);
    }
}
