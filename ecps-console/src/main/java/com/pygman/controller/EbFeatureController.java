package com.pygman.controller;

import com.pygman.model.EbFeature;
import com.pygman.service.EbFeatureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/feature")
public class EbFeatureController {
	@Autowired
	private EbFeatureService featureService;
	
	@RequestMapping("/selectAllFeature.form")
    public String selectAllFeature(Model model){
        List<EbFeature> fList=featureService.selectAllFeature();
        model.addAttribute("fList",fList);
        return "item/listfeature";
    }
}
