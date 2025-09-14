package com.wkl.study.redis.redisstudy.service;

import com.wkl.study.redis.redisstudy.dataBase.mapper.InfoToHtmlMapper;
import com.wkl.study.redis.redisstudy.dataBase.po.InfoToHtmlPo;
import com.wkl.study.redis.redisstudy.service.entity.InfoToHtmlDto;
import com.wkl.study.redis.redisstudy.service.repositoryImpl.InfoToHtmlRepositoryImpl;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class mysqlDemoService {
    @Autowired
    InfoToHtmlRepositoryImpl infoToHtmlRepositoryImpl;

    @Autowired
    RedisTemplate redisTemplate;

    public void mySqlToRedis(){
        InfoToHtmlPo infoToHtmlPo;
        InfoToHtmlDto infoToHtmlDto = new InfoToHtmlDto();
        Integer id = 1;
        infoToHtmlDto.setId(id);
        //从数据库中去取出数据
        infoToHtmlPo = infoToHtmlRepositoryImpl.selectById(infoToHtmlDto);
        //将数据存入redis中
        redisTemplate.opsForValue().set("infoToHtmlPo",infoToHtmlPo);
        System.out.println(infoToHtmlPo);
    }
}
