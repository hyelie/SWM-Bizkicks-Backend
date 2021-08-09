package com.bizkicks.backend;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import com.bizkicks.backend.entity.Alarm;
import com.bizkicks.backend.entity.CustomerCompany;
import com.bizkicks.backend.service.AlarmService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.NoArgsConstructor;


@SpringBootTest
@NoArgsConstructor
class BackendApplicationTests {

	@PersistenceContext
    private EntityManager em;

	@Autowired AlarmService alarmService;

	@Test
	void contextLoads(){}

	@Test
	@Transactional
	void create_test(){
		CustomerCompany customerCompany = new CustomerCompany("삼성", "asdf");
		em.persist(customerCompany);

		Alarm alarm = new Alarm("cost", 100);
		alarm.setRelationWithCustomerCompany(customerCompany);
		em.persist(alarm);
	}

	
}




