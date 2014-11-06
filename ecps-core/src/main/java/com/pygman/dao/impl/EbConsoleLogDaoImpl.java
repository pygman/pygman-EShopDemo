package com.pygman.dao.impl;

import com.pygman.dao.EbConsoleLogDao;
import com.pygman.model.EbConsoleLog;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

/**
 * Created by pygmalion on 2014/11/2.
 */
@Repository
public class EbConsoleLogDaoImpl extends SqlSessionDaoSupport implements EbConsoleLogDao {
    String ns="com.pygman.mapper.EmConsoleLogMapper.";
    @Override
    public void saveConsoleLog(EbConsoleLog log) {
        this.getSqlSession().insert(ns+"insert",log);
    }
}
