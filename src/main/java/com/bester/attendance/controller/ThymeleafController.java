package com.bester.attendance.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

/**
 * @author zhangqiang
 * @date 2019-04-09
 */

@Controller
public class ThymeleafController {

    @GetMapping("/success")
    public String test(Map<String, Object> map) {
        map.put("hello", "hello");
        return "success";
    }

}
