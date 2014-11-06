package com.pygman.service;

import java.util.List;

import com.pygman.model.EbFeature;

public interface EbFeatureService {

	public List<EbFeature> selectIsSpecFeature();
	public List<EbFeature> selectIsCommFeature();
	public List<EbFeature> selectIsSelectFeature();
	public List<EbFeature> selectAllFeature();
}
