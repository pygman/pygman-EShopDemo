package com.pygman.service.impl;

import com.pygman.dao.EbSkuDao;
import com.pygman.model.EbSku;
import com.pygman.service.EbSkuService;
import com.pygman.service.EbSkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by pygmalion on 2014/11/1.
 */
@Service
public class EbSkuServiceImpl implements EbSkuService {
    @Autowired
    private EbSkuDao skuDao;
    @Override
    public EbSku selectSkuById(Long skuID) {
        return skuDao.selectSkuById(skuID);
    }
}
