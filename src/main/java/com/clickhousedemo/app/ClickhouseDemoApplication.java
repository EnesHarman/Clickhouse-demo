package com.clickhousedemo.app;

import com.clickhousedemo.app.clickhouse.ClickhouseConnectorImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ClickhouseDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClickhouseDemoApplication.class, args);
    }
}
