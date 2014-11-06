package com.pygman.dao.impl;

import com.pygman.dao.EbOrderDao;
import com.pygman.model.EbOrder;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

/**
 * Created by pygmalion on 2014/11/2.
 */
@Repository
public class EbOrderDaoImpl extends SqlSessionDaoSupport implements EbOrderDao {
    String ns="com.pygman.mapper.EbOrderMapper";
    @Override
    public void saveOrder(EbOrder order) {
        this.getSqlSession().insert(ns+"insert",order);
    }

    @Override
    public void updateOrder(EbOrder order) {
        this.getSqlSession().update(ns+"updateByPrimaryKeySelective",order);
    }

    @Override
    public EbOrder selectOrderById(Long orderId) {
        return this.getSqlSession().selectOne(ns+"selectByPrimaryKey",orderId);
    }

    @Override
    public EbOrder selectOrderDetailById(Long orderId) {
        return this.getSqlSession().selectOne(ns+"selectOrderDetailById",orderId);
    }
}
