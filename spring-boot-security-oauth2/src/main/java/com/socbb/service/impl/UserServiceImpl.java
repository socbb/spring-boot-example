package com.socbb.service.impl;

import com.socbb.bean.*;
import com.socbb.dao.UserDao;
import com.socbb.service.RoleMenuService;
import com.socbb.service.UserService;
import com.socbb.utils.BeanUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * create by socbb on 2019/3/24 11:28.
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleMenuService roleMenuService;

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
                    List<Menu> menus = roleMenuService.findByRoleId(role.getId());
                    if (CollectionUtils.isNotEmpty(menus)) {
                        perimssion.addAll(menus.stream().map(Menu::getPermission).collect(Collectors.toSet()));
                    }
                });
            }
            loginAppUser.setRoles(roles);
            loginAppUser.setPermissions(perimssion);
            return loginAppUser;
        }
        return null;
    }
}
