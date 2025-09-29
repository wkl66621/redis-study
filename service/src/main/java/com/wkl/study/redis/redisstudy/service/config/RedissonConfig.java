package com.wkl.study.redis.redisstudy.service.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedissonConfig {
    @Bean(name = "myRedisson")
    public RedissonClient redissonClient(){
        Config config = new Config();
        config.useSingleServer()
                .setAddress("redis://127.0.0.1:6379")
                .setDatabase(0)
                .setConnectionMinimumIdleSize(2)
                .setConnectionPoolSize(5);
        return Redisson.create(config);
    }
}
