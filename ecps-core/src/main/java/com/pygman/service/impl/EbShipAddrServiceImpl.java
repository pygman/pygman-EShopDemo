package com.pygman.service.impl;

import com.pygman.dao.EbShipAddrDao;
import com.pygman.model.EbShipAddr;
import com.pygman.service.EbShipAddrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by pygmalion on 2014/11/3.
 */
@Service
public class EbShipAddrServiceImpl implements EbShipAddrService {
    @Autowired
    private EbShipAddrDao addrDao;
    @Override
    public List<EbShipAddr> selectAddrByUserId(Long userId) {
        return addrDao.selectAddrByUserId(userId);
    }

    @Override
    public EbShipAddr selectAddrById(Long shipAddrId) {
        return addrDao.selectAddrById(shipAddrId);
    }

    @Override
    public EbShipAddr selectDefaultAddr(Long userId) {
        return addrDao.selectDefaultAddr(userId);
    }

    @Override
    public void updateOrSaveAddr(EbShipAddr addr) {
        if(addr.getShipAddrId()==null){
            addrDao.saveAddr(addr);
        }else{
            addrDao.updateAddr(addr);
        }
    }

    @Override
    public void updateDefaultAddr(Long shipAddrId, Long userId) {
        EbShipAddr addr1=addrDao.selectDefaultAddr(userId);
        addr1.setDefaultAddr((short)0);
        addrDao.updateAddr(addr1);
        EbShipAddr addr=new EbShipAddr();
        addr.setShipAddrId(shipAddrId);
        addr.setDefaultAddr((short)1);
        addrDao.updateAddr(addr);
    }
}
