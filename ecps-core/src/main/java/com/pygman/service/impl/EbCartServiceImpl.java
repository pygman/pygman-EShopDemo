package com.pygman.service.impl;

import com.pygman.dao.EbSkuDao;
import com.pygman.model.EbCart;
import com.pygman.model.EbSku;
import com.pygman.service.EbCartService;
import com.pygman.util.EbConstants;
import net.sf.json.JSONArray;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pygmalion on 2014/11/2.
 */
@Service
public class EbCartServiceImpl implements EbCartService {
    @Autowired
    private EbSkuDao skuDao;
    @Override
    public List<EbCart> selectCart(HttpServletRequest request, HttpServletResponse response) {
        List<EbCart> cartList=new ArrayList<EbCart>();
        Cookie[] cookies=request.getCookies();
        if(cookies!=null && cookies.length>0){
            for (Cookie cookie:cookies){
                String keyName=cookie.getName();
                if(StringUtils.equals(keyName, EbConstants.ECPS_COOKIE)){
                    String value=cookie.getValue();
                    value= URLDecoder.decode(value);
                    JSONArray ja=JSONArray.fromObject(value);
                    JsonConfig jc=new JsonConfig();
                    String[] excludes={"sku"};
                    jc.setExcludes(excludes);
                    jc.setRootClass(EbCart.class);
                    cartList=(List<EbCart>) JSONSerializer.toJava(ja, jc);
                    for(EbCart cart:cartList){
                        EbSku sku=skuDao.selectSkuDetailById(cart.getSkuId());
                        cart.setSku(sku);
                    }
                }
            }
        }
        return cartList;
    }

    @Override
    public void addCart(Long skuId, Integer quantity, HttpServletRequest request, HttpServletResponse response) {
        List<EbCart> cartList=new ArrayList<EbCart>();
        JsonConfig jc=new JsonConfig();
        String[] excludes={"sku"};
        jc.setExcludes(excludes);
        jc.setRootClass(EbCart.class);
        Cookie[] cookies=request.getCookies();
        if(cookies!=null && cookies.length>0){
            for(Cookie cookie:cookies){
                String keyName=cookie.getName();
                if(StringUtils.equals(keyName,EbConstants.ECPS_COOKIE)){
                    String value=cookie.getValue();
                    JSONArray ja=JSONArray.fromObject(value);
                    cartList=(List<EbCart>)JSONSerializer.toJava(ja,jc);
                    boolean isExsit=false;
                    for(EbCart cart:cartList){
                        if(skuId.longValue()==cart.getSkuId().longValue()){
                            cart.setQuantity(cart.getQuantity()+quantity);;
                            isExsit=true;
                            break;
                        }
                    }
                    if(!isExsit){
                        EbCart cart1=new EbCart();
                        cart1.setSkuId(skuId);
                        cart1.setQuantity(quantity);
                        cartList.add(cart1);
                    }
                }
            }
        }
        if(cartList.size()==0){
            EbCart cart1=new EbCart();
            cart1.setSkuId(skuId);;
            cart1.setQuantity(quantity);
            cartList.add(cart1);
        }
        JSONArray jo=JSONArray.fromObject(cartList,jc);
        String result=jo.toString();
        result= URLEncoder.encode(result);
        Cookie cookie=new Cookie(EbConstants.ECPS_COOKIE,result);
        cookie.setMaxAge(Integer.MAX_VALUE);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    @Override
    public void removeCart(Long skuId, HttpServletRequest request, HttpServletResponse response) {
        //TODO
    }

    @Override
    public void addNum(Long skuId, HttpServletRequest request, HttpServletResponse response) {
        List<EbCart> cartList=new ArrayList<EbCart>();
        JsonConfig jc=new JsonConfig();
        String[] excludes={"sku"};
        jc.setExcludes(excludes);
        jc.setRootClass(EbCart.class);
        Cookie[] cookies=request.getCookies();
        if(cookies!=null && cookies.length>0){
            for(Cookie cookie:cookies){
                String keyName=cookie.getName();
                if(StringUtils.equals(keyName,EbConstants.ECPS_COOKIE)){
                    String value=cookie.getValue();
                    value=URLDecoder.decode(value);
                    JSONArray ja=JSONArray.fromObject(value);
                    cartList= (List<EbCart>) JSONSerializer.toJava(ja,jc);
                    for(EbCart cart:cartList){
                        if(skuId.longValue()==cart.getSkuId().longValue()){
                            cart.setQuantity(cart.getQuantity()+1);
                        }
                    }
                }
            }
        }
        JSONArray jo=JSONArray.fromObject(cartList,jc);
        String result=jo.toString();
        result=URLEncoder.encode(result);
        Cookie cookie=new Cookie(EbConstants.ECPS_COOKIE,result);
        cookie.setMaxAge(Integer.MAX_VALUE);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    @Override
    public void reduceNum(Long skuId, HttpServletRequest request, HttpServletResponse response) {
        //TODO
    }

    @Override
    public void clearCart(HttpServletRequest request, HttpServletResponse response) {
        List<EbCart> cartList=new ArrayList<EbCart>();
        JsonConfig jc=new JsonConfig();
        String[] excludes={"sku"};
        jc.setRootClass(EbCart.class);
        Cookie[] cookies= request.getCookies();
        if(cookies!=null && cookies.length>0){
            for(Cookie cookie:cookies){
                String keyName=cookie.getName();
                if(StringUtils.equals(keyName,EbConstants.ECPS_COOKIE)){
                    String value=cookie.getValue();
                    value=URLDecoder.decode(value);
                    JSONArray ja=JSONArray.fromObject(value);
                    cartList= (List<EbCart>) JSONSerializer.toJava(ja,jc);
                    cartList.clear();
                }
            }
        }
        JSONArray jo=JSONArray.fromObject(cartList,jc);
        String result=jo.toString();
        result=URLEncoder.encode(result);
        Cookie cookie=new Cookie(EbConstants.ECPS_COOKIE,result);
        cookie.setMaxAge(Integer.MAX_VALUE);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    @Override
    public String validStockAllItem(HttpServletRequest request) {
        String tip="success";
        List<EbCart> cartList=new ArrayList<EbCart>();
        Cookie[] cookies=request.getCookies();
        if(cookies!=null && cookies.length>0){
            for(Cookie cookie:cookies){
                String keyName=cookie.getName();
                if(StringUtils.equals(keyName,EbConstants.ECPS_COOKIE)){
                    String value=cookie.getValue();
                    value=URLDecoder.decode(value);
                    JSONArray ja=JSONArray.fromObject(value);
                    JsonConfig jc=new JsonConfig();
                    String[] excludes={"sku"};
                    jc.setExcludes(excludes);
                    jc.setRootClass(EbCart.class);
                    cartList= (List<EbCart>) JSONSerializer.toJava(ja,jc);
                    for(EbCart cart:cartList){
                        EbSku sku=skuDao.selectSkuById(cart.getSkuId());
                        if(cart.getQuantity().intValue()>sku.getStockInventory().intValue()){
                            tip=sku.getItem().getItemName()+"库存不足"+cart.getQuantity()+"个";
                            break;
                        }
                    }
                }
            }
        }
        return tip;
    }
}
