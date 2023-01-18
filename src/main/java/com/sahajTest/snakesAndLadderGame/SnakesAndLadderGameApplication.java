package com.sahajTest.snakesAndLadderGame;

import com.sahajTest.snakesAndLadderGame.config.SnakesAndLaddersConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(SnakesAndLaddersConfig.class)
public class SnakesAndLadderGameApplication {
	public static void main(String[] args) {
		SpringApplication.run(SnakesAndLadderGameApplication.class, args);
	}
}