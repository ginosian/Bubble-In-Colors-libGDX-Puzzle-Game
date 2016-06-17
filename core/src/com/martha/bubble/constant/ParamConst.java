package com.martha.bubble.constant;

import com.badlogic.gdx.Gdx;

/**
 * Created by Martha on 1/14/2016.
 */
public class ParamConst {
    public static final float SW = Gdx.graphics.getWidth() / 100;
    public static final float SH = Gdx.graphics.getWidth() / 100;
    // region Board
    public static final int ROWS_QUANTITY = 9;
    public static final int COLUMNS_QUANTITY = 9;
    public static final float CELL_WIDTH = (Gdx.graphics.getWidth()) / COLUMNS_QUANTITY;
    public static final float CELL_HEIGHT = CELL_WIDTH;
    public static final float BALL_WIDTH = CELL_WIDTH * 0.85f;
    public static final float BALL_HEIGHT = BALL_WIDTH;
    public static final float BOARD_WIDTH = CELL_WIDTH * COLUMNS_QUANTITY;
    public static final float BOARD_HEIGHT = CELL_HEIGHT * ROWS_QUANTITY;
    public static final int PALLETS_QUANTITY = 33;
    public static final int COLOR_QUANTITY = 5;
    public static float SCORE_LABEL_FONT_SCALE = CELL_HEIGHT / 100;
    public static final float GAME_OVER_LABEL_WIDTH = Gdx.graphics.getWidth() * 0.80f;
    // endregion

    // region MenuDialog
    public static float MENU_DIALOG_BG_LINE_WIDTH = SW * 2;
    public static float MENU_DIALOG_HEIGHT = Gdx.graphics.getHeight() - 50;
    // endregion


    public static final float BP_HEIGHT_RATIO = 0.3f;

    // region Delays

    // endregion





  // region Positions and Sizes
    public static float BOARD_X = 0;
    public static float BOARD_Y = (Gdx.graphics.getHeight() - Gdx.graphics.getWidth()) * 0.6f;

    public static float BOARD_CENTER_X = Gdx.graphics.getWidth() / 2;
    public static float BOARD_CENTER_Y = BOARD_Y  + (ROWS_QUANTITY * CELL_HEIGHT) / 2;

    public static float UPPER_PANEL_X = 0;
    public static float UPPER_PANEL_Y = BOARD_Y +ROWS_QUANTITY * CELL_HEIGHT;

    public static float BUTTON_PANEL_HEIGHT = BOARD_Y * 0.4f;
    public static float BUTTON_PANEL_WIDTH = Gdx.graphics.getWidth();

    public static float BUTTON_PANEL_X = 0;
    public static float BUTTON_PANEL_Y = BOARD_Y - BUTTON_PANEL_HEIGHT;

    public static float BUTTON_PANEL_BUTTON_WIDTH = (BUTTON_PANEL_WIDTH / 3) * 0.8f;
    public static float BUTTON_PANEL_BUTTON_HEIGHT = BUTTON_PANEL_HEIGHT * 0.7f;

    public static float UPPER_PANEL_HEIGHT = Gdx.graphics.getHeight() - UPPER_PANEL_Y;
    public static float UPPER_PANEL_WIDTH = Gdx.graphics.getWidth();

    public static float GAME_STAT_PANEL_WIDTH = Gdx.graphics.getWidth();
    public static float GAME_STAT_PANEL_HEIGHT = UPPER_PANEL_HEIGHT;

    public static float GAME_STAT_PANEL_X = UPPER_PANEL_X;
    public static float GAME_STAT_PANEL_Y = Gdx.graphics.getHeight() - GAME_STAT_PANEL_HEIGHT;

    public static float PROGRESS_BAR_PANEL_WIDTH = Gdx.graphics.getWidth();
    public static float PROGRESS_BAR_PANEL_HEIGHT = GAME_STAT_PANEL_HEIGHT * 0.3f;

    public static float PROGRESS_BAR_WIDTH = Gdx.graphics.getWidth() * 0.8f;
    public static float PROGRESS_BAR_HEIGHT = UPPER_PANEL_HEIGHT * 0.12f;

    public static float PROGRESS_BAR_X = (Gdx.graphics.getWidth() - PROGRESS_BAR_WIDTH) / 2;
    public static float PROGRESS_BAR_Y = (PROGRESS_BAR_PANEL_HEIGHT / 2) - (PROGRESS_BAR_HEIGHT / 2);

    // region GameStat
    public static float BUTTON_PANEL_LABEL_SCALE = BUTTON_PANEL_BUTTON_WIDTH / 500;
    public static float GAME_STAT_PANEL_LABEL_SCALE = PROGRESS_BAR_PANEL_HEIGHT / 108;
    public static float GAME_OVER_LABEL_SCALE = Gdx.graphics.getWidth() * 0.0009f;
    public static float MENU_LABEL_SCALE = SW / 9;
    //endregion



    // endregion



    // region Delays
    public static float gameOverDelay = 0.8f;
    // endregion






}
