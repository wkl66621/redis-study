package com.wkl.study.redis.redisstudy.service.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

@Data
@Slf4j
public class InfoToHtmlDto implements Serializable {
    private Integer id;

    private String name;

    private String phone;
}
