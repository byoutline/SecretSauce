package com.byoutline.secretsauce.di;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * @author Sebastian Kacprzak <sebastian.kacprzak at byoutline.com>
 */
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
public @interface SessionId {
}
