package com.byoutline.sampleapplication.datepicker;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import com.byoutline.sampleapplication.R;
import com.byoutline.sampleapplication.SampleApp;
import com.byoutline.sampleapplication.databinding.ActivityDatePickerSampleBinding;
import com.byoutline.secretsauce.events.DateSetEvent;
import com.byoutline.secretsauce.fragments.DatePickerFragment;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import javax.inject.Inject;


public class DatePickerSampleActivity extends AppCompatActivity {

    public static final String TAG = "datePSampleTag";

    /*
     bus is needed to receive date from fragment
     */
    @Inject
    Bus bus;

    private ActivityDatePickerSampleBinding binding;

    private void onClick() {
        // show date picker fragment
        DatePickerFragment fragment = new DatePickerFragment();
        fragment.show(getFragmentManager(), TAG);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_date_picker_sample);
        SampleApp.Companion.getComponent().inject(this);
        binding.dateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerSampleActivity.this.onClick();
            }
        });
    }


    /*
     * receiving date from DatePickerFragment
     */
    @Subscribe
    public void receiveDateChanges(DateSetEvent event) {
        // Display received data.
        binding.dateTv.setText(event.dateString);
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
}
