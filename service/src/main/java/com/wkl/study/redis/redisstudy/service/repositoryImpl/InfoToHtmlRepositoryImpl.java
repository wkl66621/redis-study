package com.wkl.study.redis.redisstudy.service.repositoryImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wkl.study.redis.redisstudy.dataBase.mapper.InfoToHtmlMapper;
import com.wkl.study.redis.redisstudy.dataBase.po.InfoToHtmlPo;
import com.wkl.study.redis.redisstudy.repository.InfoToHtmlRepository;
import com.wkl.study.redis.redisstudy.service.entity.InfoToHtmlDto;
import org.springframework.stereotype.Component;

@Component
public class InfoToHtmlRepositoryImpl
        extends ServiceImpl<InfoToHtmlMapper, InfoToHtmlPo>
        implements InfoToHtmlRepository {

    public  InfoToHtmlPo selectById(InfoToHtmlDto infoToHtmlDto) {
        return getBaseMapper().selectById(infoToHtmlDto);
    }

    public boolean updateById(InfoToHtmlPo infoToHtmlPo) {
        return getBaseMapper().updateById(infoToHtmlPo) > 0;
    }
}
