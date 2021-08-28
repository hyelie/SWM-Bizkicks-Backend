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
import com.bizkicks.backend.entity.User;
import com.bizkicks.backend.repository.CoordinateRepository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.NoArgsConstructor;

@SpringBootTest
@Transactional
@NoArgsConstructor
class CoordinateRepositoryTest {
    @PersistenceContext EntityManager em;
    @Autowired CoordinateRepository coordinateRepository;

    CustomerCompany customerCompany;
    User user;
    Consumption consumption;
    Coordinate coordinate1;
    Coordinate coordinate2;
    List<Coordinate> coordinates;

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

        this.consumption = Consumption.builder()
                                .departTime(LocalDateTime.of(2021,8,29,03,50,10))
                                .arriveTime(LocalDateTime.of(2021,8,29,04,10,10))
                                .cycle(10)
                                .build();
        consumption.setRelationWithUser(this.user);
        System.out.println(this.consumption.getDepartTime() + " " + this.consumption.getArriveTime());
        em.persist(this.consumption);

        coordinates = new ArrayList<>();

        this.coordinate1 = Coordinate.builder()
                                .longitude(1.11)
                                .latitude(1.11)
                                .sequence((long)1)
                                .consumption(this.consumption)
                                .build();
        coordinate1.setRelationWithConsumption(this.consumption);
        this.coordinates.add(this.coordinate1);

        this.coordinate2 = Coordinate.builder()
                                .longitude(2.22)
                                .latitude(2.22)
                                .sequence((long)2)
                                .consumption(this.consumption)
                                .build();
        coordinate2.setRelationWithConsumption(this.consumption);
        this.coordinates.add(this.coordinate2);
    }

    @Test
    void find_exist_coordinate(){
        // given
        em.persist(this.coordinate1);
        em.persist(this.coordinate2);

        // when
        List<Coordinate> repositoryCoordinates = coordinateRepository.findAllCoordinatesInConsumption(this.consumption);

        // then
        Assertions.assertThat(repositoryCoordinates.containsAll(this.coordinates)).isTrue();
    }

    @Test
    void insert_and_find_coordinate(){
        // given
        coordinateRepository.saveAllCoordinatesInConsumption(this.consumption, this.coordinates);

        // when
        List<Consumption> consumptions = new ArrayList<Consumption>();
        consumptions.add(this.consumption);
        List<Coordinate> repositoryCoordinates = coordinateRepository.findCoordinatesInConsumptions(consumptions);
        
        // then
        Assertions.assertThat(repositoryCoordinates.containsAll(this.coordinates)).isTrue();
    }
}
