package com.socbb.service;

import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;

/**
 * create by socbb on 2019/3/28 14:51.
 */
public interface PermissionService {

    boolean hasPermission(HttpServletRequest request, Authentication authentication);
}
