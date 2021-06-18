package ro.asis.order.service.java;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class OrderServiceJavaApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderServiceJavaApplication.class, args);
    }

}
