package com.byoutline.sampleapplication.di;

import com.byoutline.secretsauce.di.AppComponentInterface;

import dagger.Component;

/**
 * Created by michalp on 12.04.16.
 */

@AppScope
@Component(modules = AppModule.class)
public interface AppComponent extends AppComponentInterface {
}