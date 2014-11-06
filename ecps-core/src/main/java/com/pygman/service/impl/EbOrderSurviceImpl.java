package com.pygman.service.impl;

import com.pygman.dao.EbOrderDao;
import com.pygman.dao.EbOrderDetailDao;
import com.pygman.dao.EbSkuDao;
import com.pygman.exception.EbStockException;
import com.pygman.model.EbOrder;
import com.pygman.model.EbOrderDetail;
import com.pygman.model.TaskBean;
import com.pygman.service.EbCartService;
import com.pygman.service.EbOrderService;
import com.pygman.service.FlowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Created by pygmalion on 2014/11/3.
 */
@Service
public class EbOrderSurviceImpl implements EbOrderService {
    @Autowired
    private EbOrderDao orderDao;
    @Autowired
    private EbOrderDetailDao orderDetailDao;
    @Autowired
    private EbSkuDao skuDao;
    @Autowired
    private EbCartService cartService;
    @Autowired
    private FlowService flowService;
    @Override
    public String saveOrder(HttpServletRequest request, HttpServletResponse response, EbOrder order, List<EbOrderDetail> detailList) throws EbStockException {
        orderDao.saveOrder(order);
        Map<String,Object> map=new HashMap<String,Object>();
        for(EbOrderDetail detail:detailList){
            detail.setOrderId(order.getOrderId());
            orderDetailDao.saveOrderDetail(detail);
            map.put("skuId",detail.getSkuId());
            map.put("quantity",detail.getQuantity());
            int flag=skuDao.updateStock(map);
            if(flag==0){
                throw new EbStockException("库存不足");
            }
        }
        String proccessInstanceId=flowService.startProcess(order.getOrderId());
        cartService.clearCart(request,response);
        return proccessInstanceId;
    }

    @Override
    public void pay(String processInstanceId, Long orderId) {
        EbOrder order=new EbOrder();
        order.setIsPaid((short)1);
        order.setOrderId(orderId);
        orderDao.updateOrder(order);
        flowService.completeTaskByPI(processInstanceId,"付款");
    }

    @Override
    public List<TaskBean> selectOrderPay(String assignee, Short isCall) {
        List<TaskBean> tbList=flowService.selectTaskByAssignee(assignee);
        List<TaskBean> tbList1=new ArrayList<TaskBean> ();
        for(TaskBean tb:tbList){
            String businessKey=tb.getBusinessKey();
            EbOrder order=orderDao.selectOrderById(new Long(businessKey));
            if(order.getIsCall().shortValue()==isCall.shortValue()){
                tb.setOrder(order);
                tbList1.add(tb);
            }
        }
        return tbList1;
    }

    @Override
    public List<TaskBean> selectOrderCall(String assignee) {
        List<TaskBean> tbList=flowService.selectTaskByAssignee(assignee);
        for(TaskBean tb:tbList){
            String businessKey=tb.getBusinessKey();
            EbOrder order=orderDao.selectOrderById(new Long(businessKey));
            tb.setOrder(order);
        }
        return tbList;
    }

    @Override
    public TaskBean selectOrderDetailById(String taskId, Long orderId) {
        EbOrder order=orderDao.selectOrderDetailById(orderId);
        TaskBean tb=flowService.selectTaskBeanById(taskId);
        tb.setOrder(order);
        return tb;
    }

    @Override
    public void completeCall(Long orderID) {
        EbOrder order=new EbOrder();
        order.setOrderId(orderID);
        order.setIsCall((short)1);
        order.setUpdateTime(new Date());
        orderDao.updateOrder(order);
    }

    @Override
    public void completeTask(String taskId, Long orderId, String outcome) {
        EbOrder order=new EbOrder();
        order.setOrderId(orderId);
        order.setUpdateTime(new Date());
        orderDao.updateOrder(order);
        flowService.completeTaskById(taskId,outcome);
    }
}
