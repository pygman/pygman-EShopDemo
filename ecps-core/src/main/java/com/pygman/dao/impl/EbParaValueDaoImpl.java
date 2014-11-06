package com.pygman.dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import com.pygman.dao.EbParaValueDao;
import com.pygman.model.EbParaValue;

@Repository
public class EbParaValueDaoImpl extends SqlSessionDaoSupport implements
		EbParaValueDao {

	String ns="com.pygman.mapper.EbParaValueMapper.";
	public void saveParaValue(List<EbParaValue> paraValueList, Long itemId) {
		SqlSession session=this.getSqlSession();
		for(EbParaValue para: paraValueList){
			para.setItemId(itemId);
			session.insert(ns+"insert",para);
		}
	}

}
