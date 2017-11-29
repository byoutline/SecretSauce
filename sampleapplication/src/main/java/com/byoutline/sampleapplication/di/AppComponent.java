package com.byoutline.sampleapplication.di;

import com.byoutline.sampleapplication.networkchangereceiver.NetworkActivityModule;
import com.byoutline.secretsauce.di.ActivityInjectorApp;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjectionModule;


@Singleton
@Component(modules = {
        NetworkActivityModule.class,
        ActivitiesModule.class,
        ViewModelMapModule.class,
        ViewModelProvidersModule.class,
        AndroidInjectionModule.class
})
public interface AppComponent {

    void inject(ActivityInjectorApp o);
}