package com.study;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootApplication
@MapperScan("com.study.vhr.mapper")
public class VhrTestApplication {



    public static void main(String[] args) {
        SpringApplication.run(VhrTestApplication.class, args);
    }

}
