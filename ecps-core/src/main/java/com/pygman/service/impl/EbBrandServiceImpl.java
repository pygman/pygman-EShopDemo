package com.pygman.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pygman.dao.EbBrandDao;
import com.pygman.model.EbBrand;
import com.pygman.service.EbBrandService;

@Service
public class EbBrandServiceImpl implements EbBrandService {

	@Autowired
	private EbBrandDao brandDao;
	public void saveBrand(EbBrand brand) {
		brandDao.saveBrand(brand);
	}

	public EbBrand getBrandById(Long brandId) {
		return brandDao.getBrandById(brandId);
	}

	public void updateBrand(EbBrand brand) {
		brandDao.updateBrand(brand);
	}

	public void deleteBrand(Long brandId) {
		brandDao.deleteBrand(brandId);
	}

	public List<EbBrand> selectBrandAll() {
		return brandDao.selectBrandAll();
	}

	public List<EbBrand> selectBrandByName(String brandName) {
		return brandDao.selectBrandByName(brandName);
	}
}
