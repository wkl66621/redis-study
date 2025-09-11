package com.wkl.study.redis.redisstudy.service;

import com.wkl.study.redis.redisstudy.dataBase.mapper.InfoToHtmlMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class mysqlDemoService {
    @Autowired
    private InfoToHtmlMapper infoToHtmlMapper;
}
