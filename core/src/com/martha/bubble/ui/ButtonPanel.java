package com.martha.bubble.ui;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.martha.bubble.Game;
import com.martha.bubble.actor.Pallet;
import com.martha.bubble.constant.ParamConst;
import com.martha.bubble.constant.SkinConst;
import com.martha.bubble.constant.StringConst;
import com.martha.bubble.controller.AssetController;
import com.martha.bubble.controller.GameController;
import com.martha.bubble.enums.GameState;
import com.martha.bubble.enums.SoundType;

/**
 * Created by Lion on 1/23/16.
 */
public class ButtonPanel extends Table {
    private AssetController assetController;
    private GameController gameController;
    private Button restartButton;
    private Button menuButton;
    private Button soundButton;

    private Label soundLabel;


    public ButtonPanel(Game game) {
        setTransform(false);
        this.assetController = game.getAssetController();
        this.gameController = game.getGameController();
        initButtons();
    }

    public void initButtons(){
        this.restartButton = new Button(assetController.getSkin(), SkinConst.STYLE_BUTTON);
        this.menuButton = new Button(assetController.getSkin(), SkinConst.STYLE_BUTTON);
        this.soundButton = new Button(assetController.getSkin(), SkinConst.STYLE_SOUND_BUTTON);


        Label restartLabel = new Label(StringConst.BP_RESTART_T, assetController.getLabelStyle());
        restartLabel.setFontScale(ParamConst.BUTTON_PANEL_LABEL_SCALE);
        Label menuLabel = new Label(StringConst.BP_MENU_T, assetController.getLabelStyle());
        menuLabel.setFontScale(ParamConst.BUTTON_PANEL_LABEL_SCALE);
        this.soundLabel = new Label(StringConst.BP_SOUND_ON_T, assetController.getLabelStyle());
        this.soundLabel.setFontScale(ParamConst.BUTTON_PANEL_LABEL_SCALE);

        this.restartButton.add(restartLabel).padBottom(1.5f * ParamConst.SH);
        this.menuButton.add(menuLabel).padBottom(1.5f * ParamConst.SH);
        this.soundButton.add(soundLabel).padBottom(1.5f * ParamConst.SH);


        this.soundButton.setChecked(gameController.getGameStatPanel().getUserData().isSoundDisabled());
        refreshSoundLabel();

        disableButtons();
    }

    public void addButtons(Pallet pallet){
        this.add(restartButton).width(ParamConst.BUTTON_PANEL_BUTTON_WIDTH).height(ParamConst.BUTTON_PANEL_BUTTON_HEIGHT).expand();
        this.add(menuButton).width(ParamConst.BUTTON_PANEL_BUTTON_WIDTH).height(ParamConst.BUTTON_PANEL_BUTTON_HEIGHT).expand();
        this.add(soundButton).width(ParamConst.BUTTON_PANEL_BUTTON_WIDTH).height(ParamConst.BUTTON_PANEL_BUTTON_HEIGHT).expand();
        refreshButtonsColor(pallet);
    }

    public void disableButtons() {
        restartButton.setTouchable(Touchable.disabled);
        menuButton.setTouchable(Touchable.disabled);
        soundButton.setTouchable(Touchable.disabled);
    }
    public void enableButtons() {
        restartButton.setTouchable(Touchable.enabled);
        menuButton.setTouchable(Touchable.enabled);
        soundButton.setTouchable(Touchable.enabled);
    }

    public void initButtonListeners(){
        restartButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameController.getAssetController().playSound(SoundType.BUTTON);
                restartButtonPressed();

            }
        });
        menuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameController.getAssetController().playSound(SoundType.BUTTON);
                menuButtonPressed();
            }
        });
        soundButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(soundButton.isChecked()){
                    soundButtonPressed();
                } else {
                    soundButtonUnpressed();
                }
            }
        });
    }

    public void restartButtonPressed(){
        if(gameController.getGameState() == GameState.RESTARTING || gameController.getGameState() == GameState.MENU){
            return;
        }
        gameController.setGameState(GameState.RESTARTING);
        gameController.gameOver();
    }
    public void menuButtonPressed(){
        gameController.showMenuDialog();
    }
    public void soundButtonPressed(){
        gameController.getGameStatPanel().getUserData().setSoundDisabled(true);
        refreshSoundLabel();
    }
    public void soundButtonUnpressed(){
        gameController.getGameStatPanel().getUserData().setSoundDisabled(false);
        gameController.getAssetController().playSound(SoundType.BUTTON);
        refreshSoundLabel();
    }

    private void refreshSoundLabel() {
        if (gameController.getGameStatPanel().getUserData().isSoundDisabled()) soundLabel.setText(StringConst.BP_SOUND_OFF_T);
        else soundLabel.setText(StringConst.BP_SOUND_ON_T);

    }

    public void refreshButtonsColor(Pallet pallet){
        this.restartButton.setColor(pallet.getColor2());
        this.menuButton.setColor(pallet.getColor3());
        this.soundButton.setColor(pallet.getColor4());
    }
}
