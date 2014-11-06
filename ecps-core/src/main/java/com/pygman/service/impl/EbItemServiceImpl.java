package com.pygman.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pygman.dao.*;
import com.pygman.model.*;
import com.pygman.stub.EbWSItemService;
import com.pygman.stub.EbWSItemServiceService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pygman.service.EbItemService;
import com.pygman.util.Page;

@Service
public class EbItemServiceImpl implements EbItemService {

    @Autowired
    private EbItemDao itemDao;
    @Autowired
    private EbSkuDao skuDao;
    @Autowired
    private EbParaValueDao paraValueDao;
    @Autowired
    private EbItemClobDao itemClobDao;
    @Autowired
    private EbConsoleLogDao logDao;

    public Page selectItemByConditionPage(QueryCondition qc) {
        Integer totalCount = itemDao.selectItemByConditionCount(qc);
        Page page = new Page();
        page.setPageNo(qc.getPageNo());
        page.setTotalCount(totalCount);
        Integer startNum = page.getStartNum();
        Integer endNum = page.getEndNum();
        qc.setStartNum(startNum);
        qc.setEndNum(endNum);
        List<EbItem> itemList = itemDao.selectItemByCondition(qc);
        page.setList(itemList);
        return page;
    }

    public void saveItem(EbItem item, EbItemClob itemClob,

                         List<EbParaValue> paraList, List<EbSku> skuList) {
        itemDao.saveItem(item);
        itemClobDao.saveItemClob(itemClob, item.getItemId());
        paraValueDao.saveParaValue(paraList, item.getItemId());
        skuDao.saveSku(skuList, item.getItemId());
    }

    @Override
    public void auditItem(Long itemId, Short auditStatus, String notes) {
        EbItem item=new EbItem();
        item.setItemId(itemId);
        item.setAuditStatus(auditStatus);
        item.setUpdateTime(new Date());
        itemDao.updateItem(item);

        EbConsoleLog log=new EbConsoleLog();
        log.setEntityId(itemId);
        log.setEntityName("商品名");
        log.setNotes(notes);
        log.setOpTime(new Date());
        log.setOpType("商品审核");
        log.setTableName("EB_ITEM");
        log.setUserId((long) 11);
        logDao.saveConsoleLog(log);
    }

    @Override
    public void showItem(Long itemId, Short showStatus, String notes) {
        EbItem item=new EbItem();
        item.setItemId(itemId);
        item.setShowStatus(showStatus);
        item.setUpdateTime(new Date());
        itemDao.updateItem(item);

        EbConsoleLog log=new EbConsoleLog();
        log.setEntityId(itemId);
        log.setEntityName("商品表");
        log.setNotes(notes);
        log.setOpTime(new Date());
        log.setTableName("EB_ITEM");
        log.setUserId((long) 11);
        logDao.saveConsoleLog(log);
    }

    @Override
    public List<EbItem> listItem(String price, Long brandId, String paraListStr) {
        Map<String,Object> map=new HashMap<String, Object>();
        if(StringUtils.isNotBlank(price)){
            String[] prices=price.split("-");
            map.put("minPrice",prices[0]);
            map.put("maxPrice",prices[1]);
        }
        map.put("brandId",brandId);
        if(StringUtils.isNotBlank(paraListStr)){
            String[] paraList=paraListStr.split(",");
            map.put("paraList",paraList);
        }
        return itemDao.listItem(map);
    }

    @Override
    public EbItem productDetail(Long itemId) {
        return itemDao.productDetail(itemId);
    }

    @Override
    public String publishItem(Long itemId, String pass) {
        EbWSItemServiceService ebWSItemServiceService=new EbWSItemServiceService();
        EbWSItemService ebWSItemService=ebWSItemServiceService.getEbWSItemServicePort();
        return ebWSItemService.publishItem(itemId, pass);
    }

}
