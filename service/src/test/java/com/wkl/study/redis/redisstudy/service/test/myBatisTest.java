package com.wkl.study.redis.redisstudy.service.test;

import com.wkl.study.redis.redisstudy.dataBase.po.InfoToHtmlPo;
import com.wkl.study.redis.redisstudy.RedisStudyApplication;
import com.wkl.study.redis.redisstudy.service.entity.InfoToHtmlDto;
import com.wkl.study.redis.redisstudy.service.repositoryImpl.InfoToHtmlRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = RedisStudyApplication.class)
public class myBatisTest {

    @Autowired
    InfoToHtmlRepositoryImpl infoToHtmlRepositoryImpl;

    @Test
    public void f1() {
        InfoToHtmlPo infoToHtmlPo;
        InfoToHtmlDto infoToHtmlDto = new InfoToHtmlDto();
        Integer id = 1;
        infoToHtmlDto.setId(id);
        infoToHtmlPo = infoToHtmlRepositoryImpl.selectById(infoToHtmlDto);
        System.out.println(infoToHtmlPo);
    }

}
