package com.sahajTest.snakesAndLadderGame.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import java.util.Map;

@ConfigurationProperties(prefix = "snakes-and-ladders-conf")
public record SnakesAndLaddersConfig (Map<Integer, Integer> snakes,
                                      Map<Integer, Integer> ladders,
                                      Integer maxSimulationCount) {

}