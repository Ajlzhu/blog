package com.li.blog.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author licheng
 * @description
 * @create 2018/7/24 17:43
 */
@RestController
public class HelloGradle {

    @RequestMapping("/hello")
    public String index(){
        return "hello gradle";
    }
}
