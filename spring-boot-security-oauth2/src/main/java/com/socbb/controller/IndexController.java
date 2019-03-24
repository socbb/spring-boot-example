package com.socbb.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * create by socbb on 2019/3/23 22:07.
 */
@RestController
public class IndexController {

    @GetMapping("index")
    public String index(){
        return "hello";
    }

}
