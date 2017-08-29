package com.byoutline.sampleapplication.di;

import javax.inject.Scope;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by michalp on 12.04.16.
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface AppScope {
}
