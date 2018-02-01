package com.byoutline.sampleapplication.di;

import com.byoutline.secretsauce.di.ActivityInjectorApp;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjectionModule;


@Singleton
@Component(modules = {
        ActivitiesModule.class,
        ViewModelMapModule.class,
        AndroidInjectionModule.class
})
public interface AppComponent {

    void inject(ActivityInjectorApp o);
}