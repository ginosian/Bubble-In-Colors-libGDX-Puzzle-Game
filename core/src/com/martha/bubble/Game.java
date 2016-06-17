package com.martha.bubble;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.martha.bubble.controller.AssetController;
import com.martha.bubble.controller.GameController;
import com.martha.bubble.stage.LayerStage;
import com.martha.bubble.tween.ActorAccessor;
import com.martha.bubble.tween.TweenAnimation;

/**
 * Created by Martha on 11/3/2015.
 */
public abstract class Game extends com.badlogic.gdx.Game{

    // region Instance Fields
    AssetController assetController;
    LayerStage stage;
    GameController gameController;
    TweenAnimation tweenAnimation;
    TweenManager tweenManager;
    // endregion

    // region CTOR
    public Game(){
    }
    // endregion

    // region Logic Methods
    public void initialize() {
        initTween();
        stage = new LayerStage();
        assetController = new AssetController();
        gameController = new GameController(this);
        assetController.setGameController(gameController);
    }
    private void initTween() {
        this.tweenManager = new TweenManager();
        Tween.registerAccessor(Actor.class, new ActorAccessor());
        this.tweenAnimation = new TweenAnimation(tweenManager);
    }
    // endregion

    // region Getters and Setters
    public AssetController getAssetController() {
        return assetController;
    }
    public LayerStage getStage() {
        if (stage == null) throw new NullPointerException("The stage object is null. You have to call initialize() method of Main class to create a stage.");
        return stage;
    }
    public GameController getGameController() {
        return gameController;
    }
    public TweenAnimation getTweenAnimation() {
        return tweenAnimation;
    }
    // endregion
}
