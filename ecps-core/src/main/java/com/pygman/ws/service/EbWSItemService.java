package com.pygman.ws.service;

import javax.jws.WebService;

/**
 * Created by pygmalion on 2014/11/3.
 */
@WebService
public interface EbWSItemService {
    public String publishItem(Long itemId,String pass)throws Exception;
}
