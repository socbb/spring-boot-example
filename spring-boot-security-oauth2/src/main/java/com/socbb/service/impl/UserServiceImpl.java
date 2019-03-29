package com.socbb.service.impl;

import com.socbb.bean.Role;
import com.socbb.bean.User;
import com.socbb.dao.UserDao;
import com.socbb.dao.UserRoleDao;
import com.socbb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

/**
 * create by socbb on 2019/3/24 11:28.
 */
@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserRoleDao userRoleDao;

    @Override
    public Set<Role> findRolesById(Long id) {
        return userRoleDao.findRolesByUserId(id);
    }

    @Override
    public User findByUsername(String username) {
        User user = userDao.findByUsername(username);
        return user;
    }
}
