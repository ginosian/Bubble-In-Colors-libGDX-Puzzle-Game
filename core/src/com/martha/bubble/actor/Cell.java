package com.martha.bubble.actor;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;
import com.martha.bubble.constant.ParamConst;

/**
 * Created by Martha on 11/1/2015.
 */
public class Cell extends Image {

    // region Instance Fields
    private Ball ball;
//    private CellBackground background;
    private int rowIndex;
    private int columnIndex;
    private boolean touchPermitted;
    private float ballX;
    private float ballY;
    // endregion

    // region CTOR
    public Cell(Drawable drawable) {
        this.refreshCellSize();
        this.setDrawable(drawable);
    }
    // endregion

    // region Member Methods
    private void refreshCellSize() {
        this.setSize(ParamConst.CELL_WIDTH, ParamConst.CELL_HEIGHT);
    }
    public void refreshBallPosition() {
        this.ball.setPosition(getX(Align.center), getY(Align.center), Align.center);
        this.ballX = ball.getX();
        this.ballY = ball.getY();
    }
    public void occupy(Ball ball){
        this.ball = ball;
        this.ball.setCell(this);
    }
    public void free(){
        this.ball.setCell(null);
        this.ball = null;
    }
    public boolean isFree(){
        return ball == null;
    }
    // endregion

    // region Getters and Setters
    public void setCellIndexes(int rowIndex, int columnIndex) {
        this.rowIndex = rowIndex;
        this.columnIndex = columnIndex;
    }
    public int getRowIndex() {
        return rowIndex;
    }
    public int getColumnIndex() {
        return columnIndex;
    }
    public Ball getBall() {
        return ball;
    }
    public boolean isTouchPermitted() {
        return touchPermitted;
    }
    public void setTouchPermitted(boolean touchPermitted) {
        this.touchPermitted = touchPermitted;
    }
    public float getBallX() {
        return ballX;
    }
    public float getBallY() {
        return ballY;
    }
    // endregion
}
