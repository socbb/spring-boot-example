package com.socbb.service;

import com.socbb.bean.Menu;

import java.util.List;

/**
 * create by socbb on 2019/3/28 16:35.
 */
public interface RoleMenuService {

    List<Menu> findByRoleId(Long... roleIds);

}
