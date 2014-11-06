package com.pygman.service;

import com.pygman.model.EbShipAddr;

import java.util.List;

/**
 * Created by pygmalion on 2014/11/2.
 */
public interface EbShipAddrService {
    public List<EbShipAddr> selectAddrByUserId(Long userId);
    public EbShipAddr selectAddrById(Long shipAddrId);
    public EbShipAddr selectDefaultAddr(Long userId);
    public void updateOrSaveAddr(EbShipAddr addr);
    public void updateDefaultAddr(Long shipAddrId,Long userId);
}
