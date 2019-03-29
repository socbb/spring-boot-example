package com.socbb.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * create by socbb on 2019/3/23 22:07.
 */
@RestController
@RequestMapping("/user")
public class UserController {

//    @PreAuthorize("hasAuthority('sys:user:list')")
    @GetMapping("/list")
    public String user() {
        return "user list";
    }

//    @PreAuthorize("hasAuthority('sys:user:delete')")
    @DeleteMapping("/delete")
    public String delete() {
        return "user delete";
    }


}
