package com.martha.bubble.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.martha.bubble.Game;
import com.martha.bubble.actor.Pallet;
import com.martha.bubble.constant.ParamConst;
import com.martha.bubble.constant.SkinConst;
import com.martha.bubble.constant.StringConst;
import com.martha.bubble.controller.AssetController;
import com.martha.bubble.controller.GameController;
import com.martha.bubble.data.UserData;

/**
 * Created by Lion on 1/23/16.
 */
public class GameStat extends Table {

    // region Instance Fields
    private GameController gameController;
    private UserData userData;
    private ProgressBar progressBar;
    private AssetController assetController;
    private int currentScore;
    private int averageScore;
    private Label highestScoreTxtLb;
    private Label averageScoreTxtLb;
    private Label currentScoreTxtLb;
    private Label highestScoreVlLb;
    private Label averageScoreVlLb;
    private Label currentScoreVlLb;
    // endregion

    // region Ctor
    public GameStat(Game game) {
        this.assetController = game.getAssetController();
        this.gameController = game.getGameController();
        this.userData = new UserData();
        this.currentScore = 0;
        this.averageScore = 0;
    }
    // endregion

    // region init
    public void initGameStatItems(){
        createLabels();
        createProgressBar();
        addGameStatItems();
        refreshData();
        refreshPanelColors(gameController.getPallet());
    }
    // endregion

    // region Create
    public void createLabels(){
        this.highestScoreTxtLb = new Label(StringConst.HIGHEST_SCORE_TEXT, assetController.getLabelStyle());
        this.averageScoreTxtLb = new Label(StringConst.AVERAGE_SCORE_TEXT, assetController.getLabelStyle());
        this.currentScoreTxtLb = new Label(StringConst.CURRENT_GAME_SCORE_TEXT, assetController.getLabelStyle());
        this.highestScoreVlLb = new Label(Integer.toString(userData.getHighestScore()), assetController.getLabelStyle());
        this.averageScoreVlLb = new Label(Integer.toString(averageScore), assetController.getLabelStyle());
        this.currentScoreVlLb = new Label(Integer.toString(currentScore), assetController.getLabelStyle());

    }
    public void createProgressBar(){
        Drawable bgDrawable = assetController.getSkin().getDrawable(SkinConst.DRAWABLE_PB);
        Drawable knobDrawable = assetController.getSkin().getDrawable(SkinConst.DRAWABLE_KNOB);
        ProgressBar.ProgressBarStyle style = new ProgressBar.ProgressBarStyle(bgDrawable, knobDrawable);
        this.progressBar = new ProgressBar((float)0, (float)userData.getHighestScore(), 1, false, style);
        this.progressBar.getStyle().knob.setMinHeight(ParamConst.PROGRESS_BAR_HEIGHT);
        this.progressBar.getStyle().background.setMinHeight(ParamConst.PROGRESS_BAR_HEIGHT);
        this.progressBar.setSize(ParamConst.PROGRESS_BAR_WIDTH, ParamConst.PROGRESS_BAR_HEIGHT);
        this.progressBar.setPosition(ParamConst.PROGRESS_BAR_X, ParamConst.PROGRESS_BAR_Y);
        style.knobBefore = knobDrawable;
        style.knob = null;
    }
    public void addGameStatItems(){
        this.highestScoreTxtLb.setFontScale(ParamConst.GAME_STAT_PANEL_LABEL_SCALE);
        this.averageScoreTxtLb.setFontScale(ParamConst.GAME_STAT_PANEL_LABEL_SCALE);
        this.currentScoreTxtLb.setFontScale(ParamConst.GAME_STAT_PANEL_LABEL_SCALE);
        this.highestScoreVlLb.setFontScale(ParamConst.GAME_STAT_PANEL_LABEL_SCALE);
        this.averageScoreVlLb.setFontScale(ParamConst.GAME_STAT_PANEL_LABEL_SCALE);
        this.currentScoreVlLb.setFontScale(ParamConst.GAME_STAT_PANEL_LABEL_SCALE);
        this.add(currentScoreTxtLb).expand();
        this.add(averageScoreTxtLb).expand();
        this.add(highestScoreTxtLb).expand();
        this.row();
        this.add(currentScoreVlLb).expand();
        this.add(averageScoreVlLb).expand();
        this.add(highestScoreVlLb).expand();
        this.row();
        this.add().width(Gdx.graphics.getWidth()).height(this.getHeight() * 0.25f).colspan(3);
        addActor(progressBar);
    }
    // endregion

    // region update
    public int countScore(int destroyedBallsQuantity){
        int oneStepScore = (destroyedBallsQuantity * destroyedBallsQuantity * destroyedBallsQuantity) / 2;
        currentScore += oneStepScore;
        return oneStepScore;
    }
    public void dataUpdate(Color color){
        currentScoreVlLb.setText(Integer.toString(currentScore));
        currentScoreVlLb.setColor(color);
        currentScoreTxtLb.setColor(color);
        if(currentScore > userData.getHighestScore()){
            userData.setHighestScore(currentScore);
            highestScoreVlLb.setText(Integer.toString(currentScore));
            userData.setHighestScore(currentScore);
            userData.writeData();
        }
        updateProgressBar();
    }
    public void updateProgressBar() {
        progressBar.setRange(0, userData.getHighestScore());
        progressBar.setValue(currentScore);
    }
    // endregion

    // region Logic Methods
    public void keepData(){
        if(currentScore == 0){
            return;
        }
        userData.setOverallGamesPlayed(userData.getOverallGamesPlayed() + 1);
        userData.setOverallScore(userData.getOverallScore() + currentScore);
        if(userData.getHighestScore() > 0) {
            averageScore = userData.getOverallScore() / userData.getHighestScore();
        }
        userData.writeData();
    }
    public void refreshData(){
        userData.readData();
        highestScoreVlLb.setText(Integer.toString(userData.getHighestScore()));
        if(userData.getOverallGamesPlayed() > 0){
            averageScoreVlLb.setText(Integer.toString(userData.getOverallScore() / userData.getOverallGamesPlayed()));}
        currentScore = 0;
        currentScoreVlLb.setText(Integer.toString(currentScore));
        progressBar.setValue(0);
        gameController.setPreviousRecord(userData.getHighestScore());
    }
    public void refreshPanelColors(Pallet pallet){
        highestScoreTxtLb.setColor(pallet.getColor1());
        highestScoreVlLb.setColor(pallet.getColor1());
        averageScoreTxtLb.setColor(pallet.getColor2());
        averageScoreVlLb.setColor(pallet.getColor2());
        currentScoreTxtLb.setColor(pallet.getColor3());
        currentScoreVlLb.setColor(pallet.getColor3());
        progressBar.setColor(pallet.getColor1());
    }
    // endregion

    // region Getters and Setters
    public int getCurrentScore() {
        return currentScore;
    }
    public float getCurrentScoreX(){
        return (ParamConst.GAME_STAT_PANEL_WIDTH / 3)/ 2 ;
    }
    public float getCurrentScoreY(){
        return ParamConst.GAME_STAT_PANEL_Y + (ParamConst.GAME_STAT_PANEL_HEIGHT / 2);
    }
    public UserData getUserData() {
        return userData;
    }
    // endregion
}
