package com.socbb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * create by socbb on 2019/3/30 11:34.
 */
@Controller
public class RenderController {

    @GetMapping("index")
    public String index() {
        return "index";
    }

    @GetMapping("/authentication/require")
    public String login(){
        return "login";
    }

}
