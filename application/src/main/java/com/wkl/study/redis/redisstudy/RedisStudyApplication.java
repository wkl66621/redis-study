package com.wkl.study.redis.redisstudy;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.wkl.study.redis.redisstudy.dataBase.mapper")
@SpringBootApplication
public class RedisStudyApplication {
    public static void main(String[] args) {
    }

}