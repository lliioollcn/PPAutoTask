package cn.lliiooll.autotask;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("cn.lliiooll.autotask.data.mapper")
@SpringBootApplication
public class AutoTaskApplication {

    public static void main(String[] args) {
        SpringApplication.run(AutoTaskApplication.class, args);
    }

}
