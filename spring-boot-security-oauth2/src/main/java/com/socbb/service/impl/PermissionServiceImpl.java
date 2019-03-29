package com.socbb.service.impl;

import com.socbb.bean.Menu;
import com.socbb.consts.SecurityConst;
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
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * create by socbb on 2019/3/28 14:40.
 */
@Slf4j
@Component("permissionService")
public class PermissionServiceImpl implements PermissionService {

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Autowired
    private MenuDao permissionDao;

    /**
     * 鉴权接口
     *
     * @param request
     * @param authentication
     * @return
     */
    @Override
    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {
        AtomicBoolean hasPermission = new AtomicBoolean(false);
        Object principal = authentication.getPrincipal();
        List<SimpleGrantedAuthority> grantedAuthorityList = (List<SimpleGrantedAuthority>) authentication.getAuthorities();
        if (principal == null) {
            return false;
        }
        if (CollectionUtils.isEmpty(grantedAuthorityList)) {
            log.warn("角色列表为空：{}", authentication.getPrincipal());
            return false;
        }

        // 获取角色code
        List<String> roleCodes = new ArrayList<>();
        for (SimpleGrantedAuthority authority : grantedAuthorityList) {
            if (!SecurityConst.BASE_ROLE.equalsIgnoreCase(authority.getAuthority()) && !SecurityConst.ROLE_ANONYMOUS.equalsIgnoreCase(authority.getAuthority())) {
                roleCodes.add(authority.getAuthority());
            }
        }

        // 获取权限code
        if (!roleCodes.isEmpty()) {
            List<Menu> menus = permissionDao.findMenusByRoleCodes(roleCodes);
            // 权限校验
            if (CollectionUtils.isNotEmpty(menus)) {
                menus.stream().filter(menu -> StringUtils.isNotBlank(menu.getPath())
                        && antPathMatcher.match(menu.getPath(), request.getRequestURI())
                        && request.getMethod().equalsIgnoreCase(menu.getMethod())
                ).findFirst().ifPresent(permission -> hasPermission.set(true));
            }
        }
        return hasPermission.get();
    }
}
