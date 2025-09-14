package com.wkl.study.redis.redisstudy.controller;

import com.wkl.study.redis.redisstudy.service.MysqlDemoService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/db")
public class dbController {
    @Resource
    private MysqlDemoService mysqlDemoService;

    @GetMapping("/toRedis")
    public String mySqlToRedis()
    {
        mysqlDemoService.mySqlToRedis();
        return "success";
    }
}
