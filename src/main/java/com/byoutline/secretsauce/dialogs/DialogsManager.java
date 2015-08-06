package com.byoutline.secretsauce.dialogs;

import com.byoutline.secretsauce.Settings;
import com.byoutline.secretsauce.events.DismissDialogEvent;
import com.byoutline.secretsauce.events.InternalDismissDialogsEvent;
import com.byoutline.secretsauce.utils.LogUtils;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author Sebastian Kacprzak <nait at naitbit.com>
 */
public class DialogsManager {
    private static final String TAG = LogUtils.internalMakeLogTag(DialogsManager.class);
    private final Map<String, Boolean> visibleDialogsUids = new HashMap<>();
    /**
     * Workaround for crash due to double unregister call that occurs on L devices if screen is rotated mid
     * activity transition.
     */
    private final AtomicBoolean isRegisteredWorkaround = new AtomicBoolean(false);

    public synchronized void addVisibleDialog(String dialogUid) {
        visibleDialogsUids.put(dialogUid, true);
    }

    public synchronized void removeVisibleDialog(String dialogUid) {
        visibleDialogsUids.remove(dialogUid);
    }

    public synchronized List<String> getDialogsThatShouldBeHidden() {
        List<String> dialogsToHide = new ArrayList<>();
        for (Map.Entry<String, Boolean> entry : visibleDialogsUids.entrySet()) {
            if (!entry.getValue()) {
                dialogsToHide.add(entry.getKey());
            }
        }
        return dialogsToHide;
    }

    @Subscribe
    public synchronized void onDismissDialog(DismissDialogEvent event) {
        if (!visibleDialogsUids.containsKey(event.dialogUid)) {
            LogUtils.LOGW(TAG, "Attempted to dismiss dialog that was not visible");
        }
        visibleDialogsUids.put(event.dialogUid, false);
        postDismissDialogsUid();
    }

    public void registerAndPostEvents() {
        synchronized (isRegisteredWorkaround) {
            if (isRegisteredWorkaround.compareAndSet(false, true)) {
                Settings.BUS.register(this);
            }
        }
        postDismissDialogsUid();
    }

    public void unregister() {
        synchronized (isRegisteredWorkaround) {
            if (isRegisteredWorkaround.compareAndSet(true, false)) {
                Settings.BUS.unregister(this);
            }
        }
    }

    private void postDismissDialogsUid() {
        Settings.BUS.post(getDismissDialogsUid());
    }

    private InternalDismissDialogsEvent getDismissDialogsUid() {
        return new InternalDismissDialogsEvent(getDialogsThatShouldBeHidden());
    }
}
