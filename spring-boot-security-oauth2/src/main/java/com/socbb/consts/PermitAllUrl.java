package com.socbb.consts;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * 需要放开权限的url
 *
 */
public final class PermitAllUrl {

    /**
     * 监控中心和swagger需要访问的url
     */
    private static final String[] ENDPOINTS = {"/authentication/require","/auth/login", "/auth/refresh_token", "/auth/logout"};

    /**
     * 需要放开权限的url
     *
     * @param urls 自定义的url
     * @return 自定义的url和监控中心需要访问的url集合
     */
    public static String[] permitAllUrl(String... urls) {
        if (urls == null || urls.length == 0) {
            return ENDPOINTS;
        }

        Set<String> set = new HashSet<>();
        Collections.addAll(set, ENDPOINTS);
        Collections.addAll(set, urls);

        return set.toArray(new String[0]);
    }

}
