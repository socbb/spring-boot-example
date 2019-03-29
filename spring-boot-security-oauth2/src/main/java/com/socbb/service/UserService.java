package com.socbb.service;

import com.socbb.bean.Role;
import com.socbb.bean.User;

import java.util.List;
import java.util.Set;

/**
 * create by socbb on 2019/3/24 11:28.
 */
public interface UserService {

    Set<Role> findRolesById(Long id);

    User findByUsername(String username);

}
