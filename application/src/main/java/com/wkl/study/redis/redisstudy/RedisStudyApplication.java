package com.wkl.study.redis.redisstudy;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ConfigurableApplicationContext;

@MapperScan("com.wkl.study.redis.redisstudy.dataBase.mapper")
@SpringBootApplication
public class RedisStudyApplication {

    // 注入配置文件中的url
    @Value("${spring.datasource.url:未读取到配置}")
    private String dbUrl;

    public static void main(String[] args) {
        // 启动Spring上下文并获取上下文对象
        ConfigurableApplicationContext context = SpringApplication.run(RedisStudyApplication.class, args);
        // 获取当前类的Bean，打印配置值
        RedisStudyApplication app = context.getBean(RedisStudyApplication.class);
        System.out.println("===== 数据库URL配置：" + app.dbUrl + " =====");
    }

}