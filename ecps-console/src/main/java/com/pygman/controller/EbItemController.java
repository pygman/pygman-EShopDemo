package com.pygman.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.pygman.model.*;
import com.pygman.service.EbFeatureService;
import com.pygman.service.EbItemService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pygman.service.EbBrandService;
import com.pygman.util.Page;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/item")
public class EbItemController {
	@Autowired
	private EbBrandService brandService;
	@Autowired
	private EbItemService itemService;
    @Autowired
    private EbFeatureService featureService;

	@RequestMapping("/listItem.form")
	public String listItem(QueryCondition qc,Model model){
		  List<EbBrand> bList=brandService.selectBrandAll();
		model.addAttribute("bList",bList);
		if(qc.getPageNo()==null){
			qc.setPageNo(1);
		}
		Page page=itemService.selectItemByConditionPage(qc);
		model.addAttribute("page",page);
		model.addAttribute("qc",qc);
		return "item/list";
	}
    @RequestMapping("/toAddItem.form")
    public String toAddItem(Model model){
        List<EbBrand> bList=brandService.selectBrandAll();
        List<EbFeature> commList=featureService.selectIsCommFeature();
        List<EbFeature> specList=featureService.selectIsSpecFeature();
        model.addAttribute("bList",bList);
        model.addAttribute("commList",commList);
        model.addAttribute("specList",specList);
        return "item/addItem";
    }
    @RequestMapping("/addItem.form")
    public String addItem(EbItem item,EbItemClob itemClob,HttpServletRequest request,Integer divNum,Model model){
        item.setItemNo(new SimpleDateFormat("yyyyMMDDHHmmssSSS").format(new Date()));
        if(item.getIsNew()==null){
            item.setIsNew((short)0);
        }
        if(item.getIsGood()==null){
            item.setIsGood((short) 0);
        }
        if(item.getIsHot()==null){
            item.setIsHot((short) 0);
        }
        List<EbFeature> commList=featureService.selectIsCommFeature();
        List<EbParaValue> paraList=new ArrayList<EbParaValue>();
        List<EbSku> skuList=new ArrayList<EbSku>();
        for(EbFeature feature:commList){
            Long featureId=feature.getFeatureId();
            if(feature.getInputType()==3){
                String[] results=request.getParameterValues(featureId+"");
                if(results!=null && results.length>0){
                    String manyResult="";
                    for(String  resultValue:results){
                        manyResult=manyResult=resultValue+",";
                    }
                    manyResult=manyResult.substring(0,manyResult.length()-1);
                    EbParaValue paraValue=new EbParaValue();
                    paraValue.setParaValue(manyResult);
                    paraValue.setFeatureId(featureId);
                    paraList.add(paraValue);
                }
            }else{
                String result=request.getParameter(featureId+"");
                if(result!=null && !StringUtils.equals(result, "")){
                    EbParaValue paraValue=new EbParaValue();
                    paraValue.setParaValue(result);
                    paraValue.setFeatureId(featureId);
                    paraList.add(paraValue);
                }
            }
        }
        for(int i=1;i<=divNum;i++){
            String skuPrice=request.getParameter("skuPrice"+i);
            if(skuPrice!=null){
                String skuType = request.getParameter("skuType"+i);
                String sort = request.getParameter("sort"+i);
                String sku = request.getParameter("sku"+i);
                String showStatus = request.getParameter("showStatus"+i);
                String marketPrice = request.getParameter("marketPrice"+i);
                String stockInventory = request.getParameter("stockInventory"+i);
                String skuUpperLimit = request.getParameter("skuUpperLimit"+i);
                String location = request.getParameter("location"+i);
                EbSku skuObj=new EbSku();
                if(StringUtils.isNotBlank(skuPrice)){
                    skuObj.setSkuPrice(new BigDecimal(skuPrice));
                }
                if(StringUtils.isNotBlank(skuType)){
                    skuObj.setSkuType(new Short(skuType));
                }
                if(StringUtils.isNotBlank(sort)){
                    skuObj.setSkuSort(new Integer(sort));
                }
                skuObj.setSku(sku);
                if(StringUtils.isNotBlank(showStatus)){
                    skuObj.setShowStatus(new Short(showStatus));
                }
                if(StringUtils.isNotBlank(marketPrice)){
                    skuObj.setMarketPrice(new BigDecimal(marketPrice));
                }
                if(StringUtils.isNotBlank(stockInventory)){
                    skuObj.setStockInventory(new Integer(stockInventory));
                }
                if(StringUtils.isNotBlank(skuUpperLimit)){
                    skuObj.setSkuUpperLimit(new Integer(skuUpperLimit));
                }
                skuObj.setLocation(location);
                List<EbFeature> specFList=featureService.selectIsSpecFeature();
                List<EbSpecValue> specValueList=new ArrayList<EbSpecValue>();
                for(EbFeature feature:specFList){
                    Long featureId=feature.getFeatureId();
                    String specValue=request.getParameter(featureId+"specradio"+i);
                    EbSpecValue spec=new EbSpecValue();
                    spec.setFeatureId(featureId);
                    spec.setSpecValue(specValue);
                    specValueList.add(spec);
                }
                skuObj.setSpecList(specValueList);
                skuList.add(skuObj);
            }
        }
        itemService.saveItem(item,itemClob,paraList,skuList);
        return "redirect:listItem.form";
    }
}
