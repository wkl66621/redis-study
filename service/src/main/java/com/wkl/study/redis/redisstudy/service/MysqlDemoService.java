package com.wkl.study.redis.redisstudy.service;

import com.wkl.study.redis.redisstudy.dataBase.InfoToHtmlPoToVoAssemble;
import com.wkl.study.redis.redisstudy.dataBase.po.InfoToHtmlPo;
import com.wkl.study.redis.redisstudy.dataBase.vo.InfoToHtmlVo;
import com.wkl.study.redis.redisstudy.service.entity.InfoToHtmlDto;
import com.wkl.study.redis.redisstudy.service.repositoryImpl.InfoToHtmlRepositoryImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class MysqlDemoService {
    private static final Logger log = LoggerFactory.getLogger(MysqlDemoService.class);
    @Autowired
    InfoToHtmlRepositoryImpl infoToHtmlRepositoryImpl;

    @Autowired
    RedisTemplate redisTemplate1;

    @Autowired
    @Qualifier("JsonRedisTemplate")
    RedisTemplate redisTemplate2;

    public void setRedis(){
        InfoToHtmlPo infoToHtmlPo;
        InfoToHtmlDto infoToHtmlDto = new InfoToHtmlDto();
        Integer id = 1;
        infoToHtmlDto.setId(id);
        //从数据库中去取出数据
        infoToHtmlPo = infoToHtmlRepositoryImpl.selectById(infoToHtmlDto);
        InfoToHtmlVo infoToHtmlVo = InfoToHtmlPoToVoAssemble.getInstance().convertToVo(infoToHtmlPo);
        if(redisTemplate2.opsForValue().getOperations().hasKey("infoToHtmlVo" + id)){
            log.info("redis中已存在key:{}","infoToHtmlVo" + 1);
        }else{
            //将数据存入redis中
            redisTemplate2.opsForValue().set("infoToHtmlVo" + id,infoToHtmlVo);
        }
        log.info("{}",infoToHtmlVo);
    }

    public void updateRedis(){
        InfoToHtmlPo infoToHtmlPo = new InfoToHtmlPo();
        infoToHtmlPo.setId(1);
        infoToHtmlPo.setName("wkl");
        infoToHtmlPo.setPhone("123456789");
        if(infoToHtmlRepositoryImpl.updateById(infoToHtmlPo)){
            log.info("更新成功");
            if ((redisTemplate1.opsForValue().getOperations().hasKey("infoToHtmlVo" + infoToHtmlPo.getId()))){
                redisTemplate1.opsForValue().set("infoToHtmlVo" + infoToHtmlPo.getId(),
                        InfoToHtmlPoToVoAssemble.getInstance().convertToVo(infoToHtmlPo));
            }
        }
    }
}
