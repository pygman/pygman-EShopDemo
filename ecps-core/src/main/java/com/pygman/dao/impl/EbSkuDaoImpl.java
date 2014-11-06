package com.pygman.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import com.pygman.dao.EbSkuDao;
import com.pygman.model.EbSku;
import com.pygman.model.EbSpecValue;
@Repository
public class EbSkuDaoImpl extends SqlSessionDaoSupport implements EbSkuDao {

	String ns="com.pygman.EbSkuMapper.";
	String ns1="com.pygman.EbSpecValueMapper.";
	public void saveSku(List<EbSku> skuList, Long itemId) {
		SqlSession session=this.getSqlSession();
		for(EbSku sku:skuList){
			sku.setItemId(itemId);
			session.insert(ns+"insert",sku);
			List<EbSpecValue> specList=sku.getSpecList();
			for(EbSpecValue spec:specList){
				spec.setSkuId(sku.getSkuId());
				session.insert(ns1+"insert", spec);
			}
		}
	}

    @Override
    public EbSku selectSkuById(Long skuId) {
        return this.getSqlSession().selectOne(ns+"selectByPrimaryKey",skuId);
    }

    @Override
    public EbSku selectSkuDetailById(Long skuId) {
        return this.getSqlSession().selectOne(ns+"selectSkuDetailById",skuId);
    }

    @Override
    public int updateStock(Map<String, Object> map) {
        return this.getSqlSession().update(ns+"updateStock",map);
    }


}
