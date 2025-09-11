package com.wkl.study.redis.redisstudy.service.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


@Getter
@Setter
public class InfoToHtmlDto implements Serializable {
    private Integer id;

    private String name;

    private String phone;
}
