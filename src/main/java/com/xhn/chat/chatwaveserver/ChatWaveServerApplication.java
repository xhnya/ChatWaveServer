package com.xhn.chat.chatwaveserver;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.CrossOrigin;


@MapperScan("com.xhn.chat.chatwaveserver.*.mapper")
@ComponentScan(basePackages = {"com.xhn"})
@SpringBootApplication

public class ChatWaveServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChatWaveServerApplication.class, args);
	}

}
