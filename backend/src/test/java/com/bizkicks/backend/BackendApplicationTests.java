package com.bizkicks.backend;

import com.bizkicks.backend.entity.ContractRelation;
import com.bizkicks.backend.entity.CustomerCompany;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.time.LocalDateTime;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
@NoArgsConstructor
class BackendApplicationTests {

	private EntityManager em;

	@Test
	void createContractRelationTest() {

//		CustomerCompany customerCompany1 = new CustomerCompany("123456", "씽씽");
//		em.persist(customerCompany1);
//
//		ContractRelation contractRelation1 = new ContractRelation(LocalDateTime.now(), "measuredRate");
//		contractRelation1.setRelationWithCustomerCompany(customerCompany1);
//
//		em.persist(contractRelation1);
//
//		ContractRelation contractRelation2 = new ContractRelation(LocalDateTime.now(), "fixedCharge");
//		contractRelation2.setRelationWithCustomerCompany(customerCompany1);
//
//		em.persist(contractRelation2);


	}

}
