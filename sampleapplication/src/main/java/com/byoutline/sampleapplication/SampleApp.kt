package com.byoutline.sampleapplication

import android.app.Application
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModelProvider
import com.byoutline.sampleapplication.di.AppComponent
import com.byoutline.sampleapplication.di.DaggerAppComponent
import com.byoutline.sampleapplication.networkchangereceiver.NetworkActivityModule
import com.byoutline.secretsauce.SecretSauceSettings
import com.byoutline.secretsauce.di.AppInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

/**
 * Application entry point that configures Dagger and SecretSauce.
 */
open class SampleApp : Application(), HasAndroidInjector {

    @Inject lateinit var _androidInjector: DispatchingAndroidInjector<Any>
    override fun androidInjector() = _androidInjector
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

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
