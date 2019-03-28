package com.socbb.controller;

import com.socbb.consts.SecurityConst;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.util.OAuth2Utils;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

/**
 * create by socbb on 2019/3/23 22:07.
 */
@RestController
@RequestMapping("/auth")
public class TokenController {

    @Autowired
    private ConsumerTokenServices tokenServices;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${server.port}")
    private int post;

    @GetMapping("index")
    public String index() {
        return "hello";
    }

    @PostMapping("/login")
    public Map login(String username, String password) {
        StringJoiner joiner = new StringJoiner("&");
        Map<String, String> parameters = new HashMap<>();
        parameters.put(OAuth2Utils.GRANT_TYPE, "password");
        parameters.put(OAuth2Utils.CLIENT_ID, SecurityConst.CLIENT_ID);
        parameters.put("client_secret", SecurityConst.CLIENT_SECRET);
        parameters.put(OAuth2Utils.SCOPE, SecurityConst.CLIENT_SCOPE);
        parameters.put("username", username);
        parameters.put("password", password);
        parameters.forEach((k, v) -> joiner.add(k + "=" + v));
        return restTemplate.postForObject(String.format("http://127.0.0.1:%d/oauth/token?%s", post, joiner.toString()), null, Map.class);
    }

    @PostMapping("/refresh_token")
    public Map refresh_token(String refresh_token) {
        StringJoiner joiner = new StringJoiner("&");
        Map<String, String> parameters = new HashMap<>();
        parameters.put(OAuth2Utils.GRANT_TYPE, "refresh_token");
        parameters.put(OAuth2Utils.CLIENT_ID, SecurityConst.CLIENT_ID);
        parameters.put("client_secret", SecurityConst.CLIENT_SECRET);
        parameters.put(OAuth2Utils.SCOPE, SecurityConst.CLIENT_SCOPE);
        parameters.put("refresh_token", refresh_token);
        parameters.forEach((k, v) -> joiner.add(k + "=" + v));
        return restTemplate.postForObject(String.format("http://127.0.0.1:%d/oauth/token?%s", post, joiner.toString()), null, Map.class);
    }

    @GetMapping("/logout")
    public String logout(String access_token, @RequestHeader(required = false, value = "Authorization") String token) {
        if (StringUtils.isBlank(access_token)) {
            if (StringUtils.isNoneBlank(token)) {
                access_token = token.substring(OAuth2AccessToken.BEARER_TYPE.length() + 1);
            }
        }
        boolean flag = tokenServices.revokeToken(access_token);
        if (flag) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            return "sucess";
        }
            return "error";
    }
}
