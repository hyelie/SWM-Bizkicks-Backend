package com.bizkicks.backend;

import com.bizkicks.backend.entity.Alarm;
import com.bizkicks.backend.entity.ContractRelation;
import com.bizkicks.backend.entity.CustomerCompany;
import com.bizkicks.backend.repository.AlarmRepository;
import lombok.NoArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@NoArgsConstructor
class BackendApplicationTests {

	@Autowired AlarmRepository alarmRepository;

	@Test
	void createAlarmTest() {

		CustomerCompany customerCompany1 = new CustomerCompany("asdfg", "씽씽");
		Alarm alarm1 = new Alarm("cost", 10000);
		Alarm alarm2 = new Alarm("time", 100);

		List<Alarm> alarms = new ArrayList<>();
		alarms.add(alarm1);
		alarms.add(alarm2);

		alarmRepository.saveAll(alarms);
		Assertions.assertEquals(alarmRepository.findAll(), alarms);

	}

	@Test
	void findAlarmTest(){

	}
}
