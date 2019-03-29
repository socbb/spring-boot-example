package com.socbb.service.impl;

import com.socbb.bean.Role;
import com.socbb.bean.User;
import com.socbb.bean.UserRole;
import com.socbb.service.UserService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * create by socbb on 2019/3/23 22:12.
 */
@Component("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    @Transactional(readOnly = true)// 懒加载字段必须在事物中才能获取
    public UserDetailsImpl loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByUsername(username);
        if (user == null) {
            throw new AuthenticationCredentialsNotFoundException("用户不存在");
        }
        return new UserDetailsImpl(user, user.getRoles_());
    }
}
