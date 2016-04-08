package com.byoutline.sampleapplication.di;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by michalp on 12.04.16.
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface AppScope {
}
