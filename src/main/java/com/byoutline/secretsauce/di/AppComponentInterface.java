package com.byoutline.secretsauce.di;

import android.support.annotation.IdRes;
import com.squareup.otto.Bus;

import javax.inject.Provider;

public interface AppComponentInterface {

    Bus getBus();

    @SessionId
    Provider<String> getSessionIdProvider();

    @ContainerId
    @IdRes
    int getContainerId();

    @DefaultFontName
    String getDefaultFontName();
}
