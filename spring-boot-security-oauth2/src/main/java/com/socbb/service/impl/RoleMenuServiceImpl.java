package com.socbb.service.impl;

import com.socbb.bean.Menu;
import com.socbb.bean.RoleMenu;
import com.socbb.dao.RoleMenuDao;
import com.socbb.service.RoleMenuService;
import com.socbb.utils.SearchFilter;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * create by socbb on 2019/3/28 16:35.
 */
@Service
public class RoleMenuServiceImpl implements RoleMenuService {

    @Autowired
    private RoleMenuDao roleMenuDao;

    @Override
    public List<Menu> findByRoleId(Long... roleIds) {
        Map<String, String[]> params = new HashMap<>();
        params.put("IN_Jrole.id_Long", Arrays.stream(roleIds).map(String::valueOf).toArray(String[]::new));
        List<RoleMenu> roleMenus = roleMenuDao.findAll(spec(params));
        if (CollectionUtils.isEmpty(roleMenus)) {
            return null;
        }
        return roleMenus.stream().map(RoleMenu::getMenu).collect(Collectors.toList());
    }

    private Specification<RoleMenu> spec(Map<String, String[]> params) {
        return SearchFilter.spec(SearchFilter.parse(params).values(), RoleMenu.class);
    }
}
