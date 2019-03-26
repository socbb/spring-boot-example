package com.socbb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.common.util.OAuth2Utils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * create by socbb on 2019/3/23 22:07.
 */
@RestController
public class TokenController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("index")
    public String index() {
        return "hello";
    }

    @PostMapping("/login")
    public Map<String, Object> smsLogin(String username, String password) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put(OAuth2Utils.GRANT_TYPE, "password");
        parameters.put(OAuth2Utils.CLIENT_ID, "socbb");
        parameters.put("client_secret", "socbb");
        parameters.put(OAuth2Utils.SCOPE, "app");

        parameters.put("username", username);
        parameters.put("password", password);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<String> formEntity = new HttpEntity<String>(null, headers);
        String map = restTemplate.postForObject("http://127.0.0.1:8080/oauth/token", formEntity, String.class, parameters);
        return null;
    }
}
