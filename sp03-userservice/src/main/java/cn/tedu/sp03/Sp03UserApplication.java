package cn.tedu.sp03;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class Sp03UserApplication {

    public static void main(String[] args) {
        SpringApplication.run(Sp03UserApplication.class, args);
    }

}
