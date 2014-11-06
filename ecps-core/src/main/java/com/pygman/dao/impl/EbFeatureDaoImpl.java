package com.pygman.dao.impl;

import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import com.pygman.dao.EbFeatureDao;
import com.pygman.model.EbFeature;

@Repository
public class EbFeatureDaoImpl extends SqlSessionDaoSupport implements
		EbFeatureDao {

	String ns="com.pygman.mapper.EbFeatureMapper.";
	public List<EbFeature> selectIsSpecFeature() {
		return this.getSqlSession().selectList(ns+"selectIsSpecFeature");
    }

	public List<EbFeature> selectIsCommFeature() {
		return this.getSqlSession().selectList(ns+"selectIsCommFeature");
	}

	public List<EbFeature> selectIsSelectFeature() {
		return this.getSqlSession().selectList(ns+"selectIsSelectFeature");
	}

	public List<EbFeature> selectAllFeature() {
		return this.getSqlSession().selectList(ns+"selectAllFeature");
	}

}
