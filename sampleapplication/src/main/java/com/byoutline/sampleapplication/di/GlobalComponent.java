package com.byoutline.sampleapplication.di;

import com.byoutline.sampleapplication.DialogSamples.DialogActivityExample;
import com.byoutline.sampleapplication.baseCompatAppActivityExamples.FirstFragment;
import com.byoutline.sampleapplication.datePickerExample.DatePickerSampleActivity;
import com.byoutline.sampleapplication.networkChangeExample.NetworkChangeActivity;
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