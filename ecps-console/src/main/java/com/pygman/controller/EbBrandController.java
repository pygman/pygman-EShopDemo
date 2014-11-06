package com.pygman.controller;

import java.io.PrintWriter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pygman.model.EbBrand;
import com.pygman.service.EbBrandService;

@Controller
@RequestMapping("/brand")
public class EbBrandController {

	@Autowired
	private EbBrandService brandService;
	
	@RequestMapping("/toItemIndex.form")
	public String toItemIndex(){
		return "item/index";
	}
	
	@RequestMapping("toAdd.form")
	public String toAdd(){
		return "item/addbrand";
	}

	@RequestMapping("/validBrandName.form")
	public void validBrandName(String brandName,PrintWriter out){
		List<EbBrand> bList=brandService.selectBrandByName(brandName);
		String flag ="no";
		if(bList.size()>0){
			flag="yes";
		}
		out.write(flag);
	}

    @RequestMapping("/selectBrandAll.form")
    public String selectBrandAll(Model model){
        List<EbBrand> bList=brandService.selectBrandAll();
        model.addAttribute("bList",bList);
        return "item/listbrand";
    }

	@RequestMapping("/addBrand.form")
	public String addBrand(EbBrand brand){
		brandService.saveBrand(brand);
		return "redirect:selectBrandAll.form";
	}
	
	@RequestMapping("/deleteBrand.form")
	public String deleteBrand(Long brandId){
		brandService.deleteBrand(brandId);
		return "redirect:selectBrandAlll.form";
	}
	
	@RequestMapping("/getBrand.form")
	public String getBrand(Long brandId,Model model){
		EbBrand brand=brandService.getBrandById(brandId);
		model.addAttribute("brand",brand);
		return "item/editbrand";
	}
	
}
