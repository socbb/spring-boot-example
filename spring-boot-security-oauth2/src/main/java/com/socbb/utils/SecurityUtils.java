package com.socbb.utils;

import com.socbb.bean.LoginAppUser;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * create by socbb on 2019/3/28 14:56.
 */
public class SecurityUtils {

    /**
     * 获取当前用户
     *
     * @return
     */
    public static LoginAppUser getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal == null) {
            return null;
        }
        if (principal instanceof LoginAppUser) {
            return (LoginAppUser) principal;
        }
        return null;
    }

}
