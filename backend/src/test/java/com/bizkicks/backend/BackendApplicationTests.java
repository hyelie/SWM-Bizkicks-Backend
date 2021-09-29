package com.bizkicks.backend;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.NoArgsConstructor;

import java.util.List;

import javax.transaction.Transactional;



@SpringBootTest
@Transactional
@NoArgsConstructor
class BackendApplicationTests {

    @Test
    void asdf(){
		LOG.debug( "#ex1 - debug log" );
		LOG.info( "#ex1 - info log" );
		LOG.warn( "#ex1 - warn log" );
		LOG.error( "#ex1 - error log" );
		
		return "콘솔 또는 파일경로 확인";
    }
}







