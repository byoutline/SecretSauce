package com.byoutline.secretsauce.events;

/**
 * Event for displaying or hiding dialogs.
 *
 * @author Sebastian Kacprzak <nait at naitbit.com> on 17.06.14.
 */
@Deprecated
public abstract class DialogEvent {
    public final String dialogUid;
    public final boolean showDialog;

    /**
     * @param dialogUid  unique id of dialog
     * @param showDialog true if dialog should be shown, false if it should be dismissed.
     */
    protected DialogEvent(String dialogUid, boolean showDialog) {
        this.dialogUid = dialogUid;
        this.showDialog = showDialog;
    }
}
