package com.wkl.study.redis.redisstudy.dataBase;


import com.wkl.study.redis.redisstudy.dataBase.po.InfoToHtmlPo;
import com.wkl.study.redis.redisstudy.dataBase.vo.InfoToHtmlVo;

public class InfoToHtmlPoToVoAssemble {

    // 饿汉式单例，类加载时就初始化实例
    private static final InfoToHtmlPoToVoAssemble INSTANCE = new InfoToHtmlPoToVoAssemble();

    // 私有构造方法，防止外部实例化
    private InfoToHtmlPoToVoAssemble() {}

    // 获取单例实例
    public static InfoToHtmlPoToVoAssemble getInstance() {
        return INSTANCE;
    }

    /**
     * 将InfoToHtmlPo对象转换为InfoToHtmlVo对象
     * @param po 待转换的Po对象
     * @return 转换后的Vo对象，若po为null则返回null
     */
    public InfoToHtmlVo convert(InfoToHtmlPo po) {
        if (po == null) {
            return null;
        }

        InfoToHtmlVo vo = new InfoToHtmlVo();
        vo.setId(po.getId());
        vo.setName(po.getName());
        vo.setPhone(po.getPhone());
        return vo;
    }
}
