package com.sahajTest.snakesAndLadderGame;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("!test")
@Component
public class SnakeAndLadderGameRunner implements ApplicationRunner {

    private SnakeAndLadderGame game;


    public SnakeAndLadderGameRunner(SnakeAndLadderGame game) {
        this.game = game;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        this.game.startGame();
    }
}