package com.martha.bubble.ui;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.martha.bubble.Game;
import com.martha.bubble.actor.Pallet;
import com.martha.bubble.constant.ParamConst;
import com.martha.bubble.constant.SkinConst;
import com.martha.bubble.constant.StringConst;
import com.martha.bubble.constant.TweenConst;
import com.martha.bubble.controller.AssetController;
import com.martha.bubble.controller.GameController;
import com.martha.bubble.enums.BallType;
import com.martha.bubble.enums.GameState;
import com.martha.bubble.enums.SoundType;
import com.martha.bubble.tween.TweenAnimation;
import com.martha.bubble.tween.TweenListener;
import com.martha.bubble.util.Util;

import java.util.ArrayList;

/**
 * Created by Lion on 1/23/16.
 */
public class MenuDialog extends Group {
    
    // region Instance Fields
    private AssetController assetController;
    private GameController gameController;
    private Button playButton;
    private Button rateButton;
    private TweenAnimation tweenAnimation;
    private ArrayList<Image> balls;
    private Table ballsTable;

    private Image outerBg;
    private Image middleBg;
    private Image innerBg;
    // endregion

    // region Ctor
    public MenuDialog(Game game) {
        setTransform(false);
        this.assetController = game.getAssetController();
        this.gameController = game.getGameController();
        this.tweenAnimation = gameController.getTweenAnimation();
        this.balls = new ArrayList<Image>();
    }
    // endregion

    // region Instance Fields
    public void initItems() {
        // Content table init.
        Table contentTable = new Table();
        contentTable.setSize(this.getWidth() - 4 * ParamConst.MENU_DIALOG_BG_LINE_WIDTH,
                this.getHeight() - gameController.getAdScreenHeight() - 4 * ParamConst.MENU_DIALOG_BG_LINE_WIDTH);
        contentTable.setPosition(this.getX() + 2 * ParamConst.MENU_DIALOG_BG_LINE_WIDTH,
                this.getY() + gameController.getAdScreenHeight() + 2 * ParamConst.MENU_DIALOG_BG_LINE_WIDTH);

        // Button init.
        this.playButton = new Button(assetController.getSkin(), SkinConst.STYLE_BUTTON);
        this.rateButton = new Button(assetController.getSkin(), SkinConst.STYLE_BUTTON);

        // Button label init.
        Label playLabel = new Label(StringConst.MENU_PLAY_TEXT, assetController.getLabelStyle());
        playLabel.setFontScale(ParamConst.MENU_LABEL_SCALE);
        Label rateLabel = new Label(StringConst.MENU_RATE_TEXT, assetController.getLabelStyle());
        rateLabel.setFontScale(ParamConst.MENU_LABEL_SCALE);

        // Adding labels to buttons.
        this.playButton.add(playLabel).padBottom(4 * ParamConst.SH);
        playLabel.setWrap(true);
        playLabel.setAlignment(Align.center);
        playLabel.setSize(playButton.getWidth(), playButton.getHeight());
        this.rateButton.add(rateLabel).height(rateButton.getHeight() * 0.5f).padBottom(2 * ParamConst.SH);
        rateLabel.setWrap(true);
        rateLabel.setAlignment(Align.center);
        rateLabel.setSize(playButton.getWidth(), playButton.getHeight());

        // Adding buttons to content table.
        contentTable.add(playButton).width(contentTable.getWidth() * 0.6f).height(contentTable.getHeight() * 0.15f)
                .expand().bottom().padBottom(ParamConst.SH * 6);
        contentTable.row();
        contentTable.add(rateButton).width(contentTable.getWidth() * 0.6f).height(contentTable.getHeight() * 0.15f)
                .expand().top().padTop(ParamConst.SH * 6);
        contentTable.row();

        // Frame background init.
        this.outerBg = new Image(assetController.getSkin().getDrawable(SkinConst.DRAWABLE_FLAT_FRAME));
        this.middleBg = new Image(assetController.getSkin().getDrawable(SkinConst.DRAWABLE_FLAT_FRAME));
        this.innerBg = new Image(assetController.getSkin().getDrawable(SkinConst.DRAWABLE_FLAT_FRAME));
        refreshBgSizes();
        this.addActor(outerBg);
        this.addActor(middleBg);
        this.addActor(innerBg);

        // Ball table init and adding.
        this.ballsTable = new Table();
        contentTable.add(ballsTable).width(this.innerBg.getWidth()).height(this.innerBg.getHeight() * 0.4f);

        // Instantiating ball images, adding in arrayList and add into balls table.
        for (int i = 0; i < ParamConst.COLUMNS_QUANTITY; i++) {
            balls.add(new Image(assetController.getSkin().getDrawable(SkinConst.DRAWABLE_BALL)));
            ballsTable.add(balls.get(i)).width(ParamConst.BALL_WIDTH).height(ParamConst.BALL_HEIGHT).bottom().expand();
        }

        // Adding content table
        this.addActor(contentTable);

        initButtonListeners();
        refreshColors();
        addAnimationToBalls();
    }
    public void initButtonListeners(){
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameController.getInputHandler().setKeyAction(null);
                gameController.getAssetController().playSound(SoundType.BUTTON);
                gameController.getBoard().addAllActorsToStage();
                gameController.setGameState(GameState.GAME);
                hide();

            }
        });

        rateButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameController.getAssetController().playSound(SoundType.BUTTON);
                gameController.getBubbleInColors().iRate.showRateDialog();
            }
        });
    }
    // endregion

    // region Logic Methods
    public void disableButtons() {
        playButton.setTouchable(Touchable.disabled);
        rateButton.setTouchable(Touchable.disabled);
    }
    public void enableButtons() {
        playButton.setTouchable(Touchable.enabled);
        rateButton.setTouchable(Touchable.enabled);
    }
    public void addAnimationToBalls(){
        for (final Image ball : balls) {
            tweenAnimation.jumpContinously(ball, getHeight() / 10,
                    getHeight() / 13, Util.random.nextInt(4) + 2, null);

        }
    }
    public void killBallsAnimation(){
        for (final Image ball : balls) {
            gameController.getTweenAnimation().getTweenManager().killTarget(ball);
            ball.setY(ballsTable.getY());
        }
    }
    private void refreshBgSizes() {
        float lineWidth = ParamConst.MENU_DIALOG_BG_LINE_WIDTH;
        this.outerBg.setSize(this.getWidth(), this.getHeight());
        this.outerBg.setPosition(this.getX(), this.getY());

        this.middleBg.setSize(outerBg.getWidth() - 2 * lineWidth,
                outerBg.getHeight() - gameController.getAdScreenHeight() - 2 * lineWidth);
        this.middleBg.setPosition(outerBg.getX() + lineWidth,
                outerBg.getY() + gameController.getAdScreenHeight() + lineWidth);

        this.innerBg.setSize(middleBg.getWidth() - 2 * lineWidth,
                middleBg.getHeight() - 2 * lineWidth);
        this.innerBg.setPosition(middleBg.getX() + lineWidth,
                middleBg.getY() + lineWidth);
    }
    public void show(){
        gameController.addMenuDialog();
        refreshColors();
        addAnimationToBalls();
        gameController.getTweenAnimation().smoothAppear(this, TweenConst.MENU_DIALOG_DURATION, new TweenListener() {
            @Override
            public void onComplete() {
                gameController.getBoard().removeAllActorsFromStage();
                setTouchable(Touchable.enabled);
                gameController.getInputHandler().setKeyAction(gameController.initMenuBackKeyAction());
                enableButtons();
            }
        });

    }
    public void hide(){
        this.setTouchable(Touchable.disabled);
        disableButtons();
        gameController.getTweenAnimation().smoothDisappear(this, TweenConst.MENU_DIALOG_DURATION, new TweenListener() {
            @Override
            public void onComplete() {
                killBallsAnimation();
                gameController.removeMenuDialog();
                gameController.getInputHandler().setKeyAction(gameController.initGameBackKeyAction());
                gameController.getButtonPanel().enableButtons();
            }
        });

    }
    public void refreshColors(){
        Pallet pallet = gameController.getPallet();
        this.outerBg.setColor(pallet.getColor2());
        this.middleBg.setColor(pallet.getColor3());
        this.innerBg.setColor(0.6f, 0.7f, 0.75f, 1);
        this.playButton.setColor(pallet.getColor2());
        this.rateButton.setColor(pallet.getColor3());
        int colorIndx = 1;
        for (Image ball : balls) {
            if (colorIndx > ParamConst.COLOR_QUANTITY) {
                colorIndx = 1;
            }
            ball.setColor(pallet.acquireColor(BallType.valueOf(StringConst.PREFIX_TYPE + colorIndx)));
            colorIndx++;
        }
    }
    public void enableRateButton() {
        rateButton.setTouchable(Touchable.enabled);
    }
    public void disableRateButton() {
        rateButton.setTouchable(Touchable.disabled);
    }
    // endregion

    // region Override
    @Override
    public void setSize(float width, float height) {
        super.setSize(width, height);
    }

    // endregion
}
