package cn.lliiooll.autotask;

import cn.lliiooll.autotask.utils.Utils;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AutoTaskApplication {

    public static void main(String[] args) {
        Utils.init();
        SpringApplication.run(AutoTaskApplication.class, args);
    }

}
