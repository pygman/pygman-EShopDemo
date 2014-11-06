package com.pygman.dao.impl;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import com.pygman.dao.EbItemDao;
import com.pygman.model.EbItem;
import com.pygman.model.QueryCondition;

@Repository
public class EbItemDaoImpl extends SqlSessionDaoSupport implements EbItemDao {

	String ns="com.pygman.mapper.EbItemMapper.";
	public List<EbItem> selectItemByCondition(QueryCondition qc) {
		return this.getSqlSession().selectList(ns+"selectItemByCondition",qc);
	}

	public Integer selectItemByConditionCount(QueryCondition qc) {
		return this.getSqlSession().selectOne(ns+"selectItemByConditionCount",qc);
	}

	public void saveItem(EbItem item) {
		this.getSqlSession().insert(ns+"insert",item);
	}

    @Override
    public void updateItem(EbItem item) {
        this.getSqlSession().update(ns+"updateByPrimaryKeySelective",item);
    }

    @Override
    public List<EbItem> listItem(Map<String, Object> map) {
        return this.getSqlSession().selectList(ns+"listItem",map);
    }

    @Override
    public EbItem productDetail(Long itemId) {
        return this.getSqlSession().selectOne(ns+"productDetail",itemId);
    }

}
