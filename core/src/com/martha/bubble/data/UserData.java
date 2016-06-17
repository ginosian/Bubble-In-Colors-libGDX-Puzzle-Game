package com.martha.bubble.data;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.martha.bubble.constant.DataConstants;
import com.martha.bubble.constant.StringConst;

/**
 * Created by Martha on 11/3/2015.
 */
public class UserData{

    // region Instance Fields
    private int highestScore;
    private int overallScore;
    private int overallGamesPlayed;
    private boolean soundDisabled;
    private Preferences gameData;
    private Preferences settings;
    // endregion

    // region Ctor

    public UserData() {
        this.highestScore = 0;
        this.overallScore = 0;
        this.overallGamesPlayed = 0;
        initPref();
        readData();
    }
    // endregion

    // region Data management
    private void initPref(){
        gameData = Gdx.app.getPreferences(DataConstants.DATA_GAME_DATA);
        settings = Gdx.app.getPreferences(DataConstants.DATA_SETTINGS);
    }
    public void readData(){
        highestScore = gameData.getInteger(DataConstants.HIGHEST_SCORE);
        overallScore = gameData.getInteger(DataConstants.OVERALL_SCORE);
        overallGamesPlayed = gameData.getInteger(DataConstants.OVERALL_GAMES_PLAYED);
        soundDisabled = settings.getBoolean(StringConst.DATA_IS_SOUND_ON);
    }
    public void writeData(){
        gameData.putInteger(DataConstants.HIGHEST_SCORE, highestScore);
        gameData.putInteger(DataConstants.OVERALL_SCORE, overallScore);
        gameData.putInteger(DataConstants.OVERALL_GAMES_PLAYED, overallGamesPlayed);
        gameData.flush();
    }
    // endregion
    // region Getters and Setters
    public int getHighestScore() {
        return highestScore;
    }
    public void setHighestScore(int highestScore) {
        this.highestScore = highestScore;
    }
    public int getOverallGamesPlayed() {
        return overallGamesPlayed;
    }
    public void setOverallGamesPlayed(int overallGamesPlayed) {
        this.overallGamesPlayed = overallGamesPlayed;
    }
    public int getOverallScore() {
        return overallScore;
    }
    public void setOverallScore(int overallScore) {
        this.overallScore = overallScore;
    }

    public boolean isSoundDisabled() {
        return soundDisabled;
    }

    public void setSoundDisabled(boolean soundDisabled) {
        this.soundDisabled = soundDisabled;
        settings.putBoolean(StringConst.DATA_IS_SOUND_ON, soundDisabled);
        settings.flush();
    }
    // endregion
}