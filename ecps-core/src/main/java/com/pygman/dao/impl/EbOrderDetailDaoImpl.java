package com.pygman.dao.impl;

import com.pygman.dao.EbOrderDetailDao;
import com.pygman.model.EbOrderDetail;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

/**
 * Created by pygmalion on 2014/11/2.
 */
@Repository
public class EbOrderDetailDaoImpl extends SqlSessionDaoSupport implements EbOrderDetailDao {
    String ns="com.pygman.mapper.EbOrderDetailMapper.";
    @Override
    public void saveOrderDetail(EbOrderDetail orderDetail) {
        this.getSqlSession().insert(ns+"insert",orderDetail);
    }
}
