package com.byoutline.sampleapplication.di;

import com.byoutline.sampleapplication.datepicker.DatePickerSampleActivity;
import com.byoutline.sampleapplication.draweractivity.ExampleActivity;
import com.byoutline.sampleapplication.networkchangereceiver.NetworkChangeActivity;
import com.squareup.otto.Bus;
import dagger.Component;


@GlobalScope
@Component(modules = GlobalModule.class)
public interface GlobalComponent {

    Bus getBus();

    void inject(NetworkChangeActivity networkChangeActivity);

    void inject(DatePickerSampleActivity datePickerSampleActivity);

    void inject(ExampleActivity o);
}