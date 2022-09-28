package com.study.vhr.controller;

import com.study.utils.CodeUtil;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName: CodeController
 * @Author: LiuYun
 * @Description: 验证码
 * @Data: Create in 19:43 2022/9/28
 */
@Controller
public class CodeController {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @GetMapping("/getCode")
    public void getValidateCode(HttpServletResponse response, HttpServletRequest request) throws IOException {

        //创建输出流
        OutputStream outputStream = response.getOutputStream();
        //获取session
        HttpSession session = request.getSession();
        //获取验证码
        CodeUtil codeUtil = new CodeUtil();
        String generateVerifyCode = codeUtil.getString();
        //将验证码存入session，做登录验证
        stringRedisTemplate.opsForValue().set("code",generateVerifyCode,120, TimeUnit.SECONDS);
//        session.setAttribute("code",generateVerifyCode);

        //获取验证码图片
        BufferedImage image = codeUtil.getImage();
        ImageIO.write(image,"jpg",outputStream);
        // 关闭流
        outputStream.flush();
        outputStream.close();
    }
}
