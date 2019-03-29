package com.socbb.service.impl;

import com.socbb.bean.Menu;
import com.socbb.dao.MenuDao;
import com.socbb.service.PermissionService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * create by socbb on 2019/3/28 14:40.
 */
@Slf4j
@Component("permissionService")
public class PermissionServiceImpl implements PermissionService {

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Autowired
    private MenuDao menuDao;

    /**
     * 鉴权接口
     */
    @Override
    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {
        Object principal = authentication.getPrincipal();
        List<SimpleGrantedAuthority> authorityList = (List<SimpleGrantedAuthority>) authentication.getAuthorities();
        AtomicBoolean hasPermission = new AtomicBoolean(false);

        if (principal != null) {
            if (CollectionUtils.isEmpty(authorityList)) {
                log.warn("角色列表为空：{}", authentication.getPrincipal());
                return false;
            }

            Set<Menu> urls = new HashSet<>();
            authorityList.stream().filter(authority -> !StringUtils.equals(authority.getAuthority(), "ROLE_USER"))
                    .forEach(authority -> {
                        List<Menu> menuVOSet = menuDao.findMenusByRoleCodes(Collections.singletonList(authority.getAuthority()));
                        CollectionUtils.addAll(urls, menuVOSet);
                    });

            urls.stream().filter(menu -> StringUtils.isNotBlank(menu.getUrl())
                    && antPathMatcher.match(menu.getUrl(), request.getRequestURI())
                    && request.getMethod().equalsIgnoreCase(menu.getMethod()))
                    .findFirst().ifPresent(menuVO -> hasPermission.set(true));
        }
        return hasPermission.get();
    }
}
