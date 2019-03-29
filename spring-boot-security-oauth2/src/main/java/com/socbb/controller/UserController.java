package com.socbb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * create by socbb on 2019/3/23 22:07.
 */
@Controller
public class UserController {

    @GetMapping("index")
    public String index() {
        return "index";
    }

    @ResponseBody
    @DeleteMapping("/user/delete")
    public String delete() {
        return "user delete";
    }

    @ResponseBody
    @GetMapping("/user/list")
    public String user() {
        return "user list";
    }


}
