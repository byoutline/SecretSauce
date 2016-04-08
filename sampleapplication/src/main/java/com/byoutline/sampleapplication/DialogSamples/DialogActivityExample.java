package com.byoutline.sampleapplication.DialogSamples;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.byoutline.sampleapplication.R;
import com.byoutline.sampleapplication.SampleApp;
import com.byoutline.secretsauce.activities.BaseAppCompatActivity;
import com.byoutline.secretsauce.events.HideWaitFragmentEvent;
import com.byoutline.secretsauce.events.ShowWaitFragmentEvent;
import com.byoutline.secretsauce.utils.WaitDialogHandler;
import com.squareup.otto.Bus;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DialogActivityExample extends BaseAppCompatActivity {

    private static final String UID = "dialogUID123";

    @Inject
    Bus bus;

    @Bind(R.id.waitDialogBtn)
    Button waitDialogBtn;

    /**
     * WaitDialogHandler provides support for WaitFragment.
     */
    private WaitDialogHandler waitDialogHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_activity_example);
        SampleApp.component.inject(this);
        ButterKnife.bind(this);
        waitDialogHandler = new WaitDialogHandler(this, getSupportFragmentManager());
    }

    @Override
    protected void onResume() {
        super.onResume();
        waitDialogHandler.onResume();
        bus.register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        waitDialogHandler.onPause();
        bus.unregister(this);
    }

    @OnClick(R.id.waitDialogBtn)
    public void onClick(View view) {
     showWaitDialog();
    }

    private void showWaitDialog() {
        /**
         * ShowWaitDialogEvent show dialog fragment to the user by proper event
         */
        bus.post(new ShowWaitFragmentEvent());
        /**
         * betwen post ShowWaitFragmentEvent and HideWaitFragmentEvent
         * wait dialog will be visible to the user
         *
         * to simulate some work, we use postDelayed() on button,
         * the dialog dismisses after 4 seconds
         */
        waitDialogBtn.postDelayed(new Runnable() {
            @Override
            public void run() {
                bus.post(new HideWaitFragmentEvent());
            }
        }, 4000);
    }
}
