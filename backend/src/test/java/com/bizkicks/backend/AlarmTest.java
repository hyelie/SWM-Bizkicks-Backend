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
		CustomerCompany customerCompany = new CustomerCompany(companyCode, companyName);
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
		alarmRepository.saveAllAlarmsInCustomerCompany("삼성", samsungAlarms);
		alarmRepository.saveAllAlarmsInCustomerCompany("LG", lgAlarms);

		// then
		List<Alarm> repositorySamsungAlarms = alarmRepository.findByCustomerCompanyName("삼성");
		DeepCompareTwoLists(repositorySamsungAlarms, samsungAlarms);
		List<Alarm> repositoryLgAlarms = alarmRepository.findByCustomerCompanyName("LG");
		DeepCompareTwoLists(repositoryLgAlarms, lgAlarms);
	}

	@Test
	void save_delete_and_find() {
		// when
		alarmRepository.saveAllAlarmsInCustomerCompany("삼성", samsungAlarms);
		alarmRepository.saveAllAlarmsInCustomerCompany("LG", lgAlarms);
		alarmRepository.deleteAllAlarmsInCustomerCompany("삼성");
		alarmRepository.deleteAllAlarmsInCustomerCompany("LG");

		// then
		Assertions.assertThat(alarmRepository.findByCustomerCompanyName("삼성")).isEqualTo(emptyAlarms);
		Assertions.assertThat(alarmRepository.findByCustomerCompanyName("apple")).isEqualTo(emptyAlarms);
		Assertions.assertThat(alarmRepository.findByCustomerCompanyName("LG")).isEqualTo(emptyAlarms);
	}

	
}

@SpringBootTest
@Transactional
@NoArgsConstructor
class AlarmServiceTest{
	@Autowired AlarmService alarmService;
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
		lg = CreateCustomerCompany("qwerty", "삼성");
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
		CustomerCompany customerCompany = new CustomerCompany(companyCode, companyName);
		return customerCompany;
	}
	
	public Alarm CreateAlarmInCompany(CustomerCompany customerCompany, String type, Integer value){
		Alarm alarm = new Alarm(type, value);
		alarm.setRelationWithCustomerCompany(customerCompany);
		return alarm;
	}

	@Test
	void find() {
		// when
		alarmRepository.saveAllAlarmsInCustomerCompany("삼성", samsungAlarms);
		alarmRepository.saveAllAlarmsInCustomerCompany("LG", lgAlarms);

		// then
		List<Alarm> repositorySamsungAlarms = alarmRepository.findByCustomerCompanyName("삼성");
		DeepCompareTwoLists(repositorySamsungAlarms, samsungAlarms);
		List<Alarm> repositoryLgAlarms = alarmRepository.findByCustomerCompanyName("LG");
		DeepCompareTwoLists(repositoryLgAlarms, lgAlarms);
		
	}

	@Test
	void find_not_exist_company(){
		List<Alarm> repositoryEmptyAlarms = alarmRepository.findByCustomerCompanyName("asdf");
		DeepCompareTwoLists(repositoryEmptyAlarms, emptyAlarms);
	}

	@Test
	void update() {
		// when
		alarmRepository.saveAllAlarmsInCustomerCompany("삼성", samsungAlarms);
		alarmRepository.saveAllAlarmsInCustomerCompany("삼성", samsungAlarms);
		alarmService.updateAlarms("삼성", samsungAlarms);
		alarmRepository.saveAllAlarmsInCustomerCompany("LG", lgAlarms);
		alarmService.updateAlarms("LG", lgAlarms);

		// then
		List<Alarm> repositorySamsungAlarms = alarmRepository.findByCustomerCompanyName("삼성");
		DeepCompareTwoLists(repositorySamsungAlarms, samsungAlarms);
		List<Alarm> repositoryLgAlarms = alarmRepository.findByCustomerCompanyName("LG");
		DeepCompareTwoLists(repositoryLgAlarms, lgAlarms);
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

	@Test
	void verifyNotExistCookie() throws Exception{
		//given
		Cookie notExistCookie = new Cookie("company", "null");

		// when
		// then
		mockMvc.perform(get("/manage/alarms")
						.cookie(notExistCookie))
							.andExpect(status().isBadRequest());
	}

	// TODO
	// cookie 안/잘못 넣었을 때 400번
	// 내용 잘못되었을 때 400번
}