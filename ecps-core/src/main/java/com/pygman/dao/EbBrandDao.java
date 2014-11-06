package com.pygman.dao;

import java.util.List;

import com.pygman.model.EbBrand;

public interface EbBrandDao {
	public void saveBrand(EbBrand brand);
	public EbBrand getBrandById(Long brandId);
	public void updateBrand(EbBrand brand);
	public void deleteBrand(Long brandId);
	public List<EbBrand> selectBrandAll();
	public List<EbBrand> selectBrandByName(String brandName);
}
