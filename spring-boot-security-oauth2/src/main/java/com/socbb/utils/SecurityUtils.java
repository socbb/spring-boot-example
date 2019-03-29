package com.socbb.utils;

import com.socbb.service.impl.UserDetailsImpl;
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
    public static UserDetailsImpl getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal == null) {
            return null;
        }
        if (principal instanceof UserDetailsImpl) {
            return (UserDetailsImpl) principal;
        }
        return null;
    }

}
