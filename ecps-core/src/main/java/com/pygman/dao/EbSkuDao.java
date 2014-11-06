package com.pygman.dao;

import java.util.List;
import java.util.Map;

import com.pygman.model.EbSku;

public interface EbSkuDao {

	public void saveSku(List<EbSku> skuList, Long itemId);
    public EbSku selectSkuById(Long skuId);
    public EbSku selectSkuDetailById(Long skuId);
    public int updateStock(Map<String,Object> map);
}
