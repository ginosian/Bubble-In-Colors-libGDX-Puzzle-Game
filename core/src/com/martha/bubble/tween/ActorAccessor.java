package com.martha.bubble.tween;

import aurelienribon.tweenengine.TweenAccessor;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Align;

/**
 * Created by Martha on 11/25/2015.
 */
public class ActorAccessor implements TweenAccessor<Actor> {
    public static final int WIDTH = 0;
    public static final int HEIGHT = 1;
    public static final int X = 2;
    public static final int Y = 3;
    public static final int A = 4;
    public static final int MOVE_CENTER_X = 5;
    public static final int MOVE_CENTER_Y = 6;
    public static final int ROTATE_RIGHT = 7;
    public static final int ROTATE_LEFT = 8;

    @Override
    public int getValues(Actor actor, int i, float[] floats) {
        switch (i){
            case WIDTH:
                floats[0] = actor.getWidth();
                break;
            case HEIGHT:
                floats[0] = actor.getHeight();
                break;
            case X:
                floats[0] = actor.getX();
                break;
            case Y:
                floats[0] = actor.getY();
                break;
            case A:
                floats[0] = actor.getColor().a;
                break;
            case MOVE_CENTER_X:
                floats[0] = actor.getX(Align.center);
                break;
            case MOVE_CENTER_Y:
                floats[0] = actor.getY(Align.center);
                break;
            case ROTATE_RIGHT:
                floats[0] = actor.getOriginX();
                floats[1] = actor.getOriginY();
                floats[2] = actor.getRotation();
                break;
            case ROTATE_LEFT:
                floats[0] = actor.getOriginX();
                floats[1] = actor.getOriginY();
                floats[2] = actor.getRotation();
                break;
        }
        return 1;
    }

    @Override
    public void setValues(Actor actor, int i, float[] floats) {
        switch (i){
            case WIDTH:
                actor.setWidth(floats[0]);
                break;
            case HEIGHT:
                actor.setHeight(floats[0]);
                break;
            case X:
                actor.setX(floats[0]);
                break;
            case Y:
                actor.setY(floats[0]);
                break;
            case A:
                actor.setColor(actor.getColor().r, actor.getColor().g, actor.getColor().b, floats[0]);
                break;
            case MOVE_CENTER_X:
                actor.setX(floats[0] - actor.getWidth() / 2);
                break;
            case MOVE_CENTER_Y:
                actor.setY(floats[0] - actor.getHeight() / 2);
                break;
            case ROTATE_RIGHT:
                actor.setOriginX(actor.getWidth() / 2);
                actor.setOriginY(actor.getHeight() / 2);
                actor.setRotation(actor.getRotation() - 50);
                break;
            case ROTATE_LEFT:
                actor.setOriginX(actor.getWidth() / 2);
                actor.setOriginY(actor.getHeight() / 2);
                actor.setRotation(actor.getRotation() + 30);
                break;
        }
    }
}
