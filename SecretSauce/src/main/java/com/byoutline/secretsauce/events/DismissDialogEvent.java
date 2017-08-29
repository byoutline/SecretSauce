package com.byoutline.secretsauce.events;

/**
 * @author Sebastian Kacprzak <nait at naitbit.com> on 17.06.14.
 */
public class DismissDialogEvent extends DialogEvent {
    public DismissDialogEvent(String dialogUid) {
        super(dialogUid, false);
    }
}
