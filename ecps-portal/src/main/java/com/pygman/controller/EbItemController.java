package com.pygman.controller;

import com.pygman.model.EbBrand;
import com.pygman.model.EbFeature;
import com.pygman.model.EbItem;
import com.pygman.model.EbSku;
import com.pygman.service.EbBrandService;
import com.pygman.service.EbFeatureService;
import com.pygman.service.EbItemService;
import com.pygman.service.EbSkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.PrintWriter;
import java.util.List;

/**
 * Created by pygmalion on 2014/11/1.
 */
@Controller
@RequestMapping("/item")
public class EbItemController {
    @Autowired
    EbBrandService brandService;
    @Autowired
    EbFeatureService featureService;
    @Autowired
    EbItemService itemService;
    @Autowired
    EbSkuService skuService;
    @RequestMapping("/toIndex.form")
    public String toIndex(Model model){
        List<EbBrand> brandList=brandService.selectBrandAll();
        List<EbFeature> featureList=featureService.selectIsSelectFeature();
        model.addAttribute("bList",brandList);
        model.addAttribute("fList", featureList);
        return "index";
    }
    @RequestMapping("/listItem.form")
    public String listItem(String prices,Long brandId,String paraListStr,Model model){
        List<EbItem> itemList=itemService.listItem(prices,brandId,paraListStr);
        model.addAttribute("itemList",itemList);
        return "phoneClassification";
    }
    @RequestMapping("/productDetail.form")
    public String productDetail(Long itemId,Model model){
        EbItem item=itemService.productDetail(itemId);
        model.addAttribute("item",item);
        return "productDetail";
    }
    @RequestMapping("/validStock.form")
    public void validStock(Long skuId,PrintWriter out){
        EbSku sku=skuService.selectSkuById(skuId);
        String flag="yes";
        if(sku.getStockInventory()<=0){
            flag="no";
        }
        out.write(flag);
    }
}
