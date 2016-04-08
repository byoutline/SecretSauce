package com.byoutline.sampleapplication.datePickerExample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.byoutline.sampleapplication.R;
import com.byoutline.sampleapplication.SampleApp;
import com.byoutline.secretsauce.events.DateSetEvent;
import com.byoutline.secretsauce.fragments.DatePickerFragment;
import com.byoutline.secretsauce.views.CustomFontTextView;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DatePickerSampleActivity extends AppCompatActivity {

    public static final String TAG = "datePSampleTag";

    /*
     bus is needed to receive date from fragment
     */
    @Inject
    Bus bus;

    @Bind(R.id.dateTv)
    CustomFontTextView dateTv;

    @OnClick(R.id.dateBtn)
    public void onClick() {
        /*
         * show date picker fragment
         */
        DatePickerFragment fragment = new DatePickerFragment();
        fragment.show(getFragmentManager(), TAG);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_picker_sample);
        SampleApp.component.inject(this);
        ButterKnife.bind(this);
    }


    /*
     * receiving date from DatePickerFragment
     */
    @Subscribe
    public void receiveDateChanges(DateSetEvent event) {
        /*
         * set received date in TextView
         */
        dateTv.setText(event.dateString);
    }

    @Override
    protected void onResume() {
        super.onResume();
        bus.register(this);
    }

    @Override
    protected void onPause() {
        bus.unregister(this);
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        ButterKnife.unbind(this);
        super.onDestroy();
    }
}
