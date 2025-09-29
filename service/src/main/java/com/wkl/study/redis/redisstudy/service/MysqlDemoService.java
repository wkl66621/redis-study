package com.wkl.study.redis.redisstudy.service;

import com.wkl.study.redis.redisstudy.dataBase.InfoToHtmlPoToVoAssemble;
import com.wkl.study.redis.redisstudy.dataBase.po.InfoToHtmlPo;
import com.wkl.study.redis.redisstudy.dataBase.vo.InfoToHtmlVo;
import com.wkl.study.redis.redisstudy.service.entity.InfoToHtmlDto;
import com.wkl.study.redis.redisstudy.service.repositoryImpl.InfoToHtmlRepositoryImpl;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
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

    @Autowired
    @Qualifier("myRedisson")
    RedissonClient redissonClient;

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

    public void getLock() {
        // 1. 定义锁的key
        String lockKey = "redissonLock:";

        // 2. 获取锁对象（可重入锁）
        RLock lock = redissonClient.getLock(lockKey);

        try {
            // 3. 尝试获取锁
            // 参数说明：waitTime=10秒（最多等待10秒），leaseTime=30秒（获取锁后持有30秒）
            boolean isLocked = lock.tryLock(10, 30, TimeUnit.SECONDS);

            if (!isLocked) {
                // 获取锁失败
                System.out.println("获取锁失败，可能并发过高");
                return ;
            }

            // 4. 成功获取锁，执行临界区业务逻辑
            System.out.println("线程" + Thread.currentThread().getId() + "获取锁成功");

            // 模拟业务
            Thread.sleep(50000);

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return ;
        } finally {
            // 5. 释放锁（必须在finally中执行，确保锁一定会被释放）
            // 先判断当前线程是否持有锁，避免释放其他线程的锁
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
                System.out.println("线程" + Thread.currentThread().getId() + "释放锁成功");
            }
        }
    }

    public void tryLock() {
        // 1. 定义锁的key
        String lockKey = "redissonLock:";

        // 2. 获取锁对象（可重入锁）
        RLock lock = redissonClient.getLock(lockKey);

        try {
            // 3. 尝试获取锁
            // 参数说明：waitTime=10秒（最多等待10秒），leaseTime=30秒（获取锁后持有30秒）
            boolean isLocked = lock.tryLock(10, 30, TimeUnit.SECONDS);

            if (!isLocked) {
                // 获取锁失败
                System.out.println("获取锁失败，可能并发过高");
                return ;
            }

            // 4. 成功获取锁，执行临界区业务逻辑
            System.out.println("线程" + Thread.currentThread().getId() + "获取锁成功");

            // 模拟业务

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return ;
        } finally {
            // 5. 释放锁（必须在finally中执行，确保锁一定会被释放）
            // 先判断当前线程是否持有锁，避免释放其他线程的锁
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
                System.out.println("线程" + Thread.currentThread().getId() + "释放锁成功");
            }
        }
    }
}
