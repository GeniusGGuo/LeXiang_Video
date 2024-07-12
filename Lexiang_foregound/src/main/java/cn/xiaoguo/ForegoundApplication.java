package cn.xiaoguo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

/**
 * @ClassName ForegoundApplication
 * @Description  启动类
 * @Author 小果
 * @Date 2024/6/7 16:29
 * @Version 1.0
 */

@MapperScan("cn.xiaoguo.mapper")
@SpringBootApplication
public class ForegoundApplication {
    public static void main(String[] args) {
        SpringApplication.run(ForegoundApplication.class);
    }
}
