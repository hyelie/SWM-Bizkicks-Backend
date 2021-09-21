package com.bizkicks.backend;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.NoArgsConstructor;

import java.util.List;

import javax.transaction.Transactional;

import com.bizkicks.backend.auth.redis.RedisUtil;


@SpringBootTest
@Transactional
@NoArgsConstructor
class BackendApplicationTests {
    @Autowired private RedisUtil redisUtil;

    @Test
    void asdf(){
        Long a = Long.valueOf(1);
        redisUtil.set("k", "v", a);

        String value = redisUtil.get("k");

        Assertions.assertThat("v").isEqualTo(value);

    }
}






