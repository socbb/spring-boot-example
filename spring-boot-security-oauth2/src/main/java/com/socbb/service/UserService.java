package com.socbb.service;

import com.socbb.bean.LoginAppUser;

/**
 * create by socbb on 2019/3/24 11:28.
 */
public interface UserService {

    LoginAppUser findByUsername(String username);

}
