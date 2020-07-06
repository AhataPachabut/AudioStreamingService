package com.epam.conversionservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jms.annotation.EnableJms;

//1. EnableJms + listener
//2. exporter + listener + interface
@SpringBootApplication
@EnableJms
@EnableDiscoveryClient
@ComponentScan(basePackages = {"com.epam.storageservice"})
public class ConversionServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConversionServiceApplication.class, args);
	}

}
