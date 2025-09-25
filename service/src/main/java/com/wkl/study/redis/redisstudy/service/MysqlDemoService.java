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

import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class MysqlDemoService {
    private static final Logger log = LoggerFactory.getLogger(MysqlDemoService.class);
    @Autowired
    InfoToHtmlRepositoryImpl infoToHtmlRepositoryImpl;

    @Autowired
    @Qualifier("redisTemplate")
    RedisTemplate redisTemplate1;

    @Autowired
    @Qualifier("JsonRedisTemplate")
    RedisTemplate redisTemplate2;

    public void setRedis(){
        InfoToHtmlPo infoToHtmlPo;
        InfoToHtmlDto infoToHtmlDto = new InfoToHtmlDto();
        Integer id = 1;
        infoToHtmlDto.setId(id);
        //String计数器
        String s = "0";
        String key1 = "count";
        redisTemplate2.opsForValue().set(key1,s);
        //从数据库中去取出数据
        infoToHtmlPo = infoToHtmlRepositoryImpl.selectById(infoToHtmlDto);
        InfoToHtmlVo infoToHtmlVo = InfoToHtmlPoToVoAssemble.getInstance().convertToVo(infoToHtmlPo);
        //使用json序列化
        if(redisTemplate2.opsForValue().getOperations().hasKey("infoToHtmlVo(json)" + id)){
            log.info("redis中已存在key:{}","infoToHtmlVo" + id);
        }else{
            //将数据存入redis中
            redisTemplate2.opsForValue().set("infoToHtmlVo(json)" + id, infoToHtmlVo,60, TimeUnit.MINUTES);
        }
        //使用普通序列化
        if(redisTemplate1.opsForValue().getOperations().hasKey("infoToHtmlVo(normal)" + id)){
            log.info("redis中已存在key:{}","infoToHtmlVo" + id);
        }else {
            redisTemplate1.opsForValue().set("infoToHtmlVo(normal)" + id, infoToHtmlVo,60, TimeUnit.MINUTES);
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

    public void baseTypeOperation() {
        //String缓存
        InfoToHtmlVo infoToHtmlVo = (InfoToHtmlVo) redisTemplate2.opsForValue().get("infoToHtmlVo(json)" + 1);
        if (Objects.isNull(infoToHtmlVo)){
            log.info("redis中不存在key:{}","infoToHtmlVo" + 1);
            this.setRedis();
        }
        //String计数器
        Integer s = 0;
        String key1 = "count";
        redisTemplate2.opsForValue().set(key1,s);
        redisTemplate2.opsForValue().increment(key1);
        //Hash缓存
        Map<String , Map<String ,String>> hash = new HashMap<>();
        Map<String ,String> map = new HashMap<>();
        map.put("name","wkl");
        map.put("phone","123456789");
        hash.put("infoToHtmlPo",map);
        redisTemplate2.opsForHash().putAll("infoToHtmlPo",hash);
        //List缓存
        String key2 = "list";
        redisTemplate2.opsForList().leftPush(key2,"a");
        redisTemplate2.opsForList().leftPush(key2,"b");
        redisTemplate2.opsForList().rightPush(key2,"c");
        List<String> list2 = redisTemplate2.opsForList().range(key2,0,-1);
        log.info("list2:{}",list2);
        //Set集合操作
        String key3 = "setA";
        String key4 = "setB";
        redisTemplate2.opsForSet().add(key3,"a");
        redisTemplate2.opsForSet().add(key3,"c");
        redisTemplate2.opsForSet().add(key4,"b");
        redisTemplate2.opsForSet().add(key4,"c");
        String intersect = redisTemplate2.opsForSet().intersect(key3, key4).toString();
        String union = redisTemplate2.opsForSet().union(key3, key4).toString();
        String difference = redisTemplate2.opsForSet().difference(key3, key4).toString();
        log.info("intersect:{}",intersect);
        log.info("union:{}",union);
        log.info("difference:{}",difference);
        //ZSet操作(以分数为例)
        String key5 = "zset";
        redisTemplate2.opsForZSet().add(key5,"a",1);
        redisTemplate2.opsForZSet().add(key5,"b",2);
        redisTemplate2.opsForZSet().add(key5,"c",3);
        redisTemplate2.opsForZSet().add(key5,"d",4);
        redisTemplate2.opsForZSet().add(key5,"e",5);
        log.info("zset1:{}",redisTemplate2.opsForZSet().rangeWithScores(key5,0,-1));
        redisTemplate2.opsForZSet().incrementScore(key5,"c",10);
        log.info("zset2:{}",redisTemplate2.opsForZSet().rangeWithScores(key5,0,-1));
    }
}
