package com.pygman.dao.impl;

import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import com.pygman.dao.EbBrandDao;
import com.pygman.model.EbBrand;

@Repository
public class EbBrandDaoImpl extends SqlSessionDaoSupport implements EbBrandDao {

	String ns="com.pygman.mapper.EbBrandMapper.";
	public void saveBrand(EbBrand brand) {
		this.getSqlSession().insert(ns+"insert",brand);
	}

	public EbBrand getBrandById(Long brandId) {
		return this.getSqlSession().selectOne(ns+"selectByPrimaryKey",brandId);
	}

	public void updateBrand(EbBrand brand) {
		this.getSqlSession().update(ns+"updateByPrimaryKey",brand);
	}

	public void deleteBrand(Long brandId) {
		this.getSqlSession().delete(ns+"deleteByPrimaryKey",brandId);
	}

	public List<EbBrand> selectBrandAll() {
		return this.getSqlSession().selectList(ns+"selectBrandAll");
	}

	public List<EbBrand> selectBrandByName(String brandName) {
		return this.getSqlSession().selectList(ns+"selectBrandByName",brandName);
	}

}
