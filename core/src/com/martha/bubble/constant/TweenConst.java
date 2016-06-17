package com.martha.bubble.constant;

/**
 * Created by Martha on 11/25/2015.
 */
public class TweenConst {

    // region JELLY
    public static final float jellyLowLim = 40;
    public static final float jellyHighLim = 45;

    public static final float phase1DurationRatio = 0.2f;
    public static final float phase2DurationRatio = 0.35f;
    public static final float phase3DurationRatio = 0.25f;
    public static final float phase4DurationRatio = 0.12f;
    public static final float phase5DurationRatio = 0.08f;

    public static final float phase1heightIncrease = 1.3f;
    public static final float phase1widthDecrease = 0.7f;
    public static final float phase2heightIncrease = 1.15f;
    public static final float phase2widthDecrease = 0.9f;
    public static final float phase3heightIncrease = 1.10f;
    public static final float phase3widthDecrease = 0.95f;
    public static final float phase4heightIncrease = 1.05f;
    public static final float phase4widthDecrease = 0.98f;

    public static final float phase1DroppingRatio = 0.3f;
    public static final float phase3DroppingSizeRatio = 0.15f;
    // endregion

    // region JUMP
    public static final float jumpLowLim = 50; // dependant
    public static final float jumpHighLim = 60; // dependant
    public static final float droppingDurationRatio = 0.2f; // dependant
    public static final float correctingCoefficent = 0.9f;
    public static final float phase2Duration = 0.3f * correctingCoefficent;
    public static final float phase3Duration = 0.2f * correctingCoefficent;
    public static final float phase4Duration = 0.14f * correctingCoefficent;
    public static final float phase5Duration = 0.1f * correctingCoefficent;
    public static final float phase6Duration = 0.07f * correctingCoefficent;

    public static final float jumpingDurationRatio = 0.4f;
    public static final float fallingDurationRation = 0.6f;

    public static final float phase2JumpingRatio = 1.9f;
    public static final float phase3JumpingRatio = 1.2f;
    public static final float phase4JumpingRatio = 0.8f;
    public static final float phase5JumpingRatio= 0.3f;
    public static final float phase6JumpingRatio = 0.02f;
    // endregion

    // region Rotate
    public static final float rotateLowLim = 120;
    public static final float rotateHighLim = 150;
    public static final float rotatePhase2Duration = 0.2f;
    public static final float rotatePhase1XIncrease = 0.1f;
    // endregion

    // region moveAndDisappear
    public static final float MDLowLim = 120;
    public static final float MDHighLim = 150;
    // endregion

    // region disappear
    public static final float disappearDuration = 0.5f;
    // endregion

    // region SmoothDisappear
    public static final float GAME_OVER_lABEL_DURATION = 0.8f;
    // endRegion

    public static final float MENU_DIALOG_DURATION = 0.8f;

    public static float GAME_OVER_TO_NEW_GAME_DURATION = 1.5f;
    public static float delayBetweenEachBallAppearance = 0.02f;

}
