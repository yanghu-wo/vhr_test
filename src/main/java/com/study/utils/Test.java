package com.study.utils;

import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.Resource;

/**
 * @ClassName: Test
 * @Author: LiuYun
 * @Description:
 * @Data: Create in 10:33 2022/9/17
 */
public class Test {


    public static void main(String[] args) {
//        BCrypt();
            getCode();
    }

    public static void BCrypt(){
        // 加密思考部分
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encode = passwordEncoder.encode("123");
        System.out.println(encode);
//        String enPass = "$2a$10$r2NjNEsVrdGR.zrtRd74ruZS3B/ZgyUrU4D4MWBoQxMG5Q7Ksd5QG";
//        String salt = enPass.substring(0,24);
//        System.out.println(salt);
////        Boolean flag = passwordEncoder.matches("$10$r2NjNEsVrdGR.zrtRd74ruZS3B/ZgyUrU4D4MWBoQxMG5Q7Ksd5QG",encode);
//        String text  = BCrypt.hashpw("123", salt); // 盐值部分，与依赖包的版本相关，vhr 中security的版本为5.4.1，我使用的是2.3.7
//        System.out.println(text);
//        System.out.println(text.equals(enPass));
        Boolean flag = passwordEncoder.matches("123","$2a$10$kCLn8P.eBCboeNvEJAPmuu9dU6WBrmEnLFhbcOShp/8p.cfI3UKfi");
        System.out.println(flag);
    }


    public static void getCode(){
        //获取验证码
        CodeUtil codeUtil = new CodeUtil();
        String generateVerifyCode = codeUtil.getString();
        System.out.println(generateVerifyCode);
    }
}
