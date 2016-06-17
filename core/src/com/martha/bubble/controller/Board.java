package com.martha.bubble.controller;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Timer;
import com.martha.bubble.Game;
import com.martha.bubble.actor.Ball;
import com.martha.bubble.actor.Cell;
import com.martha.bubble.actor.Pallet;
import com.martha.bubble.actor.TouchLocker;
import com.martha.bubble.constant.ParamConst;
import com.martha.bubble.constant.SkinConst;
import com.martha.bubble.constant.TweenConst;
import com.martha.bubble.enums.BallType;
import com.martha.bubble.enums.GameState;
import com.martha.bubble.enums.LayerType;
import com.martha.bubble.enums.SoundType;
import com.martha.bubble.stage.LayerStage;
import com.martha.bubble.tween.TweenAnimation;
import com.martha.bubble.tween.TweenListener;
import com.martha.bubble.ui.GameStat;
import com.martha.bubble.util.Util;

import java.util.LinkedList;

/**
 * Created by Martha on 11/3/2015.
 */
public class Board {
    // region Instance Fields
    private GameController gameController;
    private Cell cells[][];
    private LayerStage stage;
    private AssetController assetController;
    private LinkedList<BallType> permittedTypes;
    private Pool<Ball> ballPool;
    private TweenAnimation tweenAnimation;
    private TouchLocker touchLocker;
    private int fallingBallsQuantity;
    private int rollingBallQuantity;
    private boolean droppingDown;
    private int remainingBallsQuantity;
    private Color currentScoreLabelColor;
    private GameStat gameStat;
    private int deletingBallQuantity;
    private float scorePositionX;
    private float scorePositionY;
    private float labelX;
    private float labelY;
    private Label label;
    private float boardX;
    private float boardY;
    // endregion

    // region Ctor
    Board(Game game, float boardY) {
        this.stage = game.getStage();
        this.assetController = game.getAssetController();
        this.gameController = game.getGameController();
        this.gameStat = gameController.getGameStatPanel();
        this.permittedTypes = new LinkedList<BallType>();
        this.tweenAnimation = game.getTweenAnimation();
        this.touchLocker = new TouchLocker(boardX, boardY, ParamConst.BOARD_WIDTH, ParamConst.BOARD_HEIGHT);
        this.cells = new Cell[ParamConst.ROWS_QUANTITY][ParamConst.COLUMNS_QUANTITY];
        this.label = new Label("text", assetController.getLabelStyle());
        this.label.setSize(0, 0);
        this.label.setFontScale(ParamConst.SCORE_LABEL_FONT_SCALE);
        this.boardX = 0;
        this.boardY = boardY;
    }
    // endregion

    // region Logic Public Methods  - Board Creation
    public void initBoardItems() {
        createObjects();
        formatBallsType();
        initCellsListeners();
        this.gameStat = gameController.getGameStatPanel();
    }

    public void createNewGame() {
        obtainNewBalls();
        formatBallsType();
    }

    private void createObjects() {
        Drawable localCellDrawable = assetController.getSkin().getDrawable(SkinConst.DRAWABLE_CELL);
        Drawable localBallDrawable = assetController.getSkin().getDrawable(SkinConst.DRAWABLE_BALL);
        Pallet pallet = gameController.getPallet();
        float tempX = boardX;
        float tempY = boardY;
        arrangePooling();
        for (int i = 0; i < this.cells.length; i++) {
            for (int j = 0; j < this.cells[0].length; j++) {
                this.cells[i][j] = new Cell(localCellDrawable);
                this.cells[i][j].setPosition(tempX, tempY);
                this.cells[i][j].setCellIndexes(i, j);
                this.cells[i][j].occupy(ballPool.obtain());
                this.cells[i][j].getBall().setDrawable(localBallDrawable);
                pallet.applyRandomColor(this.cells[i][j].getBall());
                this.cells[i][j].refreshBallPosition();
                tempX += ParamConst.CELL_WIDTH;

            }
            tempX = boardX;
            tempY += ParamConst.CELL_HEIGHT;
        }
    }

    private void formatBallsType() {
        refreshPermittedBallList();
        do {
            formatColorCombination();
        } while (noMatch());
    }

    public void addAllActorsToStage() {
        for (int i = 0; i < this.cells.length; i++) {
            for (int j = 0; j < this.cells[0].length; j++) {
                if (cells[i][j].getBall() != null) this.stage.addActor(cells[i][j].getBall(), LayerType.L2);
                this.stage.addActor(cells[i][j], LayerType.L1);
            }
        }
        this.stage.addActor(touchLocker, LayerType.L1);
        this.touchLocker.toBack();
    }
    public void removeAllActorsFromStage() {
        for (int i = 0; i < this.cells.length; i++) {
            for (int j = 0; j < this.cells[0].length; j++) {
                if (cells[i][j].getBall() != null) cells[i][j].getBall().remove();
                cells[i][j].remove();
            }
        }
        this.touchLocker.remove();
    }

    public void addBallsToStage() {
        gameController.resetColors();
        float delay = TweenConst.delayBetweenEachBallAppearance;
        for (int i = 0; i < this.cells.length; i++) {
            for (int j = 0; j < this.cells[0].length; j++) {
                final int iTemp = i;
                final int jTemp = j;
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        stage.addActor(cells[iTemp][jTemp].getBall(), LayerType.L2);
                        if (iTemp == cells.length - 1 && jTemp == cells[0].length - 1) {
                            gameController.getTweenAnimation().getTweenManager().killTarget(gameController.getGameOverLabel());
                            gameController.getTweenAnimation().smoothDisappear(gameController.getGameOverLabel(), 8 * TweenConst.delayBetweenEachBallAppearance / 1000, new TweenListener() {
                                @Override
                                public void onComplete() {
                                    gameController.resetGameOverMessage(gameController.getGameOverLabel());
                                }
                            });
                            gameController.setGameState(GameState.GAME);
                            touchLocker.toBack();
                        }
                    }
                }, delay);
                delay += TweenConst.delayBetweenEachBallAppearance;
            }
        }
    }

    public void freeSingleBall(Ball ball) {
        this.ballPool.free(ball);
        ball.remove();
    }

    private void arrangePooling() {
        this.ballPool = new Pool<Ball>() {
            @Override
            protected Ball newObject() {
                return new Ball();
            }
        };
    }

    private void obtainNewBalls() {
        Pallet pallet = gameController.initPallet();
        for (int i = 0; i < this.cells.length; i++) {
            for (int j = 0; j < this.cells[0].length; j++) {
                this.cells[i][j].occupy(ballPool.obtain());
                pallet.applyRandomColor(this.cells[i][j].getBall());
                this.cells[i][j].refreshBallPosition();
            }
        }
    }
    // endregion

    // region Logic Methods - Color combination check and replace

    /**
     * Checks if there are 4 or more balls in a raw that have same color, if yes then changes the color of 4-th ball
     **/
    private void refreshPermittedBallList() {
        permittedTypes.clear();
        Pallet pallet = gameController.getPallet();
        for (int i = 0; i < pallet.getValidBallTypes().size(); i++) {
            permittedTypes.add(pallet.getValidBallTypes().get(i));
        }
    }

    private void formatColorCombination() {
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[0].length; j++) {
                changeMatchingBallType(cells[i][j], i, j);
            }
        }
    }

    private void changeMatchingBallType(Cell cell, int rawIdx, int columnIdx) {
        Ball currentBall = cell.getBall();
        boolean index = false;
        if (rawIdx > 2) {
            BallType bottomBallType1 = cells[rawIdx - 1][columnIdx].getBall().getType();
            BallType bottomBallType2 = cells[rawIdx - 2][columnIdx].getBall().getType();
            BallType bottomBallType3 = cells[rawIdx - 3][columnIdx].getBall().getType();
            if (bottomBallType1 == bottomBallType2 && bottomBallType2 == bottomBallType3) {
                permittedTypes.remove(bottomBallType1);
                index = true;
            }
        }
        if (rawIdx < cells.length - 3) {
            BallType topBallType1 = cells[rawIdx + 1][columnIdx].getBall().getType();
            BallType topBallType2 = cells[rawIdx + 2][columnIdx].getBall().getType();
            BallType topBallType3 = cells[rawIdx + 3][columnIdx].getBall().getType();
            if (topBallType1 == topBallType2 && topBallType2 == topBallType3) {
                permittedTypes.remove(topBallType1);
                index = true;
            }
        }
        if (columnIdx < cells[0].length - 3) {
            BallType rightBallType1 = cells[rawIdx][columnIdx + 1].getBall().getType();
            BallType rightBallType2 = cells[rawIdx][columnIdx + 1].getBall().getType();
            BallType rightBallType3 = cells[rawIdx][columnIdx + 1].getBall().getType();
            if (rightBallType1 == rightBallType2 && rightBallType2 == rightBallType3) {
                permittedTypes.remove(rightBallType1);
                index = true;
            }
        }
        if (columnIdx > 2) {
            BallType leftBallType1 = cells[rawIdx][columnIdx - 1].getBall().getType();
            BallType leftBallType2 = cells[rawIdx][columnIdx - 1].getBall().getType();
            BallType leftBallType3 = cells[rawIdx][columnIdx - 1].getBall().getType();
            if (leftBallType1 == leftBallType2 && leftBallType2 == leftBallType3) {
                permittedTypes.remove(leftBallType1);
                index = true;
            }
        }
        if (index) {
            BallType type = permittedTypes.get(Util.random.nextInt(permittedTypes.size()));
            currentBall.setType(type);
            currentBall.setColor(gameController.getPallet().acquireColor(type));
        }
        refreshPermittedBallList();
    }
    // endregion

    // region Listener
    private void initCellsListeners() {
        for (int i = 0; i < this.cells.length; i++) {
            for (int j = 0; j < this.cells[0].length; j++) {
                this.cells[i][j].setTouchPermitted(true);
                initCellListener(cells[i][j]);
            }
        }
    }

    private void initCellListener(final Cell cell) {
        cell.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (cell.getBall() != null) {
                    Ball clickedBall = cell.getBall();
                    if (matchExist(clickedBall)) {
                        gameController.getAssetController().playSound(SoundType.POKE);
                        touchLocker.toFront();
                        labelX = clickedBall.getX(Align.center);
                        labelY = clickedBall.getY(Align.center);
                        currentScoreLabelColor = gameController.getPallet().acquireColor(clickedBall.getType());
                        markAllMatches(clickedBall);
                        deleteMatchedBalls();
                        if (!animateAndReplaceBalls()) {
                            if (noMatch()) {
                                gameController.gameOver();
                            }
                        }
                    } else {
                        resetUnmatchedParameters(clickedBall);
                    }

                }
            }
        });
    }
    // endregion

    // region Logic Methods - Match Checking

    /**
     * Checks if there are same type balls in a line with clicked ball, if yes sets balls in "matched" condition
     **/
    private boolean matchExist(Ball ball) {
        BallType commonType = ball.getType();
        int rawIdx = ball.getCell().getRowIndex();
        int columnIdx = ball.getCell().getColumnIndex();
        boolean thereIsMatches = false;

        if (rawIdx < this.cells.length - 1) {
            Ball topBall = this.cells[rawIdx + 1][columnIdx].getBall();
            if (topBall != null && !topBall.isChecked()) {
                topBall.setChecked(true);
                if (topBall.getType() == commonType) {
                    topBall.setMatched(true);
                    ball.setMatchedOnTop(true);
                    thereIsMatches = true;
                }
            }
        }
        if (rawIdx > 0) {
            Ball bottomBall = this.cells[rawIdx - 1][columnIdx].getBall();
            if (bottomBall != null && !bottomBall.isChecked()) {
                bottomBall.setChecked(true);
                if (bottomBall.getType() == commonType) {
                    bottomBall.setMatched(true);
                    ball.setMatchedOnBottom(true);
                    thereIsMatches = true;
                }
            }
        }
        if (columnIdx < this.cells[0].length - 1) {
            Ball rightBall = this.cells[rawIdx][columnIdx + 1].getBall();
            if (rightBall != null && !rightBall.isChecked()) {
                rightBall.setChecked(true);
                if (rightBall.getType() == commonType) {
                    rightBall.setMatched(true);
                    ball.setMatchedOnRight(true);
                    thereIsMatches = true;
                }
            }
        }
        if (columnIdx > 0) {
            Ball leftBall = this.cells[rawIdx][columnIdx - 1].getBall();
            if (leftBall != null && !leftBall.isChecked()) {
                leftBall.setChecked(true);
                if (leftBall.getType() == commonType) {
                    leftBall.setMatched(true);
                    ball.setMatchedOnLeft(true);
                    thereIsMatches = true;
                }
            }
        }
        if (thereIsMatches) {
            ball.setMatched(true);
        }
        ball.setChecked(true);
        return thereIsMatches;
    }

    private void markAllMatches(Ball ball) {
        int rawIndex = ball.getCell().getRowIndex();
        int columnIndex = ball.getCell().getColumnIndex();
        if (ball.isMatchedOnTop()) {
            int topRawIdx = rawIndex;
            Ball ballOnTop = ball;
            do {
                topRawIdx++;
                if (this.cells[topRawIdx][columnIndex].getBall() != null) {
                    ballOnTop = this.cells[topRawIdx][columnIndex].getBall();
                    matchExist(ballOnTop);
                    markAllMatches(ballOnTop);
                }
            } while (ballOnTop.isMatchedOnTop());
        }
        if (ball.isMatchedOnBottom()) {
            int bottomRawIdx = rawIndex;
            Ball ballOnBottom = ball;
            do {
                bottomRawIdx--;
                if (this.cells[bottomRawIdx][columnIndex].getBall() != null) {
                    ballOnBottom = this.cells[bottomRawIdx][columnIndex].getBall();
                    matchExist(ballOnBottom);
                    markAllMatches(ballOnBottom);
                }
            } while (ballOnBottom.isMatchedOnBottom());
        }
        if (ball.isMatchedOnRight()) {
            int rightColumnIdx = columnIndex;
            Ball ballOnRight = ball;
            do {
                rightColumnIdx++;
                if (this.cells[rawIndex][rightColumnIdx].getBall() != null) {
                    ballOnRight = this.cells[rawIndex][rightColumnIdx].getBall();
                    matchExist(ballOnRight);
                    markAllMatches(ballOnRight);
                }
            } while (ballOnRight.isMatchedOnRight());
        }
        if (ball.isMatchedOnLeft()) {
            int leftColumnIdx = columnIndex;
            Ball ballOnLeft = ball;
            do {
                leftColumnIdx--;
                if (this.cells[rawIndex][leftColumnIdx].getBall() != null) {
                    ballOnLeft = this.cells[rawIndex][leftColumnIdx].getBall();
                    matchExist(ballOnLeft);
                    markAllMatches(ballOnLeft);
                }
            } while (ballOnLeft.isMatchedOnLeft());
        }
    }

    private void resetUnmatchedParameters(Ball ball) {
        int rawIdx = ball.getCell().getRowIndex();
        int columnIdx = ball.getCell().getColumnIndex();
        if (rawIdx < this.cells.length - 1) {
            Ball topBall = cells[rawIdx + 1][columnIdx].getBall();
            if (topBall != null) {
                topBall.resetBooleanParameters();
            }
        }
        if (rawIdx > 0) {
            Ball bottomBall = cells[rawIdx - 1][columnIdx].getBall();
            if (bottomBall != null) {
                bottomBall.resetBooleanParameters();
            }
        }
        if (columnIdx < this.cells[0].length - 1) {
            Ball rightBall = cells[rawIdx][columnIdx + 1].getBall();
            if (rightBall != null) {
                rightBall.resetBooleanParameters();
            }
        }
        if (columnIdx > 0) {
            Ball leftBall = cells[rawIdx][columnIdx - 1].getBall();
            if (leftBall != null) {
                leftBall.resetBooleanParameters();
            }
        }
    }

    private boolean noMatch() {
        /** Vertical check **/
        for (int j = 0; j < this.cells[0].length; j++) {
            for (int i = 0; i < this.cells.length - 1; i++) {
                Ball currentBall = this.cells[i][j].getBall();
                Ball aboveBall = this.cells[i + 1][j].getBall();
                if (currentBall != null && aboveBall != null && currentBall.getType().equals(aboveBall.getType())) {
                    return false;
                }
            }
        }
        /** Horizontal check **/
        for (int j = 0; j < this.cells.length; j++) {
            for (int i = 0; i < this.cells[0].length - 1; i++) {
                Ball currentBall = this.cells[j][i].getBall();
                Ball nextBall = this.cells[j][i + 1].getBall();
                if (currentBall != null && nextBall != null && currentBall.getType().equals(nextBall.getType())) {
                    return false;
                }
            }
        }
        return true;
    }
    // endregion

    // region Logic Methods - Deletes balls
    private void deleteMatchedBalls() {
        deletingBallQuantity = 0;
        for (int i = 0; i < this.cells.length; i++) {
            for (int j = 0; j < this.cells[0].length; j++) {
                final Ball currentBall = this.cells[i][j].getBall();
                if (currentBall != null) {
                    if (currentBall.isMatched()) {
                        deletingBallQuantity++;
                        currentBall.toFront();
                        tweenAnimation.moveAndDisappear(currentBall, scorePositionX,
                                scorePositionY, new TweenListener() {
                                    @Override
                                    public void onComplete() {
                                        ballPool.free(currentBall);
                                        currentBall.remove();
                                        deletingBallQuantity--;
                                        if (deletingBallQuantity == 0) {
                                            gameStat.dataUpdate(currentScoreLabelColor);
                                        }
                                    }
                                });
                        this.cells[i][j].free();
                    } else {
                        currentBall.resetBooleanParameters();
                    }
                }
            }
        }
        if (deletingBallQuantity > 0) {
            animateLabel(gameStat.countScore(deletingBallQuantity), labelX, labelY, currentScoreLabelColor);
        }
    }

    public void animateLabel(int score, float startPointX, float startPointY, Color color) {
        this.label.getColor().a = 1;
        this.label.setText(Integer.toString(score));
        label.setAlignment(Align.center);
        this.label.setPosition(startPointX - label.getWidth() / 2, startPointY - label.getHeight() / 2);
        this.label.setColor(color);
        this.stage.addActor(label, LayerType.L3);
        this.tweenAnimation.moveAndSmoothDisappear(label, startPointY - label.getHeight() / 2, new TweenListener() {
            @Override
            public void onComplete() {
                label.remove();
            }
        });
    }

    public void gameOver() {
        touchLocker.toFront();
        remainingBallsQuantity = 0;
        fallingBallsQuantity = 0;
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[0].length; j++) {
                if (!cells[i][j].isFree()) {
                    remainingBallsQuantity++;
                    final Ball currentBall = cells[i][j].getBall();
                    cells[i][j].free();
                    interrupt(currentBall);
                    tweenAnimation.jump(currentBall, boardY, new TweenListener() {
                        @Override
                        public void onComplete() {
                            tweenAnimation.roll(currentBall, boardX, new TweenListener() {
                                @Override
                                public void onComplete() {
                                    currentBall.setRotation(0);
                                    tweenAnimation.disappear(currentBall, new TweenListener() {
                                        @Override
                                        public void onComplete() {
                                            remainingBallsQuantity--;
                                            freeSingleBall(currentBall);
                                            if (remainingBallsQuantity == 0) {
                                                gameStat.refreshData();
                                                startNewGame();
                                            }
                                        }
                                    });
                                }
                            });
                        }
                    });
                }
            }
        }
    }

    public void startNewGame() {
        createNewGame();
        addBallsToStage();
    }
    // endregion

    // region Logic Methods - Board Check
    private boolean animateAndReplaceBalls() {
        droppingDown = false;
        for (int i = 0; i < cells[0].length; i++) {
            dropAndAnimateBalls(i);
        }
        if (!droppingDown) {
            if (!checkOutEmptyColumns()) {
                touchLocker.toBack();
                return false;
            } else {
                return true;
            }
        }
        return droppingDown;
    }


    public void interrupt(Ball ball) {
        if (ball != null) {
            tweenAnimation.getTweenManager().killTarget(ball);
            ball.stop();
        }
    }

    private boolean dropAndAnimateBalls(int columnIndex) {
        boolean index = false;
        int firstEmptyCellRowIndex = 0;
        int firstDroppingBallRowIndex = 0;
        int lastBallRowIndex = 0;
        for (int i = 0; i < cells.length; i++) {
            if (this.cells[i][columnIndex].isFree()) {
                firstEmptyCellRowIndex = i;
                do {
                    i++;
                } while (i < cells.length && cells[i][columnIndex].isFree());
                firstDroppingBallRowIndex = i;
                for (int j = firstDroppingBallRowIndex; j < cells.length; j++) {
                    if (!this.cells[j][columnIndex].isFree()) {
                        this.cells[j][columnIndex].getBall().fall();
                        this.fallingBallsQuantity++;
                        lastBallRowIndex = j;
                    }
                }
                break;
            }
        }
        for (int i = firstDroppingBallRowIndex; i < this.cells.length; i++) {
            final Ball droppingBall = this.cells[i][columnIndex].getBall();
            if (droppingBall != null && droppingBall.isFalling()) {
                droppingDown = true;
                index = true;
                float targetPositionY = this.cells[firstEmptyCellRowIndex][columnIndex].getBallY();
                if (i == lastBallRowIndex) {
                    tweenAnimation.jump(droppingBall, targetPositionY, new TweenListener() {
                        @Override
                        public void onComplete() {
                            droppingBall.stop();
                            fallingBallsQuantity--;
                            if (fallingBallsQuantity == 0) {
                                if (!checkOutEmptyColumns()) {
                                    if (noMatch()) {
                                        gameController.gameOver();
                                    } else {
                                        touchLocker.toBack();
                                    }
                                }
                            }
                        }
                    });
                    this.cells[i][columnIndex].free();
                    this.cells[firstEmptyCellRowIndex][columnIndex].occupy(droppingBall);
                    firstEmptyCellRowIndex++;
                } else {
                    tweenAnimation.jelly(droppingBall, targetPositionY, new TweenListener() {
                        @Override
                        public void onComplete() {
                            droppingBall.stop();
                            fallingBallsQuantity--;
                            if (fallingBallsQuantity == 0) {
                                if (!checkOutEmptyColumns()) {
                                    if (noMatch()) {
                                        gameController.gameOver();
                                    } else {
                                        touchLocker.toBack();
                                    }
                                }
                            }
                        }
                    });
                    this.cells[i][columnIndex].free();
                    this.cells[firstEmptyCellRowIndex][columnIndex].occupy(droppingBall);
                    firstEmptyCellRowIndex++;
                }
            }
        }
        return index;
    }

    private boolean checkOutEmptyColumns() {
        boolean index = false;
        int emptyColumnIndex;
        int replaceableColumnIndex;
        int nextOccupied;
        for (int i = this.cells[0].length - 1; i > 0; i--) {
            if (cells[0][i].isFree()) {
                emptyColumnIndex = i;
                nextOccupied = i;
                do {
                    nextOccupied--;
                } while (nextOccupied > 0 && this.cells[0][nextOccupied].isFree());
                if (!this.cells[0][nextOccupied].isFree()) {
                    replaceableColumnIndex = nextOccupied;
                    markColumnsToRoll(emptyColumnIndex, replaceableColumnIndex);
                    index = true;
                }
            }
        }
        rollAndReplaceColumns();
        return index;
    }

    private void rollAndReplaceColumns() {
        for (int i = 0; i < this.cells.length; i++) {
            for (int j = 0; j < this.cells[0].length; j++) {
                final Ball currentBall = this.cells[i][j].getBall();
                if (currentBall != null && currentBall.isRolling()) {
                    tweenAnimation.roll(currentBall, currentBall.getRollDesPointX(), new TweenListener() {
                        @Override
                        public void onComplete() {
                            currentBall.stop();
                            currentBall.setRotation(0);
                            rollingBallQuantity--;
                            if (rollingBallQuantity == 0) {
                                if (noMatch()) {
                                    gameController.gameOver();
                                } else {
                                    touchLocker.toBack();
                                }
                            }
                        }
                    });
                }
            }
        }
    }

    private void markColumnsToRoll(final int emptyColumnIndex, final int replaceableColumnIndex) {
        for (int i = 0; i < this.cells.length; i++) {
            if (!this.cells[i][replaceableColumnIndex].isFree()) {
                final Ball currentBall = this.cells[i][replaceableColumnIndex].getBall();
                final Cell occupyingCell = this.cells[i][emptyColumnIndex];
                final Cell freeingCell = this.cells[i][replaceableColumnIndex];
                currentBall.roll(occupyingCell.getBallX());
                rollingBallQuantity++;
                freeingCell.free();
                occupyingCell.occupy(currentBall);
            }
        }
    }
    // endregion

    // region Getters and Setters
    public void setScorePositionX(float scorePositionX) {
        this.scorePositionX = scorePositionX;
    }
    public void setScorePositionY(float scorePositionY) {
        this.scorePositionY = scorePositionY;
    }
    // endregion
}
