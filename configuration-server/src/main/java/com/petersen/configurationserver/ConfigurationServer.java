package com.petersen.configurationserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.context.annotation.PropertySource;


@EnableConfigServer
@SpringBootApplication
public class ConfigurationServer {

	public static void main(String[] args) {
		SpringApplication.run(ConfigurationServer.class, args);
	}

}
