package com.bizkicks.backend;


import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import com.bizkicks.backend.entity.Consumption;
import com.bizkicks.backend.entity.User;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.NoArgsConstructor;



@SpringBootTest
@Transactional
@NoArgsConstructor
class BackendApplicationTests {
    @PersistenceContext EntityManager em;

    


}






