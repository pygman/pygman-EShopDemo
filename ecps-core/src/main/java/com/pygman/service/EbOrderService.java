package com.pygman.service;

import com.pygman.exception.EbStockEbception;
import com.pygman.model.EbOrder;
import com.pygman.model.EbOrderDetail;
import com.pygman.model.TaskBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by pygmalion on 2014/11/2.
 */
public interface EbOrderService {
    public String saveOrder(HttpServletRequest request,HttpServletResponse response,EbOrder order,List<EbOrderDetail> detailList)throws EbStockEbception;
    public void pay(String processInstanceId,Long orderId);
    public List<TaskBean> selectOrderPay(String assignee,Short isCall);
    public List<TaskBean> selectOrderCall(String assignee);
    public TaskBean selectOrderDetailById(String taskId,Long orderId);
    public void completeCall(Long orderID);
    public void completeTask(String taskId,Long orderId,String outcome);
}
