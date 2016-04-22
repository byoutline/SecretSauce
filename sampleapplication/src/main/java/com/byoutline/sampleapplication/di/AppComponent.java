package com.byoutline.sampleapplication.di;

import com.byoutline.secretsauce.di.AppComponentInterface;

import dagger.Component;

/**
 * Component that defines all instances required by application.
 * <p>Since it extends {@link AppComponentInterface} components required by
 * {@code SecretSauce} init will also be provided</p>
 */
@AppScope
@Component(modules = AppModule.class)
public interface AppComponent extends AppComponentInterface {
}