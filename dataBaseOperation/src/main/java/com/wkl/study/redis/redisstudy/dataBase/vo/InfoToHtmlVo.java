package com.wkl.study.redis.redisstudy.dataBase.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class InfoToHtmlVo implements Serializable {
    private Integer id;

    private String name;

    private String phone;
}
