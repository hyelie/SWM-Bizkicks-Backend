package com.bizkicks.backend;

import org.assertj.core.api.Assertions;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
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
import com.bizkicks.backend.repository.AlarmRepository;
import com.bizkicks.backend.service.AlarmService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.ArrayList;
import java.util.List;


@SpringBootTest
@Transactional
@NoArgsConstructor
class AlarmRepositoryTest{
	@Autowired AlarmRepository alarmRepository;

	CustomerCompany samsung;
	Alarm samsungAlarm1;
	Alarm samsungAlarm2;
	List<Alarm> samsungAlarms;
	CustomerCompany lg;
	Alarm lgAlarm1;
	Alarm lgAlarm2;
	List<Alarm> lgAlarms;
	List<Alarm> emptyAlarms;

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

	public CustomerCompany CreateCustomerCompany(String companyCode, String companyName){
		CustomerCompany customerCompany = new CustomerCompany(companyName, companyCode);
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
	void save_and_find() {
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
	void save_delete_and_find() {
        // given
        CustomerCompany apple = CreateCustomerCompany("qwer123ty", "apple");

		// when
		alarmRepository.saveAllAlarmsInCustomerCompany(samsung, samsungAlarms);
		alarmRepository.saveAllAlarmsInCustomerCompany(lg, lgAlarms);
		alarmRepository.deleteAllAlarmsInCustomerCompany(samsung);
		alarmRepository.deleteAllAlarmsInCustomerCompany(lg);

		// then
		Assertions.assertThat(alarmRepository.findByCustomerCompany(samsung)).isEqualTo(emptyAlarms);
		Assertions.assertThat(alarmRepository.findByCustomerCompany(apple)).isEqualTo(emptyAlarms);
		Assertions.assertThat(alarmRepository.findByCustomerCompany(lg)).isEqualTo(emptyAlarms);
	}

    @Test
    void verify_find_not_exist_company(){
        // given
        // when
        CustomerCompany isExist = alarmRepository.isCustomerCompanyExist("apple");
        
        // then
        Assertions.assertThat(isExist).isEqualTo(null);
    }

    @Test
    void verify_find_exist_company(){
        // given
        // when
        CustomerCompany isExist = alarmRepository.isCustomerCompanyExist("samsung");
        
        // then
        Assertions.assertThat(isExist).isNotEqualTo(samsung);
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
	List<Alarm> emptyAlarms;

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
        em.persist(alarm);
		return alarm;
	}

	@Test
	void find() {
		// given
        // when
        List<Alarm> serviceSamsungAlarms = alarmService.findAlarms("삼성");
        List<Alarm> serviceLgAlarms = alarmService.findAlarms("LG");
        
        // then
        Assertions.assertThat(serviceSamsungAlarms.equals(samsungAlarms)).isTrue();
        Assertions.assertThat(serviceLgAlarms.equals(lgAlarms)).isTrue();

	}

	@Test
	void update() {
		// given
        // when
        alarmService.updateAlarms("삼성", samsungAlarms);
		alarmService.updateAlarms("LG", lgAlarms);

		// then
        List<Alarm> serviceSamsungAlarms = alarmService.findAlarms("삼성");
        List<Alarm> serviceLgAlarms = alarmService.findAlarms("LG");
        Assertions.assertThat(serviceSamsungAlarms.equals(samsungAlarms)).isTrue();
        Assertions.assertThat(serviceLgAlarms.equals(lgAlarms)).isTrue();
	}
}

@WebMvcTest(AlarmApi.class)
class AlarmControllerTest{
	@Autowired
	MockMvc mockMvc;

	@MockBean
	AlarmService alarmService;

	@Autowired
	AlarmApi alarmApi;

	private Cookie samsungCookie = new Cookie("company", "삼성");

	@BeforeEach
	void setUp(){
		mockMvc = MockMvcBuilders.standaloneSetup(alarmApi).build();
	}

	@Test
	void showAlarms() throws Exception{
		// given
		JSONArray jal = new JSONArray();
		JSONObject alarm = new JSONObject();
        alarm.put("list", jal);
		String alarmJsonString = alarm.toString();

		// when
		// then
		mockMvc.perform(get("/manage/alarms")
						.cookie(samsungCookie))
							.andExpect(status().isOk())
							.andExpect(content().contentType(MediaType.APPLICATION_JSON))
							.andExpect(content().json(alarmJsonString));
	}

	@Test
	void updateAlarms() throws Exception{
		//given
		JSONObject alarm1 = new JSONObject();
		alarm1.put("type", "cost");
        alarm1.put("value", 100000);
        JSONObject alarm2 = new JSONObject();
        alarm2.put("type", "time");
        alarm2.put("value", 80);
        JSONArray jal = new JSONArray();
        jal.put(alarm1);
        jal.put(alarm2);
        JSONObject alarm = new JSONObject();
        alarm.put("list", jal);
		String alarmJsonString = alarm.toString();

		JSONObject okMsg = new JSONObject();
        okMsg.put("msg", "Success");

		// when
		mockMvc.perform(post("/manage/alarms")
						.cookie(samsungCookie)
						.contentType(MediaType.APPLICATION_JSON)
						.content(alarmJsonString))
							.andExpect(status().isCreated())
							.andExpect(content().json(okMsg.toString()));

							
	}

	@Test
	void verifyExistCookie() throws Exception{
		//given
        JSONArray jal = new JSONArray();
        JSONObject alarm = new JSONObject();
        alarm.put("list", jal);
		String alarmJsonString = alarm.toString();

		JSONObject okMsg = new JSONObject();
        okMsg.put("msg", "Success");

		// when
		// then
		mockMvc.perform(post("/manage/alarms")
						.cookie(samsungCookie)
						.contentType(MediaType.APPLICATION_JSON)
						.content(alarmJsonString))
							.andExpect(status().isCreated())
							.andExpect(content().json(okMsg.toString()));						
	}

	// @Test
	// void verifyNotExistCookie() throws Exception{
	// 	//given
	// 	Cookie notExistCookie = new Cookie("company", "null");

	// 	// when
	// 	// then
	// 	mockMvc.perform(get("/manage/alarms")
	// 					.cookie(notExistCookie))
	// 						.andExpect(status().isBadRequest());
	// }

	// TODO
	// cookie 안/잘못 넣었을 때 400번
	// 내용 잘못되었을 때 400번
}