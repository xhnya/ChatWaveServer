package com.xhn.chat.chatwaveserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@ComponentScan(basePackages = {"com.xhn"})
@SpringBootApplication
public class ChatWaveServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChatWaveServerApplication.class, args);
	}

}
