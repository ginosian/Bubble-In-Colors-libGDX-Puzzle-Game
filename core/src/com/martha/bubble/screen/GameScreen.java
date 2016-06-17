package com.martha.bubble.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.Timer;
import com.martha.bubble.Game;
import com.martha.bubble.controller.GameController;
import com.martha.bubble.stage.LayerStage;

/**
 * Created by Martha on 11/3/2015.
 */
public class GameScreen implements Screen {

    // region Instance fields
    private Game game;
    private LayerStage stage;
    // endregion

    // region CTOR
    public GameScreen(Game game){
        this.game = game;
        this.stage = this.game.getStage();
        game.getGameController().createObjects();
    }
    // endregion

    // region Overrides
    @Override
    public void show() {
    }
    @Override
    public void render(float delta) {
        //Gdx.gl.glClearColor(0.565f, 0.76f, 0.616f, 1); // 144, 194, 157
        Gdx.gl.glClearColor(0.6f, 0.7f, 0.75f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        this.stage.act();
        this.stage.draw();
    }
    @Override
    public void resize(int width, int height) {
    }
    @Override
    public void pause() {
    }
    @Override
    public void resume() {
        final GameController gameController = game.getGameController();

        if (gameController != null) {
            gameController.disableMenuRateButton();
            Timer.instance().scheduleTask(new Timer.Task() {
                @Override
                public void run() {
                    /* The last click event was not send to process before screen closed. It means if the button touch was disabled before the rate activity opened
                     and enabled in resume() method, to made it touchable after closing the rate activity, the button will process additional touch event which was
                     made before opening the rate activity. It means that doesn't matter if the button is touchable or not after opening the rate activity. We disable
                     the touch of the button on resume and use timer to activate it after event processing tact is over.*/
                    gameController.enableMenuRateButton();
                }
            }, 0.2f);
        }
    }
    @Override
    public void hide() {
        game.getAssetController().dispose(); // Disposes game assets.
        stage.dispose(); // Disposes the stage.
    }
    @Override
    public void dispose() {

    }
    // endregion
}
