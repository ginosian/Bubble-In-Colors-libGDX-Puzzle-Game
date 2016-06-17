package com.martha.bubble.actor;

import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by Martha on 11/3/2015.
 */
public class TouchLocker extends Actor {
    public TouchLocker(float x, float y, float width, float height) {
        this.setPosition(x, y);
        this.setSize(width, height);
    }

    @Override
    public void toBack() {
        super.toBack();
    }

    @Override
    public void toFront() {
        super.toFront();
    }
}
