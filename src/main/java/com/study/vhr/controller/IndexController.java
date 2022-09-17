package com.study.vhr.controller;

import com.study.config.accnotation.UnInterception;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @ClassName: IndexController
 * @Author: LiuYun
 * @Description:
 * @Data: Create in 11:21 2022/9/3
 */
@Controller
public class IndexController {

    @GetMapping("/index")
    public String login(){
        return "login";
    }

    @GetMapping("/unauthorized")
    public String unauthorized() {
        return "unauthorized";
    }
}
