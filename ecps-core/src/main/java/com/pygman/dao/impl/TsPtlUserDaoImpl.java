package com.pygman.dao.impl;

import com.pygman.dao.TsPtlUserDao;
import com.pygman.model.TsPtlUser;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * Created by pygmalion on 2014/11/2.
 */
@Repository
public class TsPtlUserDaoImpl extends SqlSessionDaoSupport implements TsPtlUserDao {
    String ns="com.pygman.mapper.TsPtlUserMapper.";
    @Override
    public TsPtlUser selectUserByUserPass(Map<String, String> map) {
        return this.getSqlSession().selectOne(ns+"selectUserByUserPass",map);
    }

    @Override
    public void updateUser(TsPtlUser user) {
        this.getSqlSession().update(ns+"updateByPrimaryKeySelective",user);
    }

    @Override
    public TsPtlUser selectUserById(Long userId) {
        return this.getSqlSession().selectOne(ns+"selectByPrimaryKey",userId);
    }
}
