package com.pygman.dao.impl;

import com.pygman.dao.EbShipAddrDao;
import com.pygman.model.EbShipAddr;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by pygmalion on 2014/11/2.
 */
@Repository
public class EbShipAddrDaoImpl extends SqlSessionDaoSupport implements EbShipAddrDao {
    String ns="com.pygman.mapper.EbShipAddrMapper.";
    @Override
    public List<EbShipAddr> selectAddrByUserId(Long userId) {
        return this.getSqlSession().selectList(ns+"selectAddrByUserId",userId);
    }

    @Override
    public EbShipAddr selectAddrById(Long shipAddrId) {
        return this.getSqlSession().selectOne(ns+"selectByPrimaryKey",shipAddrId);
    }

    @Override
    public void saveAddr(EbShipAddr addr) {
        this.getSqlSession().insert(ns+"insert",addr);
    }

    @Override
    public void updateAddr(EbShipAddr addr) {
        this.getSqlSession().update(ns+"updateByPrimaryKeySelective",addr);
    }

    @Override
    public EbShipAddr selectDefaultAddr(Long userId) {
        return this.getSqlSession().selectOne(ns+"selectDefaultAddr",userId);
    }
}
