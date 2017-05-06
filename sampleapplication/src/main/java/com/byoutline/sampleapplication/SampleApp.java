package com.byoutline.sampleapplication;

import android.support.annotation.VisibleForTesting;

import com.byoutline.sampleapplication.di.AppComponent;
import com.byoutline.sampleapplication.di.AppModule;
import com.byoutline.sampleapplication.di.DaggerAppComponent;
import com.byoutline.sampleapplication.di.DaggerGlobalComponent;
import com.byoutline.sampleapplication.di.GlobalComponent;
import com.byoutline.sampleapplication.di.GlobalModule;
import com.byoutline.secretsauce.BaseApp;
import com.squareup.otto.Bus;

/**
 * Application entry point that configures Dagger and SecretSauce.
 */
public class SampleApp extends BaseApp {
    /**
     * Although unusually public static variable is discouraged we keep it,
     * since calls to component look much better as
     * <code>SampleApp.component.inject(this)</code>
     * <p>than alternative:
     * <code>SampleApp.getInstance(context).getComponent().inject(this)</code></p>
     */
    public static GlobalComponent component;


    @Override
    public void onCreate() {
        super.onCreate();
        resetComponents();
    }

    @Override
    protected boolean isDebug() {
        return BuildConfig.DEBUG;
    }

    @VisibleForTesting
    public void resetComponents() {
        AppComponent appComponent = createAppComponent();
        GlobalComponent mainComponent = createGlobalComponent(appComponent.getBus());
        setComponents(mainComponent, appComponent);
    }

    private GlobalComponent createGlobalComponent(Bus bus) {
        return DaggerGlobalComponent.builder()
                .globalModule(new GlobalModule(this, bus))
                .build();
    }

    private AppComponent createAppComponent() {
        return DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    @VisibleForTesting
    public synchronized void setComponents(GlobalComponent mainComponent, AppComponent appComponent) {
        component = mainComponent;
        init(appComponent);
    }
}
