package com.pygman.dao;

import java.util.List;

import com.pygman.model.EbParaValue;

public interface EbParaValueDao {

	public void saveParaValue(List<EbParaValue> paraValueList, Long itemId);
}
