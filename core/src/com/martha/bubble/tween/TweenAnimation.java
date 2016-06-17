package com.martha.bubble.tween;

import aurelienribon.tweenengine.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Align;
import com.martha.bubble.constant.ParamConst;
import com.martha.bubble.constant.TweenConst;
import com.martha.bubble.util.Util;

/**
 * Created by Martha on 11/25/2015.
 */
public class TweenAnimation {
    // region Instance Fields
    private TweenManager tweenManager;
    // endregion

    // region Tween
    /** Drops down like a jelly and then poing's while positioning **/
    public void jelly(Actor actor1, float targetY, final TweenListener listener) {
        float actor1Width = actor1.getWidth();
        float actor1Height = actor1.getHeight();
        float centerX = actor1.getX(Align.center);
        TweenEquation equation = TweenEquations.easeOutSine;
        float animationOverallPeriod = Math.abs((actor1.getY() - targetY)
                / (Util.random.nextInt((int)(ParamConst.SH * TweenConst.jellyHighLim)) + ParamConst.SH * TweenConst.jellyLowLim));

        Timeline.createParallel()
            .push(Tween.to(actor1, ActorAccessor.MOVE_CENTER_X, animationOverallPeriod)
                    .target(centerX)
                    .ease(equation))
                .push(Timeline.createSequence()
                        .push(Timeline.createParallel()
                                .push(Tween.to(actor1, ActorAccessor.Y, TweenConst.phase1DurationRatio * animationOverallPeriod)
                                        .target(targetY - actor1Height * TweenConst.phase1DroppingRatio).ease(equation))
                                .push(Tween.to(actor1, ActorAccessor.HEIGHT, TweenConst.phase1DurationRatio * animationOverallPeriod)
                                        .target(actor1Height * TweenConst.phase1heightIncrease).ease(equation))
                                .push(Tween.to(actor1, ActorAccessor.WIDTH, TweenConst.phase1DurationRatio * animationOverallPeriod)
                                        .target(actor1Width * TweenConst.phase1widthDecrease).ease(equation)))
                        .push(Timeline.createParallel()
                                .push(Tween.to(actor1, ActorAccessor.Y, TweenConst.phase2DurationRatio * animationOverallPeriod)
                                        .target(targetY).ease(equation))
                                .push(Tween.to(actor1, ActorAccessor.WIDTH, TweenConst.phase2DurationRatio * animationOverallPeriod)
                                        .target(actor1Width).ease(equation))
                                .push(Tween.to(actor1, ActorAccessor.HEIGHT, TweenConst.phase2DurationRatio * animationOverallPeriod)
                                        .target(actor1Height).ease(equation))
                                .push(Tween.to(actor1, ActorAccessor.HEIGHT, TweenConst.phase2DurationRatio * animationOverallPeriod)
                                        .target(actor1Height * TweenConst.phase2heightIncrease).ease(equation))
                                .push(Tween.to(actor1, ActorAccessor.WIDTH, TweenConst.phase2DurationRatio * animationOverallPeriod)
                                        .target(actor1Width * TweenConst.phase2widthDecrease).ease(equation)))
                        .push(Timeline.createParallel()
                                .push(Tween.to(actor1, ActorAccessor.WIDTH, TweenConst.phase3DurationRatio * animationOverallPeriod)
                                        .target(actor1Width).ease(equation))
                                .push(Tween.to(actor1, ActorAccessor.HEIGHT, TweenConst.phase3DurationRatio * animationOverallPeriod)
                                        .target(actor1Height).ease(equation))
                                .push(Tween.to(actor1, ActorAccessor.Y, TweenConst.phase3DurationRatio * animationOverallPeriod)
                                        .target(targetY - actor1Height * TweenConst.phase3DroppingSizeRatio).ease(equation))
                                .push(Tween.to(actor1, ActorAccessor.HEIGHT, TweenConst.phase3DurationRatio * animationOverallPeriod)
                                        .target(actor1Height * TweenConst.phase3heightIncrease).ease(equation))
                                .push(Tween.to(actor1, ActorAccessor.WIDTH, TweenConst.phase3DurationRatio * animationOverallPeriod)
                                        .target(actor1Width * TweenConst.phase3widthDecrease).ease(equation)))
                        .push(Timeline.createParallel()
                                .push(Tween.to(actor1, ActorAccessor.Y, TweenConst.phase4DurationRatio * animationOverallPeriod)
                                        .target(targetY).ease(equation))
                                .push(Tween.to(actor1, ActorAccessor.WIDTH, TweenConst.phase4DurationRatio * animationOverallPeriod)
                                        .target(actor1Width).ease(equation))
                                .push(Tween.to(actor1, ActorAccessor.HEIGHT, TweenConst.phase4DurationRatio * animationOverallPeriod)
                                        .target(actor1Height).ease(equation))
                                .push(Tween.to(actor1, ActorAccessor.HEIGHT, TweenConst.phase4DurationRatio * animationOverallPeriod)
                                        .target(actor1Height * TweenConst.phase4heightIncrease).ease(equation))
                                .push(Tween.to(actor1, ActorAccessor.WIDTH, TweenConst.phase4DurationRatio * animationOverallPeriod)
                                        .target(actor1Width * TweenConst.phase4widthDecrease).ease(equation)))
                        .push(Timeline.createParallel()
                                .push(Tween.to(actor1, ActorAccessor.WIDTH, TweenConst.phase5DurationRatio * animationOverallPeriod)
                                        .target(actor1Width).ease(equation))
                                .push(Tween.to(actor1, ActorAccessor.HEIGHT, TweenConst.phase5DurationRatio * animationOverallPeriod)
                                        .target(actor1Height).ease(equation))))
                .setCallback(new TweenCallback() {
                    @Override
                    public void onEvent(int i, BaseTween<?> baseTween) {
                        if (listener != null) listener.onComplete();
                    }
                })
                .start(tweenManager);
    }

    /** Drops down and jumps few times while positioning **/
    public float jump(Actor actor1, float targetY, final TweenListener listener) {
        float actor1Height = actor1.getHeight();
        float centerX = actor1.getX(Align.center);
        TweenEquation equationOut = TweenEquations.easeOutSine;
        TweenEquation equationIn = TweenEquations.easeInSine;
        float animationOverallPeriod = Math.abs((actor1.getY() - targetY)
                / (Util.random.nextInt((int)(ParamConst.SH * TweenConst.jumpHighLim)) + ParamConst.SH * TweenConst.jumpLowLim));

        Timeline.createParallel()
                .push(Tween.to(actor1, ActorAccessor.MOVE_CENTER_X, animationOverallPeriod)
                        .target(centerX))
                .push(Timeline.createSequence()
                        .push(Tween.to(actor1, ActorAccessor.Y, TweenConst.droppingDurationRatio * animationOverallPeriod)
                                .target(targetY).ease(equationIn))
                        .push(Timeline.createSequence()
                                .push(Tween.to(actor1, ActorAccessor.Y, TweenConst.phase2Duration * TweenConst.jumpingDurationRatio)
                                        .target(targetY + actor1Height * TweenConst.phase2JumpingRatio).ease(equationOut))
                                .push(Tween.to(actor1, ActorAccessor.Y, TweenConst.phase2Duration * TweenConst.fallingDurationRation)
                                        .target(targetY).ease(equationIn)))
                        .push(Timeline.createSequence()
                                .push(Tween.to(actor1, ActorAccessor.Y, TweenConst.phase3Duration * TweenConst.jumpingDurationRatio)
                                        .target(targetY + actor1Height * TweenConst.phase3JumpingRatio).ease(equationOut))
                                .push(Tween.to(actor1, ActorAccessor.Y, TweenConst.phase3Duration * TweenConst.fallingDurationRation)
                                        .target(targetY).ease(equationIn)))
                        .push(Timeline.createSequence()
                                .push(Tween.to(actor1, ActorAccessor.Y, TweenConst.phase4Duration * TweenConst.jumpingDurationRatio)
                                        .target(targetY + actor1Height * TweenConst.phase4JumpingRatio).ease(equationOut))
                                .push(Tween.to(actor1, ActorAccessor.Y, TweenConst.phase4Duration * TweenConst.fallingDurationRation)
                                        .target(targetY).ease(equationIn)))
                        .push(Timeline.createSequence()
                                .push(Tween.to(actor1, ActorAccessor.Y, TweenConst.phase5Duration * TweenConst.jumpingDurationRatio)
                                        .target(targetY + actor1Height * TweenConst.phase5JumpingRatio).ease(equationOut))
                                .push(Tween.to(actor1, ActorAccessor.Y, TweenConst.phase5Duration * TweenConst.fallingDurationRation)
                                        .target(targetY).ease(equationIn)))
                        .push(Timeline.createSequence()
                                .push(Tween.to(actor1, ActorAccessor.Y, TweenConst.phase6Duration * TweenConst.jumpingDurationRatio)
                                        .target(targetY + actor1Height * TweenConst.phase6JumpingRatio).ease(equationOut))
                                .push(Tween.to(actor1, ActorAccessor.Y, TweenConst.phase6Duration * TweenConst.fallingDurationRation)
                                        .target(targetY).ease(equationIn))))
                .setCallback(new TweenCallback() {
                    @Override
                    public void onEvent(int i, BaseTween<?> baseTween) {
                        if (listener != null) listener.onComplete();
                    }
                })
                .start(tweenManager);
        return animationOverallPeriod;
    }

    /** Moves by horizontal direction while rotating **/
    public float roll(Actor actor1, float targetX, final TweenListener listener) {
        float phase1Duration = Math.abs((targetX - actor1.getX())
                /(Util.random.nextInt((int)(ParamConst.SW * TweenConst.rotateHighLim)) + ParamConst.SW * TweenConst.rotateLowLim));
        TweenEquation equationIn = TweenEquations.easeInSine;

        Timeline.createSequence()
                .push(Timeline.createParallel()
                        .push(Tween.to(actor1, ActorAccessor.X, phase1Duration)
                                .target(targetX + actor1.getWidth() * TweenConst.rotatePhase1XIncrease).ease(equationIn))
                        .push(Tween.to(actor1, ActorAccessor.ROTATE_RIGHT, phase1Duration)
                                .target().ease(equationIn)))
                .push(Timeline.createParallel()
                        .push(Tween.to(actor1, ActorAccessor.X, TweenConst.rotatePhase2Duration)
                                .target(targetX).ease(equationIn))
                        .push(Tween.to(actor1, ActorAccessor.ROTATE_LEFT, TweenConst.rotatePhase2Duration)
                                .target().ease(equationIn)))
                .setCallback(new TweenCallback() {
                    @Override
                    public void onEvent(int i, BaseTween<?> baseTween) {
                        if (listener != null) listener.onComplete();
                    }
                })
                .start(tweenManager);
        return phase1Duration + TweenConst.rotatePhase2Duration;
    }
    /** Moves to the target while disappearing **/
    public float moveAndDisappear(Actor actor, float targetX, float targetY, final TweenListener listener) {
        float duration = Math.abs((targetY - actor.getY())
                / (Util.random.nextInt((int)(ParamConst.SH * TweenConst.MDHighLim)) + ParamConst.SH * TweenConst.MDLowLim));
        Timeline.createParallel()
                .push(Tween.to(actor, ActorAccessor.MOVE_CENTER_X, duration)
                        .target(targetX))
                .push(Tween.to(actor, ActorAccessor.MOVE_CENTER_Y, duration)
                        .target(targetY))
                .push(Timeline.createSequence()
                        .push(Timeline.createParallel()
                            .push(Tween.to(actor, ActorAccessor.WIDTH, duration * 0.8f)
                                    .target(actor.getWidth()))
                            .push(Tween.to(actor, ActorAccessor.HEIGHT, duration * 0.8f)
                                    .target(actor.getHeight())))
                        .push(Timeline.createParallel()
                            .push(Tween.to(actor, ActorAccessor.WIDTH, duration * 0.2f)
                                    .target(0))
                            .push(Tween.to(actor, ActorAccessor.HEIGHT, duration * 0.2f)
                                    .target(0))))
                .setCallback(new TweenCallback() {
                    @Override
                    public void onEvent(int i, BaseTween<?> baseTween) {
                        if (listener != null) listener.onComplete();
                    }
                })
                .start(tweenManager);
        return duration;
    }
    /** Moves to the target while disappearing **/
    public void moveAndSmoothDisappear(Actor actor, float startPointY, final TweenListener listener) {
        Timeline.createParallel()
                .push(Tween.to(actor, ActorAccessor.Y, TweenConst.GAME_OVER_lABEL_DURATION)
                        .target(startPointY + ParamConst.CELL_HEIGHT * 2))
                .push(Tween.to(actor, ActorAccessor.A, TweenConst.GAME_OVER_lABEL_DURATION)
                        .target(0))
                .setCallback(new TweenCallback() {
                    @Override
                    public void onEvent(int i, BaseTween<?> baseTween) {
                        if (listener != null) listener.onComplete();
                    }
                })
                .start(tweenManager);
    }
    public float disappear(Actor actor, final TweenListener listener) {
        float duration = TweenConst.disappearDuration;
        float actorX = actor.getX();
        float actorY = actor.getY();
        float randomX = (float)Util.random.nextInt((int)(actor.getWidth())) + actorX;
        float randomY = (float)Util.random.nextInt((int)(actor.getHeight())) + actorY;
        Timeline.createParallel()
                .push(Tween.to(actor, ActorAccessor.WIDTH, duration)
                        .target(0))
                .push(Tween.to(actor, ActorAccessor.HEIGHT, duration)
                        .target(0))
                .push(Tween.to(actor, ActorAccessor.X, duration)
                        .target(randomX))
                .push(Tween.to(actor, ActorAccessor.Y, duration)
                        .target(randomY))
                .setCallback(new TweenCallback() {
                    @Override
                    public void onEvent(int i, BaseTween<?> baseTween) {
                        if (listener != null) listener.onComplete();
                    }
                })
                .start(tweenManager);
        return duration;
    }
    public void smoothDisappear(Actor actor, float duration, final TweenListener listener) {
        Timeline.createParallel()
                .push(Tween.to(actor, ActorAccessor.A, duration)
                        .target(0))
                .setCallback(new TweenCallback() {
                    @Override
                    public void onEvent(int i, BaseTween<?> baseTween) {
                        if (listener != null) listener.onComplete();
                    }
                })
                .start(tweenManager);
    }
    public void smoothAppear(Actor actor, float duration, final TweenListener listener) {
        Timeline.createParallel()
                .push(Tween.to(actor, ActorAccessor.A, duration)
                        .target(1))
                .setCallback(new TweenCallback() {
                    @Override
                    public void onEvent(int i, BaseTween<?> baseTween) {
                        if (listener != null) listener.onComplete();
                    }
                })
                .start(tweenManager);
    }
    public void flush(final Actor actor, final float transparencyChangeLim, final int repeatCount, final float delayDuration, final float duration, final TweenListener listener){
        final float centerX = actor.getX(Align.center);
        final float centerY = actor.getY(Align.center);

            Timeline.createParallel()
                .push(Tween.to(actor, ActorAccessor.MOVE_CENTER_X, duration)
                        .target(centerX))
                .push(Tween.to(actor, ActorAccessor.MOVE_CENTER_Y, duration)
                        .target(centerY))
                .push(Timeline.createSequence()
                        .push(Tween.to(actor, ActorAccessor.A, duration / 2)
                                .target(actor.getColor().a * transparencyChangeLim))
                        .push(Tween.to(actor, ActorAccessor.A, duration / 2)
                                .target(1)))
                .setCallback(new TweenCallback() {
                    @Override
                    public void onEvent(int i, BaseTween<?> baseTween) {
                        if (listener != null) listener.onComplete();
                    }
                })
                .repeat(repeatCount, delayDuration)
                .start(tweenManager);
    }

    public void jumpContinously(final Actor actor, float maxY, float minY, float duration, final TweenListener listener ){
        float actorY = actor.getY();
        TweenEquation equationOut = TweenEquations.easeOutSine;
        TweenEquation equationIn = TweenEquations.easeInSine;
        float targetY = Math.abs(Util.random.nextInt((int)maxY) + minY);

        Timeline.createParallel()
                .push(Timeline.createSequence()
                        .push(Timeline.createSequence()
                                .push(Tween.to(actor, ActorAccessor.Y, TweenConst.phase2Duration * TweenConst.jumpingDurationRatio * duration)
                                        .target(targetY * TweenConst.phase2JumpingRatio).ease(equationOut))
                                .push(Tween.to(actor, ActorAccessor.Y, TweenConst.phase2Duration * TweenConst.fallingDurationRation * duration)
                                        .target(actorY).ease(equationIn)))
                        .push(Timeline.createSequence()
                                .push(Tween.to(actor, ActorAccessor.Y, TweenConst.phase3Duration * TweenConst.jumpingDurationRatio * duration)
                                        .target(targetY * TweenConst.phase3JumpingRatio).ease(equationOut))
                                .push(Tween.to(actor, ActorAccessor.Y, TweenConst.phase3Duration * TweenConst.fallingDurationRation * duration)
                                        .target(actorY).ease(equationIn)))
                        .push(Timeline.createSequence()
                                .push(Tween.to(actor, ActorAccessor.Y, TweenConst.phase4Duration * TweenConst.jumpingDurationRatio * duration)
                                        .target(targetY * TweenConst.phase4JumpingRatio).ease(equationOut))
                                .push(Tween.to(actor, ActorAccessor.Y, TweenConst.phase4Duration * TweenConst.fallingDurationRation * duration)
                                        .target(actorY).ease(equationIn)))
                        .push(Timeline.createSequence()
                                .push(Tween.to(actor, ActorAccessor.Y, TweenConst.phase5Duration * TweenConst.jumpingDurationRatio * duration)
                                        .target(targetY * TweenConst.phase5JumpingRatio).ease(equationOut))
                                .push(Tween.to(actor, ActorAccessor.Y, TweenConst.phase5Duration * TweenConst.fallingDurationRation * duration)
                                        .target(actorY).ease(equationIn)))
                        .push(Timeline.createSequence()
                                .push(Tween.to(actor, ActorAccessor.Y, TweenConst.phase6Duration * TweenConst.jumpingDurationRatio * duration)
                                        .target(targetY * TweenConst.phase6JumpingRatio).ease(equationOut))
                                .push(Tween.to(actor, ActorAccessor.Y, TweenConst.phase6Duration * TweenConst.fallingDurationRation * duration)
                                        .target(actorY).ease(equationIn))))
                .delay(Util.random.nextFloat())
                .repeat(Tween.INFINITY, Util.random.nextInt(2) / 3)
                .setCallback(new TweenCallback() {
                    @Override
                    public void onEvent(int i, BaseTween<?> baseTween) {
                        if (listener != null) listener.onComplete();
                    }
                })
                .start(tweenManager);
    }
    // endregion

    // region Ctor
    public TweenAnimation(TweenManager tweenManager) {
        this.tweenManager = tweenManager;
    }
    // endregion\


    public TweenManager getTweenManager() {
        return tweenManager;
    }
}
