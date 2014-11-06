package com.pygman.dao;

import com.pygman.model.EbShipAddr;

import java.util.List;

/**
 * Created by pygmalion on 2014/11/2.
 */
public interface EbShipAddrDao {
    public List<EbShipAddr> selectAddrByUserId(Long userId);
    public EbShipAddr selectAddrById(Long shipAddrId);
    public void saveAddr(EbShipAddr addr);
    public void updateAddr(EbShipAddr addr);
    public EbShipAddr selectDefaultAddr(Long userId);
}
