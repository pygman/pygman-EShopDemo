package com.pygman.controller;

import com.pygman.exception.EbStockException;
import com.pygman.model.*;
import com.pygman.service.EbCartService;
import com.pygman.service.EbOrderService;
import com.pygman.service.EbShipAddrService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by pygmalion on 2014/11/3.
 */
@Controller
@RequestMapping("/order")
public class EbOrderController {
    @Autowired
    EbShipAddrService addrService;
    @Autowired
    EbCartService cartService;
    @Autowired
    EbOrderService orderService;
    @RequestMapping("/toSubmitOrder.form")
    public String toSubmitOrder(HttpServletRequest request,HttpServletResponse response,HttpSession session,Model model){
        TsPtlUser user= (TsPtlUser) session.getAttribute("user");
        if(user==null){
            return "redirect:toLogin.form";
        }
        List<EbShipAddr> addrList=addrService.selectAddrByUserId(user.getPtlUserId());
        List<EbCart> cartList=cartService.selectCart(request,response);
        Integer itemNum=0;
        BigDecimal totalPrice=new BigDecimal(0);
        for(EbCart cart:cartList){
            itemNum=itemNum+cart.getQuantity();
            totalPrice=totalPrice.add(cart.getSku().getSkuPrice().multiply(new BigDecimal(cart.getQuantity())));
        }
        model.addAttribute("itemNum",itemNum);
        model.addAttribute("totalPrice",totalPrice);
        model.addAttribute("cartList",cartList);
        model.addAttribute("addrList",addrList);
        return "ship/confirmProductCase";
    }
    @RequestMapping("/submitOrder.form")
    public String submitOrder(String address,EbOrder order,HttpServletRequest request,HttpServletResponse response,HttpSession session,Model model){
        TsPtlUser user= (TsPtlUser) session.getAttribute("user");
        if(user==null){
            return "redirect:toLogin.form";
        }
        List<EbCart> cartList=cartService.selectCart(request,response);
        List<EbOrderDetail> detailList=new ArrayList<EbOrderDetail>();
        Integer itemNum=0;
        BigDecimal totalPrice=new BigDecimal(0);
        for(EbCart cart:cartList){
            EbOrderDetail detail=new EbOrderDetail();
            detail.setItemId(cart.getSku().getItem().getItemId());
            detail.setItemName(cart.getSku().getItem().getItemName());
            detail.setItemNo(cart.getSku().getItem().getItemNo());
            detail.setSkuId(cart.getSkuId());
            detail.setSkuPrice(cart.getSku().getSkuPrice());
            detail.setMarketPrice(cart.getSku().getMarketPrice());
            detail.setQuantity(cart.getQuantity());
            List<EbSpecValue> specValueList=cart.getSku().getSpecList();
            String result="";
            for(EbSpecValue spec:specValueList){
                result=result+spec.getSpecValue()+",";
            }
            result=result.substring(0,result.length()-1);
            detail.setSkuSpec(result);
            detailList.add(detail);
            itemNum=itemNum+cart.getQuantity();
            totalPrice=totalPrice.add(cart.getSku().getSkuPrice().multiply(new BigDecimal(cart.getQuantity())));
        }
        order.setPtlUserId(user.getPtlUserId());
        order.setUsername(user.getUsername());
        order.setOrderSum(totalPrice);
        order.setOrderNum(new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()));
        if(!StringUtils.equals(address, "add")){
            EbShipAddr addr=addrService.selectAddrById(new Long(address));
            order.setShipName(addr.getShipName());
            order.setProvince(addr.getProvince());
            order.setCity(addr.getCity());
            order.setDistrict(addr.getDistrict());
            order.setAddr(addr.getAddr());
            order.setZipCode(addr.getZipCode());
            order.setPhone(addr.getPhone());
        }
        try {
            String processInstanceId=orderService.saveOrder(request,response,order,detailList);
            model.addAttribute("order",order);
            model.addAttribute("processInstanceId",processInstanceId);
        } catch (EbStockException ebStockEbception) {
            model.addAttribute("tip","stock_error");
        }
        return "shop/confirmProductCase2";
    }
    @RequestMapping("/pay.form")
    public void pay(String processInstanceId,Long orderId,PrintWriter out){
        orderService.pay(processInstanceId,orderId);
        out.write("success");
    }
}
