package com.byoutline.sampleapplication.di;

import com.byoutline.sampleapplication.SampleApp;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjectionModule;


@Singleton
@Component(modules = {
        ActivitiesModule.class,
        ViewModelMapModule.class,
        AndroidInjectionModule.class,
        DependenciesThatNeedOverwriteInTestsModule.class
})
public interface AppComponent {

    void inject(SampleApp o);
}