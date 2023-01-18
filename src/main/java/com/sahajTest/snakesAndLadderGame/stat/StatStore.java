package com.sahajTest.snakesAndLadderGame.stat;

import com.sahajTest.snakesAndLadderGame.stat.models.GameRun;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

@Component
public class StatStore {
    private static Logger LOGGER = LoggerFactory.getLogger(StatStore.class);

    private GameRun gameRun;

    private List<GameRun> historicData = new LinkedList<>();

    public void startNewRun(){
        this.gameRun = new GameRun();
    }

    public void finishCurrentRun(){
        historicData.add(this.gameRun);
        this.gameRun = null;
    }

    public void printStats(){
        var rollDataStats = historicData.stream().map(GameRun::getRolls).mapToInt(Integer::intValue).summaryStatistics();
        LOGGER.info("Minimum number of rolls needed to win: {}", rollDataStats.getMin());
        LOGGER.info("Maximum number of rolls needed to win: {}", rollDataStats.getMax());
        LOGGER.info("Average number of rolls needed to win: {}", rollDataStats.getAverage());
        var climbDataStats = historicData.stream().map(GameRun::getClimbs).mapToInt(Integer::intValue).summaryStatistics();
        LOGGER.info("Minimum climb: {}", climbDataStats.getMin());
        LOGGER.info("Maximum climb: {}", climbDataStats.getMax());
        LOGGER.info("Average climb: {}", climbDataStats.getAverage());
        var slideDataStats = historicData.stream().map(GameRun::getSlides).mapToInt(Integer::intValue).summaryStatistics();
        LOGGER.info("Minimum slide: {}", slideDataStats.getMin());
        LOGGER.info("Maximum slide: {}", slideDataStats.getMax());
        LOGGER.info("Average slide: {}", slideDataStats.getAverage());
        LOGGER.info("Biggest climb: {}", historicData.stream().max(Comparator.comparing(GameRun::getBiggestClimb)).get().getBiggestClimb());
        LOGGER.info("Biggest slide: {}", historicData.stream().max(Comparator.comparing(GameRun::getBiggestSlide)).get().getBiggestSlide());
        LOGGER.info("Longest run: {}", historicData.stream().max(Comparator.comparing(GameRun::getLongestRun)).get().getLongestRun());
        var unLuckyRollStat = historicData.stream().map(GameRun::getSnakes).mapToInt(Integer::intValue).summaryStatistics();
        LOGGER.info("Minimum unlucky rolls: {}", unLuckyRollStat.getMin());
        LOGGER.info("Maximum unlucky rolls: {}", unLuckyRollStat.getMax());
        LOGGER.info("Average unlucky rolls: {}", unLuckyRollStat.getAverage());
        var luckyRollStat = historicData.stream().map(GameRun::getLuckyRolls).mapToInt(Integer::intValue).summaryStatistics();
        LOGGER.info("Minimum lucky rolls: {}", luckyRollStat.getMin());
        LOGGER.info("Maximum lucky rolls: {}", luckyRollStat.getMax());
        LOGGER.info("Average lucky rolls: {}", luckyRollStat.getAverage());
    }

    public void ladderEncounterRoll(int climb){
        this.gameRun.increaseRoll();
        this.gameRun.ladderEncounter(climb);
    }

    public void snakeEncounterRoll(int slide){
        this.gameRun.increaseRoll();
        this.gameRun.snakeEncounter(slide);
    }

    public void normalRoll(boolean lucky){
        this.gameRun.increaseRoll();
        if(lucky){
            this.gameRun.increaseLuckyRoll();
        }
    }

    public void consecutiveSixes(int noOfRuns){
        this.gameRun.consecutiveSixes(noOfRuns);
    }

}
