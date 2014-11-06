package com.pygman.service;

import java.util.List;

import com.pygman.model.EbItem;
import com.pygman.model.EbItemClob;
import com.pygman.model.EbParaValue;
import com.pygman.model.EbSku;
import com.pygman.model.QueryCondition;
import com.pygman.util.Page;

public interface EbItemService {

	public Page selectItemByConditionPage(QueryCondition qc);
	public void saveItem(EbItem item, EbItemClob itemClob, List<EbParaValue> paraList, List<EbSku> skuList);
    public void auditItem(Long itemId,Short auditStatus,String notes);
    public void showItem(Long itemId,Short showStatus,String notes);
    public List<EbItem> listItem(String price,Long brandId,String paraListStr);
    public EbItem productDetail(Long itemId);
    public String publishItem(Long itemId,String pass);
}
