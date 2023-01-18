package com.sahajTest.snakesAndLadderGame;

import com.sahajTest.snakesAndLadderGame.config.SnakesAndLaddersConfig;
import com.sahajTest.snakesAndLadderGame.stat.StatStore;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@SpringBootTest
@EnableConfigurationProperties(value = SnakesAndLaddersConfig.class)
@TestPropertySource("classpath:application-test.yml")
public class SnakeAndLadderGameTest {

    @Autowired
    private SnakeAndLadderGame gameRunner;

    @MockBean
    private StatStore statStore;

    @Test
    public void testRollDice(){
        var diceValue = gameRunner.rollDice();
        assertThat(diceValue).isNotNull();
        assertThat(diceValue).isBetween(1, 6);
    }

    @Test
    public void testMissedSnakeByNStepsHitSc1(){
        var result = gameRunner.missedSnakeByNSteps(26, 1);
        assertThat(result).isTrue();
    }

    @Test
    public void testMissedSnakeByNStepsHitSc2(){
        var result = gameRunner.missedSnakeByNSteps(25, 2);
        assertThat(result).isTrue();
    }
    @Test
    public void testMissedSnakeByNStepsMiss(){
        var result = gameRunner.missedSnakeByNSteps(20, 1);
        assertThat(result).isFalse();
    }

    @Test
    public void testLuckySnakeHitByTwoStep(){
        var result = gameRunner.luckySnakeMiss(25);
        assertThat(result).isTrue();
    }

    @Test
    public void testLuckySnakeHitByOneStep(){
        var result = gameRunner.luckySnakeMiss(26);
        assertThat(result).isTrue();
    }

    @Test
    public void testLuckySnakeMiss(){
        var result = gameRunner.luckySnakeMiss(20);
        assertThat(result).isFalse();
    }

    @Test
    public void testLuckyWinRollHit(){
        var result = gameRunner.luckyWinRoll(100, 1);
        assertThat(result).isTrue();
    }

    @Test
    public void testLuckyWinRollMiss(){
        var result = gameRunner.luckyWinRoll(100, 2);
        assertThat(result).isFalse();
    }

    @Test
    public void testLuckyRollHit1(){
        var result = gameRunner.isLuckyRoll(26, 0);
        assertThat(result).isTrue();
    }

    @Test
    public void testLuckyRollHit2(){
        var result = gameRunner.isLuckyRoll(95, 1);
        assertThat(result).isFalse();
    }

    @Test
    public void testLuckyRollMiss1(){
        var result = gameRunner.isLuckyRoll(20, 0);
        assertThat(result).isFalse();
    }

    @Test
    public void testLuckyRollMiss2(){
        var result = gameRunner.luckyWinRoll(96, 1);
        assertThat(result).isFalse();
    }

    @Test
    public void testCalculatePlayerPositionWin(){
        var result = gameRunner.calculatePlayerPosition(95, 5, 1);
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(100);
    }

    @Test
    public void testCalculatePlayerPositionSnakeEncounter(){
        doNothing().when(statStore).snakeEncounterRoll(anyInt());
        var result = gameRunner.calculatePlayerPosition(25, 2, 0);
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(5);
        verify(statStore).snakeEncounterRoll(22);
    }

    @Test
    public void testCalculatePlayerPositionLadderEncounter(){
        doNothing().when(statStore).ladderEncounterRoll(anyInt());
        var result = gameRunner.calculatePlayerPosition(2, 2, 0);
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(25);
        verify(statStore).ladderEncounterRoll(21);
    }

    @Test
    public void testCalculatePlayerPositionNormalRoll(){
        doNothing().when(statStore).normalRoll(Boolean.FALSE);
        var result = gameRunner.calculatePlayerPosition(5, 2, 0);
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(7);
        verify(statStore, never()).ladderEncounterRoll(anyInt());
        verify(statStore, never()).snakeEncounterRoll(anyInt());
    }

}
