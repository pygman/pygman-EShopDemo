package com.pygman.controller;

import com.pygman.model.TaskBean;
import com.pygman.service.EbOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Created by pygmalion on 2014/11/4.
 */
@Controller
@RequestMapping
public class EbOrderController {
    @Autowired
    EbOrderService orderService;
    @RequestMapping("/toIndex.form")
    public String toIndex(){
        return "order/index";
    }
    @RequestMapping("/listOrderPay.form")
    public String listOrderPay(String assignee,Short isCall,Model model){
        List<TaskBean> taskBeans=orderService.selectOrderPay(assignee,isCall);
        model.addAttribute("tbList",taskBeans);
        model.addAttribute("isCall",isCall);
        return "order/orderPay/orderPay";
    }
    @RequestMapping("/viewOrderDetail.form")
    public String veiwOrderDetail(String taskId,Long orderId,Model model){
        TaskBean taskBean=orderService.selectOrderDetailById(taskId, orderId);
        model.addAttribute("tb",taskBean);
        return "order/orderPay/detail";
    }
    @RequestMapping("/completeCall.form")
    public String completeCall(Long orderId,Model model){
        orderService.completeCall(orderId);
        return "redirect:listOrderPay.form?assignee/noPaidOrder&isCall=0";
    }
    @RequestMapping("/listOrderCall.form")
    public String listOrderPay(String assignee,Model model){
        List<TaskBean> taskBeanList=orderService.selectOrderCall(assignee);
        model.addAttribute("tbList",taskBeanList);
        return "order/orderCall/orderCall";
    }
    @RequestMapping("/viewOrderCallDetail.form")
    public String viewOrderCallDetail(String taskId,Long orderId,Model model){
        TaskBean taskBean=orderService.selectOrderDetailById(taskId,orderId);
        model.addAttribute("tb",taskBean);
        return "order/orderCall/detail";
    }
    @RequestMapping("/completeTalk.form")
    public String completeTask(String taskId,Long orderId,String outcome,Model model){
        orderService.completeTask(taskId,orderId,outcome);
        return "redirect:listOrderCall.form?assignee=paidOrderer";
    }
}
