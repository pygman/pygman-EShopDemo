package com.pygman.dao;

import com.pygman.model.TsPtlUser;

import java.util.Map;

/**
 * Created by pygmalion on 2014/11/2.
 */
public interface TsPtlUserDao {
    public TsPtlUser selectUserByUserPass(Map<String,String> map);
    public void updateUser(TsPtlUser user);
    public TsPtlUser selectUserById(Long userId);
}
