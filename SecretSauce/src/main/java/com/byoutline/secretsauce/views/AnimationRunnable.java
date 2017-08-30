package com.byoutline.secretsauce.views;

import android.content.Context;
import android.os.Handler;

import javax.annotation.Nonnull;

/**
 * Runnable for animating transition between two int states.
 * Inheriting classes should take care of reference to animated component
 * (not leaking it, not using old reference, maybe require current reference during {@link #reset(int, int)} )
 */
public abstract class AnimationRunnable implements Runnable {
    public static final int DEFAULT_FRAME_COUNT = 30;
    public static final long DEFAULT_DELAY_BETWEEN_STEPS_IN_MS = 16;

    private final Handler handler;
    private int totalFrameCount;
    private int currentValue;
    private int step;
    private int endValue;
    private int currentFrame;
    private boolean interrupted;
    private long delayBetweenSteps;

    /**
     * Default constructor that creates animation running on each UI frame with new interpolated value.
     *
     * @param context    needed to invoke animation in ui thread.
     * @param startValue state from which animation should begin.
     * @param endValue   state at which animation will end.
     */
    protected AnimationRunnable(@Nonnull Context context, int startValue, int endValue) {
        this(context, DEFAULT_FRAME_COUNT, DEFAULT_DELAY_BETWEEN_STEPS_IN_MS,
                startValue, interpolateStep(startValue, endValue, DEFAULT_FRAME_COUNT), endValue
        );
    }

    /**
     * Advanced constructor that allows for creation of endless animation.
     *
     * @param context           needed to invoke animation in ui thread.
     * @param totalFrameCount   how many times steps should be invoked.
     * @param delayBetweenSteps period between animation steps.
     * @param startValue        Current state of component. Animation will display in first step this value + step.
     * @param step              step by which value change on each animation step.
     * @param endValue          state at which animation will end.
     *                          *
     * @author Sebastian Kacprzak <nait at naitbit.com> on 06.06.14
     */
    protected AnimationRunnable(@Nonnull Context context, int totalFrameCount, long delayBetweenSteps,
                                int startValue, int step, int endValue) {
        this(new Handler(context.getMainLooper()), totalFrameCount,
                delayBetweenSteps, startValue, step, endValue);
    }

    protected AnimationRunnable(@Nonnull Handler handler, int totalFrameCount, long delayBetweenSteps,
                                int startValue, int step, int endValue) {
        this.handler = handler;
        this.delayBetweenSteps = delayBetweenSteps;

        reset(totalFrameCount, startValue, step, endValue);
    }

    protected static int interpolateStep(int startValue, int endValue, int animationFrames) {
        return (endValue - startValue) / animationFrames;
    }

    /**
     * Cancels animation(if it is running) and sets new values to animate.
     * Remember to call {@link #start()} afterwards.
     */
    public synchronized void reset(int newStartValue, int newEndValue) {
        reset(totalFrameCount, newStartValue, interpolateStep(newStartValue, newEndValue, totalFrameCount), newEndValue);
    }

    public synchronized void reset(int newTotalFrameCount, int newStartValue, int newStep, int newEndValue) {
        handler.removeCallbacks(this);
        this.totalFrameCount = newTotalFrameCount;
        this.currentFrame = 0;
        this.currentValue = newStartValue;
        this.step = newStep;
        this.endValue = newEndValue;
    }

    @Override
    public synchronized void run() {
        if (interrupted) {
            handler.removeCallbacks(this);
            return;
        }
        currentFrame++;
        currentValue += step;
//        LogUtils.LOGW("DEBUG_ANIMATION", "val: " + currentValue + " frame:" + currentFrame);
        if (currentFrame < totalFrameCount) {
            onStep(currentValue);
            handler.postDelayed(this, delayBetweenSteps);
        } else {
            end();
        }
    }

    /**
     * Start animating.
     */
    public synchronized void start() {
        handler.post(this);
    }

    /**
     * Force animation to end. Last frame of animation will be queued so end value can be set.
     * If called from ui thread value will be set before this method returns.
     */
    public synchronized void end() {
        currentFrame = totalFrameCount;
        handler.removeCallbacks(this);
        onAnimationEnd(endValue);
    }

    /**
     * Cancels animation. No more steps or end will be called (which can cause leaving component
     * in mid animation state).
     */
    public synchronized void interrupt() {
        interrupted = true;
    }

    public synchronized boolean isInterrupted() {
        return interrupted;
    }

    /**
     * Starts animations from beginning. If called from {@link #onAnimationEnd(int)} will cause
     * animation to loop. In that case use {@link #interrupt()} to stop it.
     */
    public synchronized void startOver(int newTotalFrameCount, int newStartValue, int newStep, int newEndValue) {
        reset(newTotalFrameCount, newStartValue, newStep, newEndValue);
        start();
    }

    public synchronized boolean hasEnded() {
        return currentFrame >= totalFrameCount;
    }

    public synchronized int getEndValue() {
        return endValue;
    }

    /**
     * Handle displaying intermediate value. This method will be called in ui thread.
     *
     * @param value value that should be displayed during this step.
     */
    public abstract void onStep(int value);

    /**
     * Display value in its end state. This method will be called as last frame of animation
     * instead of calling {@link #onStep(int)}.
     * Usually it should contain same code that would be invoked if you would want to display
     * value without animation, but may also provide cleanup after intermediate animation steps
     * (like resetting component alpha or position).
     * This method will be called in ui thread.
     *
     * @param endValue final value.
     */
    public abstract void onAnimationEnd(int endValue);
}
