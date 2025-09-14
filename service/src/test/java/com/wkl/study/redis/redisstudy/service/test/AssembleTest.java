package com.wkl.study.redis.redisstudy.service.test;

import com.wkl.study.redis.redisstudy.RedisStudyApplication;
import com.wkl.study.redis.redisstudy.dataBase.InfoToHtmlPoToVoAssemble;
import com.wkl.study.redis.redisstudy.dataBase.po.InfoToHtmlPo;
import com.wkl.study.redis.redisstudy.dataBase.vo.InfoToHtmlVo;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = RedisStudyApplication.class)
public class AssembleTest {
    @Test
    public void f1() {
        InfoToHtmlPo po = new InfoToHtmlPo().setId(1).setPhone("123456").setName("wkl");
        InfoToHtmlVo vo = new InfoToHtmlVo();
        vo = InfoToHtmlPoToVoAssemble.getInstance().convert(po);
        System.out.println(vo);
    }
}
