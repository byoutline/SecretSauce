package com.byoutline.secretsauce.events;

/**
 * @author Sebastian Kacprzak <nait at naitbit.com>
 */
public class InternetStateChangedEvent {
    public final boolean isOrWillBeEnabled;

    public InternetStateChangedEvent(boolean isOrWillBeEnabled) {
        this.isOrWillBeEnabled = isOrWillBeEnabled;
    }
}
