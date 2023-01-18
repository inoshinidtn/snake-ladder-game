package com.sahajTest.snakesAndLadderGame.stat.models;

import lombok.Data;

@Data
public class GameRun {

    private int ladders = 0;

    private int snakes = 0;

    private int rolls = 0;

    private int climbs = 0;

    private int slides = 0;

    private int biggestClimb = 0;

    private int biggestSlide = 0;

    private int luckyRolls = 0;

    private int longestRun = 0;

    public void increaseRoll(){
        rolls++;
    }

    public void increaseLuckyRoll(){
        luckyRolls++;
    }

    public void ladderEncounter(int currentClimb){
        ladders++;
        luckyRolls++;
        climbs += currentClimb;
        if(biggestClimb < currentClimb){
            biggestClimb = currentClimb;
        }
    }

    public void snakeEncounter(int currentSlide){
        snakes++;
        slides += currentSlide;
        if(biggestSlide < currentSlide){
            biggestSlide = currentSlide;
        }
    }

    public void consecutiveSixes(int numOfRuns){
        if(longestRun < numOfRuns){
            longestRun = numOfRuns;
        }
    }

}
