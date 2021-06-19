package ro.asis.order.service.java;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication(
        scanBasePackages = {
                "ro.asis.commons",
                "ro.asis.client.client",
                "ro.asis.account.client",
                "ro.asis.provider.client",
                "ro.asis.order.java",
                "ro.asis.order.service.java"
        }
)
public class OrderServiceJavaApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderServiceJavaApplication.class, args);
    }

}
