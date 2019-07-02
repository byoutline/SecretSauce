package com.byoutline.sampleapplication

import androidx.annotation.VisibleForTesting
import com.byoutline.sampleapplication.di.AppComponent
import com.byoutline.sampleapplication.di.DaggerAppComponent
import com.byoutline.sampleapplication.networkchangereceiver.NetworkActivityModule
import com.byoutline.secretsauce.SecretSauceSettings
import com.byoutline.secretsauce.di.ActivityInjectorApp
import com.byoutline.secretsauce.di.AppInjector

/**
 * Application entry point that configures Dagger and SecretSauce.
 */
open class SampleApp : ActivityInjectorApp() {

    override fun onCreate() {
        super.onCreate()
        initDagger()
    }

    @VisibleForTesting open fun initDagger() {
        setComponents(createGlobalComponent())
    }

    private fun createGlobalComponent(): AppComponent {
        return DaggerAppComponent.builder()
            .networkActivityModule(NetworkActivityModule(this))
            .build()
    }

    @VisibleForTesting
    @Synchronized
    open fun setComponents(mainComponent: AppComponent) {
        mainComponent.inject(this)
        AppInjector.init(this)
        SecretSauceSettings.set(
            debug = BuildConfig.DEBUG,
            containerViewId = R.id.container,
            bindingViewModelId = BR.viewModel,
            viewModelFactoryProvider = { viewModelFactory }
        )
    }
}
