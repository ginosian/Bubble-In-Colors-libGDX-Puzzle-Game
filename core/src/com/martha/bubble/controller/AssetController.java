package com.martha.bubble.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.martha.bubble.constant.SkinConst;
import com.martha.bubble.constant.StringConst;
import com.martha.bubble.enums.SoundType;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Created by Martha on 11/3/2015.
 */
public class AssetController{
    // region Instance Fields
    private GameController gameController;
    private Skin skin;
    private Label.LabelStyle labelStyle;
    private HashMap<SoundType, Sound> soundMap;
    // endregion

    // region Ctor
    public AssetController() {
        this.soundMap = new LinkedHashMap<SoundType, Sound>();
        this.skin = new Skin(Gdx.files.internal(SkinConst.SKIN_PATH));

        this.labelStyle = skin.get(Label.LabelStyle.class);
        this.labelStyle.font.getData().ascent = this.labelStyle.font.getData().ascent * -0.65f; // These 2 lines are to correct font size inaccuracy.
        this.labelStyle.font.getData().descent = 0;

        initSoundMap();
    }
    // endRegion

    // Init methods
    private void initSoundMap() {
        soundMap.put(SoundType.POKE, Gdx.audio.newSound(Gdx.files.internal(StringConst.SOUND_POKE)));
        soundMap.put(SoundType.BUTTON, Gdx.audio.newSound(Gdx.files.internal(StringConst.SOUND_BUTTON)));
    }
    // endregion

    // region Logic methods
    public void playSound(SoundType type) {
        if (!gameController.getGameStatPanel().getUserData().isSoundDisabled()) soundMap.get(type).play();
    }
    public void dispose() {
        skin.dispose();
        for (Sound sound : soundMap.values()) {
            sound.dispose();
        }
    }
    // endregion

    // region Getters and Setters
    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }

    public Skin getSkin() {
        return skin;
    }

    public Label.LabelStyle getLabelStyle() {
        return labelStyle;
    }
    // endregion
}
