package com.byoutline.secretsauce.events;

/**
 * @author Sebastian Kacprzak <nait at naitbit.com>
 */
@Deprecated
public class ChangeWaitFragmentStateEvent {
    public final boolean shouldBeVisible;

    public ChangeWaitFragmentStateEvent(boolean shouldBeVisible) {
        this.shouldBeVisible = shouldBeVisible;
    }
}
