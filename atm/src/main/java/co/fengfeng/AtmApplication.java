package co.fengfeng;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;


@SpringBootApplication
@Transactional
@EnableTransactionManagement    //开启事务支持
@MapperScan("co.fengfeng.mapper")
public class AtmApplication {
    public static void main(String[] args) {
        SpringApplication.run(AtmApplication.class);
    }
}
