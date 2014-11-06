package com.pygman.ws.service.impl;

import com.pygman.dao.EbItemDao;
import com.pygman.model.EbItem;
import com.pygman.util.EbConstants;
import com.pygman.util.FMutil;
import com.pygman.ws.service.EbWSItemService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by pygmalion on 2014/11/3.
 */
public class EbWSItemServiceImpl implements EbWSItemService {
    @Autowired
    private EbItemDao itemDao;
    @Override
    public String publishItem(Long itemId, String pass) throws Exception {
        String tip="success";
        if (!StringUtils.equals(pass, EbConstants.WS_PASS)) {
            tip="error_pass";
        } else {
            EbItem item=itemDao.productDetail(itemId);
            Map<String,Object> map=new HashMap<String,Object>();
            map.put("item",item);
            new FMutil().ouputFile("productDetail.ftl",item.getItemId()+".html",map);
        }
        return tip;
    }
}
