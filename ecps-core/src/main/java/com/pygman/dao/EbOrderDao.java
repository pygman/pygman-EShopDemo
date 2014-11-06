package com.pygman.dao;

import com.pygman.model.EbOrder;

/**
 * Created by pygmalion on 2014/11/2.
 */
public interface EbOrderDao {
    public void saveOrder(EbOrder order);
    public void updateOrder(EbOrder order);
    public EbOrder selectOrderById(Long orderId);
    public EbOrder selectOrderDetailById(Long orderId);
}
