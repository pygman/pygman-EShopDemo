package com.pygman.service.impl;

import com.pygman.dao.TsPtlUserDao;
import com.pygman.model.TsPtlUser;
import com.pygman.service.TsPtlUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by pygmalion on 2014/11/2.
 */
@Service
public class TsPtlUserServiceImpl implements TsPtlUserService {
    @Autowired
    private TsPtlUserDao userDao;
    @Override
    public TsPtlUser selectUserByUserPass(Map<String, String> map) {
        return userDao.selectUserByUserPass(map);
    }

    @Override
    public void updateUser(TsPtlUser user) {
        userDao.updateUser(user);
    }

    @Override
    public TsPtlUser selectUserById(Long userId) {
        return userDao.selectUserById(userId);
    }
}
