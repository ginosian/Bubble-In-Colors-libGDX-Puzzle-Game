package com.martha.bubble.actor;

import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Pool;
import com.martha.bubble.constant.ParamConst;
import com.martha.bubble.enums.BallType;

/**
 * Created by Martha on 11/1/2015.
 */
public class Ball extends Image implements Pool.Poolable{

    // region Instance Fields
    private Cell cell;
    private BallType type;
    private boolean matchedOnTop;
    private boolean matchedOnBottom;
    private boolean matchedOnLeft;
    private boolean matchedOnRight;
    private boolean matched;
    private boolean checked;
    private boolean falling;
    private boolean rolling;
    private float rollDesPointX;
    // endregion

    // region CTOR
    public Ball() {
        refreshBallSize();
        this.setTouchable(Touchable.disabled);
    }
    // endregion

    // region Member Methods
    private void refreshBallSize(){
        this.setSize(ParamConst.BALL_WIDTH, ParamConst.BALL_HEIGHT);
    }
    public void resetBooleanParameters(){
        matchedOnTop = false;
        matchedOnBottom = false;
        matchedOnLeft = false;
        matchedOnRight = false;
        matched = false;
        checked = false;
        falling = false;
        rolling = false;
    }

    @Override
    public void reset() {
        refreshBallSize();
        resetBooleanParameters();
        this.rollDesPointX = 0;
        this.type = null;
        this.cell = null;
        this.setColor(1, 1, 1, 1);
    }
    // endregion

    // region Getters and Setters
    public Cell getCell() {
        return cell;
    }
    public void setCell(Cell cell) {
        this.cell = cell;
    }
    public BallType getType() {
        return type;
    }
    public void setType(BallType type) {
        this.type = type;
    }
    public boolean isMatchedOnTop() {
        return matchedOnTop;
    }
    public void setMatchedOnTop(boolean matchedOnTop) {
        this.matchedOnTop = matchedOnTop;
    }
    public boolean isMatchedOnBottom() {
        return matchedOnBottom;
    }
    public void setMatchedOnBottom(boolean matchedOnBottom) {
        this.matchedOnBottom = matchedOnBottom;
    }
    public boolean isMatchedOnLeft() {
        return matchedOnLeft;
    }
    public void setMatchedOnLeft(boolean matchedOnLeft) {
        this.matchedOnLeft = matchedOnLeft;
    }
    public boolean isMatchedOnRight() {
        return matchedOnRight;
    }
    public void setMatchedOnRight(boolean matchedOnRight) {
        this.matchedOnRight = matchedOnRight;
    }
    public boolean isMatched() {
        return matched;
    }
    public void setMatched(boolean matched) {
        this.matched = matched;
    }
    public boolean isChecked() {
        return checked;
    }
    public void setChecked(boolean checked) {
        this.checked = checked;
    }
    public void fall(){
        this.falling = true;
    }
    public boolean isFalling() {
        return falling;
    }
    public void roll(float destinationPointX){
        this.rollDesPointX = destinationPointX;
        this.rolling = true;
    }
    public boolean isRolling() {
        return rolling;
    }
    public void stop(){
        this.falling = false;
        this.rolling = false;
        this.rollDesPointX = 0;
    }
    public float getRollDesPointX() {
        return rollDesPointX;
    }
    // endregion
}
