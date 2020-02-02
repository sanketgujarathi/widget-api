package com.miro.assignment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
//@EnableHypermediaSupport(type = HypermediaType.HAL_FORMS)
@EnableCaching
public class WidgetApplication {

    public static void main(String[] args) {
        SpringApplication.run(WidgetApplication.class, args);
    }
}
