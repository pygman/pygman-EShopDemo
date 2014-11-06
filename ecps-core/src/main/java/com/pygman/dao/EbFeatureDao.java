package com.pygman.dao;

import java.util.List;

import com.pygman.model.EbFeature;

public interface EbFeatureDao {

	public List<EbFeature> selectIsSpecFeature();
	public List<EbFeature> selectIsCommFeature();
	public List<EbFeature> selectIsSelectFeature();
	public List<EbFeature> selectAllFeature();
}
