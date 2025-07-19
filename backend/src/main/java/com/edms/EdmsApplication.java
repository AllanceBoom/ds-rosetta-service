package com.edms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * 实验数据管理系统主应用程序
 * 
 * @author EDMS Team
 * @version 1.0.0
 */
@SpringBootApplication
@EnableJpaAuditing
public class EdmsApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(EdmsApplication.class, args);
        System.out.println("========================================");
        System.out.println("实验数据管理系统启动成功！");
        System.out.println("API文档地址: http://localhost:8080/api/swagger-ui/");
        System.out.println("========================================");
    }
}