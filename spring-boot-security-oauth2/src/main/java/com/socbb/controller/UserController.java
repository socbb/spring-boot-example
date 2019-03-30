package com.socbb.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * create by socbb on 2019/3/23 22:07.
 */
@RestController
public class UserController {

    @DeleteMapping("/user/delete")
    public String delete() {
        return "user delete";
    }

    @GetMapping("/user/list")
    public String user() {
        return "user list";
    }


}
