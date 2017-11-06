package com.byoutline.sampleapplication.di;

import com.byoutline.sampleapplication.draweractivity.ExampleActivity;
import com.byoutline.sampleapplication.networkchangereceiver.NetworkChangeActivity;
import dagger.Component;


@GlobalScope
@Component(modules = GlobalModule.class)
public interface GlobalComponent {

    void inject(NetworkChangeActivity networkChangeActivity);

    void inject(ExampleActivity o);
}