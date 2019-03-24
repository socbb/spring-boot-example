package com.socbb.service.impl;

import com.socbb.bean.LoginAppUser;
import com.socbb.bean.Role;
import com.socbb.bean.User;
import com.socbb.bean.UserRole;
import com.socbb.dao.UserDao;
import com.socbb.service.UserService;
import com.socbb.utils.BeanUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

/**
 * create by socbb on 2019/3/24 11:28.
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    @Transactional(readOnly = true)
    public LoginAppUser findByUsername(String username) {
        User user = userDao.findByUsername(username);
        if (user != null) {
            LoginAppUser loginAppUser = new LoginAppUser();
            BeanUtils.copyPropGetSet(user, loginAppUser, false);

            Set<Role> roles = new HashSet<>();
            Set<String> perimssion = new HashSet<>();
            Set<UserRole> userRoles = user.getUserRoles();
            if (CollectionUtils.isNotEmpty(userRoles)) {
                userRoles.stream().map(UserRole::getRole).forEach(role -> {
                    roles.add(role);
                    perimssion.addAll(role.getPermissionStr());
                });
            }
            loginAppUser.setRoles(roles);
            loginAppUser.setPermissions(perimssion);
            return loginAppUser;
        }
        return null;
    }
}
