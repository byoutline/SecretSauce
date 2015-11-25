package com.byoutline.secretsauce.di;

import com.squareup.otto.Bus;

import javax.inject.Provider;

public interface AppComponentInterface {

    Bus getBus();

    @SessionId
    Provider<String> getSessionIdProvider();
}
