package com.bizkicks.backend;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import org.springframework.test.context.ActiveProfiles;
import javax.transaction.Transactional;
import lombok.NoArgsConstructor;

import com.bizkicks.backend.entity.Alarm;
import com.bizkicks.backend.entity.CustomerCompany;
import com.bizkicks.backend.repository.AlarmRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Transactional
@ExtendWith(SpringExtension.class)
@NoArgsConstructor
class BackendApplicationTests {

	@Autowired AlarmRepository alarmRepository;

	@Test
	void contextLoads(){}

	@Test
	void createAlarmTest() {

		// given - 삼성
		CustomerCompany samsung = new CustomerCompany("asdfg", "삼성");
		Alarm samsungAlarm1 = new Alarm("cost", 10000);
		samsungAlarm1.setRelationWithCustomerCompany(samsung);
		Alarm samsungAlarm2 = new Alarm("time", 100);
		samsungAlarm2.setRelationWithCustomerCompany(samsung);

		List<Alarm> samsungAlarms = new ArrayList<>();
		samsungAlarms.add(samsungAlarm1);
		samsungAlarms.add(samsungAlarm2);

		// given - LG
		CustomerCompany lg = new CustomerCompany("qwerty", "LG");
		Alarm lgAlarm1 = new Alarm("cost", 15000);
		lgAlarm1.setRelationWithCustomerCompany(lg);
		Alarm lgAlarm2 = new Alarm("time", 80);
		lgAlarm2.setRelationWithCustomerCompany(lg);

		List<Alarm> lgAlarms = new ArrayList<>();
		lgAlarms.add(lgAlarm1);
		lgAlarms.add(lgAlarm2);

		alarmRepository.saveAll(samsungAlarms);
		alarmRepository.saveAll(lgAlarms);

		Assertions.assertThat(1).isEqualTo(1);
		List<Alarm> resultSamsungAlarms = alarmRepository.findByCustomerCompanyName("삼성");
		Assertions.assertThat(resultSamsungAlarms).isEqualTo(samsungAlarms);
		Assertions.assertThat(alarmRepository.findByCustomerCompanyName("LG")).isEqualTo(lgAlarms);

	}

	@Test
	void findAlarmTest(){

	}
}
