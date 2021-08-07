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

	AlarmRepository alarmRepository;

	@Test
	void findAlarmTest(){

	}
}
