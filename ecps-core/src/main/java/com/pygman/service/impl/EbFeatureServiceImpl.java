package com.pygman.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pygman.dao.EbFeatureDao;
import com.pygman.model.EbFeature;
import com.pygman.service.EbFeatureService;

@Service
public class EbFeatureServiceImpl implements EbFeatureService {

	@Autowired
	private EbFeatureDao featureDao;
	public List<EbFeature> selectIsSpecFeature() {
		return featureDao.selectIsSpecFeature();
	}

	public List<EbFeature> selectIsCommFeature() {
		return featureDao.selectIsCommFeature();
	}

	public List<EbFeature> selectIsSelectFeature() {
		return featureDao.selectIsSpecFeature();
	}

	public List<EbFeature> selectAllFeature() {
		return featureDao.selectAllFeature();
	}

}
