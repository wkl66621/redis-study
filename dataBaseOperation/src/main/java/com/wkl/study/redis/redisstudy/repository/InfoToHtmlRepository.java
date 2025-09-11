package com.wkl.study.redis.redisstudy.repository;

import com.wkl.study.redis.redisstudy.dataBase.po.InfoToHtmlPo;
import com.wkl.study.redis.redisstudy.service.entity.InfoToHtmlDto;

public interface InfoToHtmlRepository {
    InfoToHtmlPo selectById(InfoToHtmlDto infoToHtmlDto);
}
