package org.example.personalsystem;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"org.example.personalsystem", "com.personal.system"})
@MapperScan("com.personal.system.mapper")
public class PersonalSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(PersonalSystemApplication.class, args);
    }

}
