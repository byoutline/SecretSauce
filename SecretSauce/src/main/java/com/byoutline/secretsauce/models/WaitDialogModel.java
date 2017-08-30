package com.byoutline.secretsauce.models;

import com.byoutline.secretsauce.Settings;
import com.byoutline.secretsauce.events.ChangeWaitFragmentStateEvent;
import com.byoutline.secretsauce.events.HideWaitFragmentEvent;
import com.byoutline.secretsauce.events.ShowWaitFragmentEvent;
import com.byoutline.secretsauce.utils.LogUtils;
import com.squareup.otto.Subscribe;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @deprecated Use {@link com.byoutline.secretsauce.views.WaitLayout} instead.
 */
@Deprecated
public class WaitDialogModel {
    private final AtomicInteger count = new AtomicInteger(0);

    public WaitDialogModel() {
        Settings.BUS.register(this);
    }

    public boolean shouldBeVisible() {
        return shouldBeVisible(count.get());
    }

    private boolean shouldBeVisible(int c) {
//        LogUtils.LOGD("WAIT", "shouldBeVisible " + c);
        return c > 0;
    }

    @Subscribe
    public void onEventMainThread(ShowWaitFragmentEvent event) {
        int c = count.incrementAndGet();
//        LogUtils.LOGD("WAIT", "show " + c);
        if (Settings.DEBUG && c < 1) {
            throw new AssertionError("Wait dialog count was below zero. State after increment: " + c);
        }
        postState(c);
    }

    @Subscribe
    public void onEventMainThread(HideWaitFragmentEvent event) {
        int c = count.decrementAndGet();
//        LogUtils.LOGD("WAIT", "hide " + c);
        if (Settings.DEBUG && c < 0) {
            throw new AssertionError("Wait dialog count was decremented below zero");
        }
        postState(c);
    }

    private void postState(int c) {
        LogUtils.LOGV("WAIT", "post  " + c);
        Settings.BUS.post(new ChangeWaitFragmentStateEvent(shouldBeVisible(c)));
    }
}
