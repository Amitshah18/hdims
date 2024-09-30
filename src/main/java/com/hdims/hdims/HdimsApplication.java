package com.hdims.hdims;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
public class HdimsApplication {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext(
            "spring/application-context.xml",
            "spring/dispatcher-config.xml",
            "spring/spring-security.xml"
        );

        // The application is now running and ready to receive requests
    }
}
