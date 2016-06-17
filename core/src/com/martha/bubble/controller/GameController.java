package com.martha.bubble.controller;

import aurelienribon.tweenengine.Tween;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.martha.bubble.Bubble;
import com.martha.bubble.Game;
import com.martha.bubble.actor.Pallet;
import com.martha.bubble.constant.ParamConst;
import com.martha.bubble.constant.StringConst;
import com.martha.bubble.constant.TweenConst;
import com.martha.bubble.enums.GameState;
import com.martha.bubble.enums.LayerType;
import com.martha.bubble.input.InputHandler;
import com.martha.bubble.listener.KeyAction;
import com.martha.bubble.stage.LayerStage;
import com.martha.bubble.tween.TweenAnimation;
import com.martha.bubble.ui.ButtonPanel;
import com.martha.bubble.ui.GameStat;
import com.martha.bubble.ui.MenuDialog;

/**
 * Created by Martha on 11/3/2015.
 */
public class GameController{
    // region Instance Fields
    private Board board;
    private LayerStage stage;
    private Game game;
    private GameStat gameStatPanel;
    private ButtonPanel buttonPanel;
    private AssetController assetController;
    private Label gameOverLabel;
    private int previousRecord;
    private TweenAnimation tweenAnimation;
    private ColorController colorController;
    private Pallet pallet;
    private MenuDialog menuDialog;
    private float boardY;
    private float adScreenHeight;
    private float buttonPanelY;
    private float buttonPanelHeight;
    private float gameStatPanelY;
    private float gameStatPanelHeight;
    private float overallGamePanelHeight;
    private GameState gameState;
    private InputHandler inputHandler;
    // endregion

    // region Ctor
    public GameController(Game game) {
        this.game = game;
        this.stage = game.getStage();
        this.assetController = game.getAssetController();
        this.tweenAnimation = game.getTweenAnimation();
        this.colorController = new ColorController();
        this.pallet = new Pallet();
        this.gameState = GameState.MENU;

        setupInputMultiplexer();
    }
    // endregion

    // region Initializing methods
    public void initialize() {
        this.adScreenHeight = getDeviceAdY();
        initPallet();
        this.overallGamePanelHeight = Gdx.graphics.getHeight() - adScreenHeight - ParamConst.BOARD_HEIGHT;
        this.buttonPanelY = adScreenHeight;
        this.buttonPanelHeight = overallGamePanelHeight * 0.4f;
        this.boardY = adScreenHeight + buttonPanelHeight;
        this.board = new Board(game, boardY);
        this.gameStatPanelY = this.boardY + ParamConst.BOARD_HEIGHT;
        this.gameStatPanelHeight = Gdx.graphics.getHeight() - gameStatPanelY;
    }
    public void createObjects(){
        // Game stat panel initialization.
        this.gameStatPanel = new GameStat(this.game);
        this.gameStatPanel.setSize(Gdx.graphics.getWidth(), this.gameStatPanelHeight);
        this.gameStatPanel.setPosition(0, this.gameStatPanelY);
        this.gameStatPanel.initGameStatItems();
        this.stage.addActor(gameStatPanel, LayerType.L1);

        // Button panel initialization.
        this.buttonPanel = new ButtonPanel(this.game);
        this.buttonPanel.setSize(Gdx.graphics.getWidth(), this.buttonPanelHeight);
        this.buttonPanel.setPosition(0, this.buttonPanelY);
        this.buttonPanel.addButtons(pallet);
        this.buttonPanel.initButtonListeners();
        this.stage.addActor(buttonPanel, LayerType.L1);

        // Board initialization.
        this.board.setScorePositionX(this.gameStatPanel.getCurrentScoreX());
        this.board.setScorePositionY(this.gameStatPanel.getCurrentScoreY());
        this.board.initBoardItems();

        // Game over label initialization.
        this.gameOverLabel = new Label(StringConst.GAME_WIN_TEXT, assetController.getLabelStyle());
        this.gameOverLabel.setFontScale(ParamConst.GAME_OVER_LABEL_SCALE);
        this.gameOverLabel.setWrap(true);
        this.gameOverLabel.setAlignment(Align.center);
        this.gameOverLabel.setWidth(ParamConst.GAME_OVER_LABEL_WIDTH);

        // Menu dialog initialization.
        this.menuDialog = new MenuDialog(this.game);
        this.menuDialog.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        this.menuDialog.setPosition(0, 0);
        this.menuDialog.initItems();
        addMenuDialog();
    }
    // endregion

    // region Methods - Game Control
    private void setupInputMultiplexer() {
        Gdx.input.setCatchBackKey(true);
        inputHandler = new InputHandler();
        InputProcessor multitouchPreventingProccessor = new InputProcessor() {
            @Override
            public boolean keyDown(int keycode) {
                return false;
            }
            @Override
            public boolean keyUp(int keycode) {
                return false;
            }
            @Override
            public boolean keyTyped(char character) {
                return false;
            }
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                return pointer > 0;
            }
            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                return pointer > 0;
            }
            @Override
            public boolean touchDragged(int screenX, int screenY, int pointer) {
                return pointer > 0;
            }
            @Override
            public boolean mouseMoved(int screenX, int screenY) {
                return false;
            }
            @Override
            public boolean scrolled(int amount) {
                return false;
            }
        };
        InputMultiplexer inputMultiplexer = new InputMultiplexer(multitouchPreventingProccessor, stage, inputHandler);
        Gdx.input.setInputProcessor(inputMultiplexer);
        inputHandler.setKeyAction(initMenuBackKeyAction());
    }

    public void showMenuDialog() {
        if (this.gameState == GameState.RESTARTING || this.gameState == GameState.MENU) {
            return;
        }
        inputHandler.setKeyAction(null);  // back key action sets null, and will be initialize on complete menuDialog.show() animations by calling initMenuBackKeyActions
        buttonPanel.disableButtons();
        this.gameState = GameState.MENU;
        menuDialog.show();
    }

    public KeyAction initMenuBackKeyAction(){
        return new KeyAction() {
            @Override
            public void action() {
                Gdx.app.exit();
            }
        };
    }
    public KeyAction initGameBackKeyAction(){
        return new KeyAction() {
            @Override
            public void action() {
                showMenuDialog();
            }
        };
    }

    public Pallet initPallet(){
        this.pallet = this.colorController.randomPallet();
        return this.pallet;
    }
    // endregion

    // region Game Over
    public void gameOver(){
        gameState = GameState.RESTARTING;
        int currentScore = gameStatPanel.getCurrentScore();
        if(currentScore == 0){
            gameOverLabel.setText(StringConst.NEW_GAME_TEXT);
        }else if (previousRecord > currentScore) {
            gameOverLabel.setText(Integer.toString(previousRecord - currentScore) + StringConst.GAME_LOOSE_TEXT);
        } else if (previousRecord < currentScore){
            gameOverLabel.setText(StringConst.GAME_WIN_TEXT);
        } else {
            gameOverLabel.setText(StringConst.GAME_EVEN_TEXT);
        }
        addGameOverMessage(gameOverLabel);
        this.gameOverLabel.setPosition(Gdx.graphics.getWidth() / 2, this.boardY + ParamConst.BOARD_HEIGHT / 2, Align.center);
        tweenAnimation.flush(gameOverLabel, 0.5f, Tween.INFINITY, 0, TweenConst.GAME_OVER_TO_NEW_GAME_DURATION, null);
        gameStatPanel.keepData();
        board.gameOver();
    }
    public void addGameOverMessage(Actor actor){
        actor.setColor(pallet.getColor1());
        this.stage.addActor(actor, LayerType.L3);
    }
    public void resetGameOverMessage(Label label){
        label.remove();
        label.getColor().a = 1;
    }
    public void resetColors(){
        gameStatPanel.refreshPanelColors(pallet);
        buttonPanel.refreshButtonsColor(pallet);
        menuDialog.refreshColors();
    }
    // endregion

    // region Logic methods
    public void enableMenuRateButton() {
        menuDialog.enableRateButton();
    }
    public void disableMenuRateButton() {
        menuDialog.disableRateButton();
    }
    public void addMenuDialog() {
        this.stage.addActor(this.menuDialog, LayerType.L4);
    }
    public void removeMenuDialog() {
        this.menuDialog.remove();
    }
    // endregion

    // region Getters and Setters
    public Board getBoard() {
        return board;
    }
    public GameStat getGameStatPanel() {
        return gameStatPanel;
    }
    public ButtonPanel getButtonPanel() {
        return buttonPanel;
    }
    public void setPreviousRecord(int previousRecord) {
        this.previousRecord = previousRecord;
    }
    public ColorController getColorController() {
        return colorController;
    }
    public Pallet getPallet() {
        return pallet;
    }
    public TweenAnimation getTweenAnimation() {
        return tweenAnimation;
    }
    public float getDeviceAdY(){
        Bubble bubbleincolors = (Bubble) game;
        return bubbleincolors.adController.getAdHeight();
    }
    public GameState getGameState() {
        return gameState;
    }
    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }
    public float getAdScreenHeight() {
        return adScreenHeight;
    }
    public Label getGameOverLabel() {
        return gameOverLabel;
    }
    public InputHandler getInputHandler() {
        return inputHandler;
    }
    public AssetController getAssetController() {
        return game.getAssetController();
    }
    public Bubble getBubbleInColors() {
        return (Bubble) game;
    }
    // endregion
}
