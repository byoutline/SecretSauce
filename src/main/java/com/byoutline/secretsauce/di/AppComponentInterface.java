package com.byoutline.secretsauce.di;

import com.byoutline.secretsauce.BaseApp;
import com.squareup.otto.Bus;

import javax.inject.Named;
import javax.inject.Provider;

public interface AppComponentInterface {

    Bus getBus();

    @Named(BaseApp.INJECT_NAME_SESSION_ID)
    Provider<String> getSessionIdProvider();
}
