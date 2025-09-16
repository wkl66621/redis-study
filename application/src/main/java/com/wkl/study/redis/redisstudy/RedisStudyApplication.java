package com.wkl.study.redis.redisstudy;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.wkl.study.redis.redisstudy.dataBase.mapper")
@SpringBootApplication
@Slf4j
public class RedisStudyApplication {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        SpringApplication.run(RedisStudyApplication.class, args);
        log.info("RedisStudyApplication服务启动成功----------------->耗时：{}", System.currentTimeMillis() - start);
    }

}