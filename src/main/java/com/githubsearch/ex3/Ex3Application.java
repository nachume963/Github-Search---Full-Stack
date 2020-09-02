package com.githubsearch.ex3;

import com.githubsearch.ex3.beans.Admin;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.context.WebApplicationContext;


@SpringBootApplication
public class Ex3Application {

    public static void main(String[] args) {
        SpringApplication.run(Ex3Application.class, args);
    }

}
