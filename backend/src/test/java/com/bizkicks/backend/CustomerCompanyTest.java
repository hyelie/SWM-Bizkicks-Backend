// package com.bizkicks.backend;

// import org.assertj.core.api.Assertions;
// import org.json.JSONArray;
// import org.json.JSONObject;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.springframework.boot.test.context.SpringBootTest;

// import org.springframework.test.context.ActiveProfiles;

// import javax.transaction.Transactional;

// import com.bizkicks.backend.repository.CustomerCompanyRepository;

// import lombok.NoArgsConstructor;

// import org.springframework.beans.factory.annotation.Autowired;


// import java.util.ArrayList;
// import java.util.List;

// @SpringBootTest
// @Transactional
// @NoArgsConstructor
// public class CustomerCompanyTest {
//     @Autowired CustomerCompanyRepository customerCompanyRepository;

//     @Test
// 	void is_company_exist(){
// 		// when
// 		boolean isSamsungExist = customerCompanyRepository.isCustomerCompanyExist("삼성");
// 		boolean isLGExist = customerCompanyRepository.isCustomerCompanyExist("LG");
// 		boolean isHyundaiExist = customerCompanyRepository.isCustomerCompanyExist("현대");

// 		// then
// 		Assertions.assertThat(isSamsungExist).isEqualTo(true);
// 		Assertions.assertThat(isLGExist).isEqualTo(true);
// 		Assertions.assertThat(isHyundaiExist).isEqualTo(false);
// 	}
// }
