package com.byoutline.secretsauce.di;

import javax.inject.Qualifier;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author Sebastian Kacprzak <sebastian.kacprzak at byoutline.com>
 */
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
public @interface SessionId {
}
