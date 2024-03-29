package com.bizkicks.backend;

import org.assertj.core.api.Assertions;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import org.springframework.test.context.ActiveProfiles;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.Cookie;
import javax.transaction.Transactional;
import lombok.NoArgsConstructor;

import com.bizkicks.backend.api.AlarmApi;
import com.bizkicks.backend.entity.Alarm;
import com.bizkicks.backend.entity.CustomerCompany;
import com.bizkicks.backend.exception.CustomException;
import com.bizkicks.backend.repository.AlarmRepository;
import com.bizkicks.backend.repository.CustomerCompanyRepository;
import com.bizkicks.backend.service.AlarmService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;


@SpringBootTest
@Transactional
@NoArgsConstructor
class AlarmRepositoryTest{
	@PersistenceContext EntityManager em;
	@Autowired AlarmRepository alarmRepository;

	List<Alarm> emptyAlarms;
	CustomerCompany samsung;
	Alarm samsungAlarm1;
	Alarm samsungAlarm2;
	List<Alarm> samsungAlarms;
	CustomerCompany lg;
	Alarm lgAlarm1;
	Alarm lgAlarm2;
	List<Alarm> lgAlarms;
	CustomerCompany space;
	

	@BeforeEach
	void initializeEmpty(){
		emptyAlarms = new ArrayList<>();
	}

	// given - 삼성
	@BeforeEach
	void initializeSamsung(){
		samsung = CreateCustomerCompany("asdfg", "삼성");
		samsungAlarm1 = CreateAlarmInCompany(samsung, "cost", 10000);
		samsungAlarm2 = CreateAlarmInCompany(samsung, "time", 100);

		samsungAlarms = new ArrayList<>();
		samsungAlarms.add(samsungAlarm1);
		samsungAlarms.add(samsungAlarm2);
	}

	// given - LG
	@BeforeEach
	void initializeLg(){
		lg = CreateCustomerCompany("qwerty", "LG");
		lgAlarm1 = CreateAlarmInCompany(lg, "cost", 15000);
		lgAlarm2 = CreateAlarmInCompany(lg, "time", 80);

		lgAlarms = new ArrayList<>();
		lgAlarms.add(lgAlarm1);
		lgAlarms.add(lgAlarm2);
	}

	// given - No Alarm Company
	void initializeNoAlarmCompany(){
		space = CreateCustomerCompany("zxcv", "space");
	}

	public CustomerCompany CreateCustomerCompany(String companyCode, String companyName){
		CustomerCompany customerCompany = new CustomerCompany(companyName, companyCode);
		em.persist(customerCompany);
		return customerCompany;
	}
	
	public Alarm CreateAlarmInCompany(CustomerCompany customerCompany, String type, Integer value){
		Alarm alarm = new Alarm(type, value);
		alarm.setRelationWithCustomerCompany(customerCompany);
		return alarm;
	}

	public void DeepCompareTwoLists(List<Alarm> a, List<Alarm> b){
		for (Integer i = 0; i<a.size(); i++) {
            Assertions.assertThat(a.get(i)).isEqualTo(b.get(i));
        }
	}

	@Test
	void find_exist_alarms(){
		// given
		for (Alarm alarm : samsungAlarms) {
            em.persist(alarm);
        }
		for (Alarm alarm : lgAlarms) {
            em.persist(alarm);
        }

		// when
		List<Alarm> repositorySamsungAlarms = alarmRepository.findByCustomerCompany(samsung);
		List<Alarm> repositoryLgAlarms = alarmRepository.findByCustomerCompany(lg);

		// then
		DeepCompareTwoLists(repositorySamsungAlarms, samsungAlarms);
		DeepCompareTwoLists(repositoryLgAlarms, lgAlarms);
		
	}

	@Test
	void find_not_exist_alarms(){
		// when
		List<Alarm> emptyAlarms = alarmRepository.findByCustomerCompany(space);

		// then
		Assertions.assertThat(emptyAlarms.isEmpty()).isEqualTo(true);
	}

	@Test
	void save_and_find_exist_alarm() {
		// when
		alarmRepository.saveAllAlarmsInCustomerCompany(samsung, samsungAlarms);
		alarmRepository.saveAllAlarmsInCustomerCompany(lg, lgAlarms);

		// then
		List<Alarm> repositorySamsungAlarms = alarmRepository.findByCustomerCompany(samsung);
		DeepCompareTwoLists(repositorySamsungAlarms, samsungAlarms);
		List<Alarm> repositoryLgAlarms = alarmRepository.findByCustomerCompany(lg);
		DeepCompareTwoLists(repositoryLgAlarms, lgAlarms);
	}

	@Test
	void save_and_find_not_exist_alarm() {
		// when
		alarmRepository.saveAllAlarmsInCustomerCompany(space, emptyAlarms);

		// then
		Assertions.assertThat(emptyAlarms.isEmpty()).isEqualTo(true);
	}

	@Test
	void save_delete_and_find() {
		// when
		alarmRepository.saveAllAlarmsInCustomerCompany(samsung, samsungAlarms);
		alarmRepository.saveAllAlarmsInCustomerCompany(lg, lgAlarms);
		alarmRepository.deleteAllAlarmsInCustomerCompany(samsung);
		alarmRepository.deleteAllAlarmsInCustomerCompany(lg);
		List<Alarm> repositorySamsungAlarms = alarmRepository.findByCustomerCompany(samsung);
		List<Alarm> repositoryLgAlarms = alarmRepository.findByCustomerCompany(lg);

		// then
		DeepCompareTwoLists(repositorySamsungAlarms, samsungAlarms);
		DeepCompareTwoLists(repositoryLgAlarms, lgAlarms);
	}	
}

@SpringBootTest
@Transactional
@NoArgsConstructor
class AlarmServiceTest{
    @PersistenceContext
    private EntityManager em;
	@Autowired AlarmService alarmService;

	CustomerCompany samsung;
	Alarm samsungAlarm1;
	Alarm samsungAlarm2;
	List<Alarm> samsungAlarms;
	CustomerCompany lg;
	Alarm lgAlarm1;
	Alarm lgAlarm2;
	List<Alarm> lgAlarms;
	CustomerCompany space;
	List<Alarm> emptyAlarms;

	// given - 삼성
	@BeforeEach
	void initializeSamsung(){
		samsung = CreateCustomerCompany("asdfg", "삼성");
		samsungAlarm1 = CreateAlarmInCompany(samsung, "cost", 10000);
		samsungAlarm2 = CreateAlarmInCompany(samsung, "time", 100);

		samsungAlarms = new ArrayList<>();
		samsungAlarms.add(samsungAlarm1);
		samsungAlarms.add(samsungAlarm2);
	}

	// given - LG
	@BeforeEach
	void initializeLg(){
		lg = CreateCustomerCompany("qwerty", "LG");
		lgAlarm1 = CreateAlarmInCompany(lg, "cost", 15000);
		lgAlarm2 = CreateAlarmInCompany(lg, "time", 80);

		lgAlarms = new ArrayList<>();
		lgAlarms.add(lgAlarm1);
		lgAlarms.add(lgAlarm2);
	}

	// given - No Alarm Company
	@BeforeEach
	void initializeNoAlarmCompany(){
		space = CreateCustomerCompany("zxcv", "space");
		emptyAlarms = new ArrayList<>();
	}

	public void DeepCompareTwoLists(List<Alarm> a, List<Alarm> b){
		for (Integer i = 0; i<a.size(); i++) {
            Assertions.assertThat(a.get(i)).isEqualTo(b.get(i));
        }
	}

	public CustomerCompany CreateCustomerCompany(String companyCode, String companyName){
		CustomerCompany customerCompany = new CustomerCompany(companyName, companyCode);
        em.persist(customerCompany);
		return customerCompany;
	}
	
	public Alarm CreateAlarmInCompany(CustomerCompany customerCompany, String type, Integer value){
		Alarm alarm = new Alarm(type, value);
		alarm.setRelationWithCustomerCompany(customerCompany);
		return alarm;
	}

	@Test
	void find_exist_alarms() {
		// given
		for (Alarm alarm : samsungAlarms) {
            em.persist(alarm);
        }
		for (Alarm alarm : lgAlarms) {
            em.persist(alarm);
        }

        // when
        List<Alarm> serviceSamsungAlarms = alarmService.findAlarms(samsung);
        List<Alarm> serviceLgAlarms = alarmService.findAlarms(lg);
		List<Alarm> serviceSpaceAlarms = alarmService.findAlarms(space);
        
        // then
		DeepCompareTwoLists(serviceSamsungAlarms, samsungAlarms);
		DeepCompareTwoLists(serviceLgAlarms, lgAlarms);
		Assertions.assertThat(serviceSpaceAlarms.isEmpty()).isEqualTo(true);
	}

	@Test
	void update() {
		// given
		for (Alarm alarm : samsungAlarms) {
            em.persist(alarm);
        }
		for (Alarm alarm : lgAlarms) {
            em.persist(alarm);
        }

        // when
        alarmService.updateAlarms(samsung, samsungAlarms);
		alarmService.updateAlarms(lg, lgAlarms);
        List<Alarm> serviceSamsungAlarms = alarmService.findAlarms(samsung);
        List<Alarm> serviceLgAlarms = alarmService.findAlarms(lg);
		alarmService.updateAlarms(space, emptyAlarms);

		// then
		DeepCompareTwoLists(serviceSamsungAlarms, samsungAlarms);
		DeepCompareTwoLists(serviceLgAlarms, lgAlarms);
		Assertions.assertThat(emptyAlarms.isEmpty()).isEqualTo(true);
	}
}