package com.quartjob.core;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@EntityScan("com.quartjob.core")
@MapperScan( "com.quartjob.core.dao")
public class App 
{
    public static void main( String[] args )
    {
        SpringApplication.run(App.class, args);

    }
}
