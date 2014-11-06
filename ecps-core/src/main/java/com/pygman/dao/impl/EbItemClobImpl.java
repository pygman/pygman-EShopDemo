package com.pygman.dao.impl;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import com.pygman.dao.EbItemClobDao;
import com.pygman.model.EbItemClob;

@Repository
public class EbItemClobImpl extends SqlSessionDaoSupport implements EbItemClobDao {

	String ns="com.pygman.mapper.EbItemClobMaper.";
	public void saveItemClob(EbItemClob itemClob, Long itemId) {
		itemClob.setItemId(itemId);
		this.getSqlSession().insert(ns+"insert",itemClob);
	}

}
