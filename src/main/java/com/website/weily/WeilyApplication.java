package com.website.weily;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.website.weily.dao")
public class WeilyApplication {

    public static void main(String[] args) {
        SpringApplication.run(WeilyApplication.class, args);
    }

}
