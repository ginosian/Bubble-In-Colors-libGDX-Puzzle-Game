package com.martha.bubble.input;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.martha.bubble.listener.KeyAction;

/**
 * Created by Lion on 2/3/16.
 */
public class InputHandler extends InputAdapter {

    KeyAction keyAction;

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.BACK) {
            if (keyAction != null) keyAction.action();
        }
        return super.keyDown(keycode);
    }

    public void setKeyAction(KeyAction keyAction) {
        this.keyAction = keyAction;
    }
}
