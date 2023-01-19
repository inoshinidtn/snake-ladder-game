package com.sahajTest.snakesAndLadderGame;

import com.sahajTest.snakesAndLadderGame.config.SnakesAndLaddersConfig;
import com.sahajTest.snakesAndLadderGame.stat.StatStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class SnakeAndLadderGame {

    private static Logger LOGGER = LoggerFactory.getLogger(SnakeAndLadderGameRunner.class);

    final static int START_POINT = 0;
    final static int WIN_POINT = 100;

    private SnakesAndLaddersConfig config;

    private StatStore statStore;

    public SnakeAndLadderGame(SnakesAndLaddersConfig config, StatStore statStore) {
        this.config = config;
        this.statStore = statStore;
    }

    int rollDice() {
        int n = 0;
        Random r = new Random();
        n = r.nextInt(7);
        return (n == 0 ? 1 : n);
    }

    boolean missedSnakeByNSteps(int landPosition, int n){
        return this.config.snakes().get(landPosition + n) != null;
    }

    boolean luckySnakeMiss(int landPosition){
        return missedSnakeByNSteps(landPosition, 1) || missedSnakeByNSteps(landPosition, 2);
    }

    boolean luckyWinRoll(int landPosition, int rollsAfter94){
        return rollsAfter94 == 1 && isWin(landPosition);
    }

    boolean isLuckyRoll(int landPosition, int rollsAfter94){
        return luckyWinRoll(landPosition, rollsAfter94) || luckySnakeMiss(landPosition);
    }

    int calculatePlayerPosition(int playerPosition, int diceValue, int rollsAfter94) {
        int playerNewPosition = playerPosition + diceValue;

        if (playerNewPosition > WIN_POINT){
            LOGGER.debug("Position beyond willing score. No advancement");
            return playerPosition;
        }

        if (null != config.snakes().get(playerNewPosition)) {
            LOGGER.info("Oops.. Swallowed by a snake..");
            playerNewPosition = config.snakes().get(playerNewPosition);
            this.statStore.snakeEncounterRoll(playerPosition + diceValue - playerNewPosition);
        } else if (null != config.ladders().get(playerNewPosition)) {
            LOGGER.info("Wow! Climbing up a ladder..");
            playerNewPosition = config.ladders().get(playerNewPosition);
            this.statStore.ladderEncounterRoll(playerNewPosition - playerPosition - diceValue);
        } else {
            this.statStore.normalRoll(isLuckyRoll(playerNewPosition, rollsAfter94));
        }

        return playerNewPosition;
    }

    boolean isWin(int playerPosition) {
        return WIN_POINT == playerPosition;
    }

    void startGame() {
        int simulationCount = 0;

        do {
            this.statStore.startNewRun();
            int playerPosition = START_POINT;
            int rollingSixes = 0;
            int rollsAfter94 = 0;
            int diceValue;
            LOGGER.info("---------- New game round is starting -------------");
            do {
                diceValue = rollDice();
                LOGGER.info("You rolled: {}", diceValue);
                if(diceValue == 6){
                    LOGGER.debug("Rolled a 6, steak is on");
                    rollingSixes++;
                } else {
                    if(rollingSixes >= 1){
                        LOGGER.debug("Rolling 6 steak broken");
                        this.statStore.consecutiveSixes(rollingSixes);
                        rollingSixes = 0;
                    }
                }
                playerPosition = calculatePlayerPosition(playerPosition, diceValue, rollsAfter94);
                if(playerPosition >= 94){
                    rollsAfter94++;
                }
                LOGGER.info("Your current position: {}", playerPosition);
            } while (!isWin(playerPosition));

            simulationCount++;
            LOGGER.info("Congratulations! You won at round {}", simulationCount);
            this.statStore.finishCurrentRun();
            this.statStore.printStats();
        } while (simulationCount < config.maxSimulationCount());
    }
}