package com.byoutline.sampleapplication.di;

import com.byoutline.sampleapplication.baseappcompatactivity.FirstFragment;
import com.byoutline.sampleapplication.datepicker.DatePickerSampleActivity;
import com.byoutline.sampleapplication.dialogactivity.DialogActivityExample;
import com.byoutline.sampleapplication.networkchangereceiver.NetworkChangeActivity;
import com.squareup.otto.Bus;
import dagger.Component;


@GlobalScope
@Component(modules = GlobalModule.class)
public interface GlobalComponent {

    Bus getBus();

    void inject(FirstFragment firstFragment);

    void inject(NetworkChangeActivity networkChangeActivity);

    void inject(DatePickerSampleActivity datePickerSampleActivity);

    void inject(DialogActivityExample dialogActivityExample);
}