package com.pygman.service;

import com.pygman.model.EbSku;
import org.springframework.stereotype.Service;

/**
 * Created by pygmalion on 2014/11/1.
 */
public interface EbSkuService {
    public EbSku selectSkuById(Long skuID);
}
