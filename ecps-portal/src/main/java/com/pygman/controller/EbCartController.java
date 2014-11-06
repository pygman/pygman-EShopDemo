package com.pygman.controller;

import com.pygman.model.EbCart;
import com.pygman.model.EbSku;
import com.pygman.service.EbCartService;
import com.pygman.service.EbSkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by pygmalion on 2014/11/3.
 */
@Controller
@RequestMapping("/cart")
public class EbCartController {
    @Autowired
    EbCartService cartService;
    @Autowired
    EbSkuService skuService;
    @RequestMapping("/listCart.form")
    public String listCart(HttpServletRequest request,HttpServletResponse response,Model model){
        List<EbCart> cartList=cartService.selectCart(request,response);
        Integer itemNum=0;
        BigDecimal totalPrice=new BigDecimal(0);
        for(EbCart cart:cartList){
            itemNum=itemNum+cart.getQuantity();
            totalPrice=totalPrice.add(cart.getSku().getSkuPrice().multiply(new BigDecimal(cart.getQuantity())));
            model.addAttribute("itemNum",itemNum);
            model.addAttribute("totalPrice",totalPrice);
            model.addAttribute("cartList",cartList);
        }
        return "shop/car";
    }
    @RequestMapping("/addCart.form")
    public void addCart(HttpServletRequest request,HttpServletResponse response,Long skuId,Integer quantity,PrintWriter out){
        EbSku sku=skuService.selectSkuById(skuId);
        if(sku.getStockInventory()>=quantity){
            cartService.addCart(skuId,quantity,request,response);
            out.write("success");
        }else{
            out.write("stock_error");
        }
    }
    @RequestMapping("/validStockByNum.form")
    public void validStockByNum(Long skuId,Integer quantity,PrintWriter out){
        String flag="yes";
        EbSku sku=skuService.selectSkuById(skuId);
        if(sku.getStockInventory()<quantity){
            flag="no";
        }
        out.write(flag);
    }
    @RequestMapping("/addNum.form")
    public String addNum(HttpServletRequest request,HttpServletResponse response,Long skuId,PrintWriter out){
        cartService.addNum(skuId,request,response);
        return "redirect:listCart.form";
    }
    @RequestMapping("/clearCart.form")
    public String clearCart(HttpServletRequest request,HttpServletResponse response,Long skuId,PrintWriter out){
        cartService.clearCart(request,response);
        return "redirect:listCart.form";
    }
    @RequestMapping("/validStockAllItem.form")
    public void validStockAllItem(HttpServletRequest request,HttpServletResponse response,Long skuId)throws Exception{
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        String tip=cartService.validStockAllItem(request);
        response.getWriter().write(tip);
    }
}
