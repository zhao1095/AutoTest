package com.course;

import javafx.application.Application;
import org.springframework.boot.ConfigurableBootstrapContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PreDestroy;


@EnableScheduling
@SpringBootApplication
public class Applocation {
    private  static ConfigurableBootstrapContext context;

    public static void main(String[] args) {
        Application.context = SpringApplication.run(Applocation.class);
    }

    @PreDestroy
    public void close(){
        Application.context.close();
    }
}
