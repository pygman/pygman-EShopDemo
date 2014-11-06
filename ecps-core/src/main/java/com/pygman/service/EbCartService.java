package com.pygman.service;

import com.pygman.model.EbCart;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by pygmalion on 2014/11/2.
 */
public interface EbCartService {
    public List<EbCart> selectCart(HttpServletRequest request,HttpServletResponse response);
    public void addCart(Long skuId,Integer quantity,HttpServletRequest request,HttpServletResponse response);
    public void removeCart(Long skuId,HttpServletRequest request,HttpServletResponse response);
    public void addNum(Long skuId,HttpServletRequest request,HttpServletResponse response);
    public void reduceNum(Long skuId,HttpServletRequest request,HttpServletResponse response);
    public void clearCart(HttpServletRequest reques,HttpServletResponse response);
    public String validStockAllItem(HttpServletRequest request);
}
