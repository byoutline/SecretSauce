package com.byoutline.secretsauce.events;

import java.util.List;

/**
 * @author Sebastian Kacprzak <nait at naitbit.com>
 */
public class InternalDismissDialogsEvent {
    public final List<String> dialogUids;

    public InternalDismissDialogsEvent(List<String> dialogUids) {
        this.dialogUids = dialogUids;
    }
}
