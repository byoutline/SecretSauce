package com.byoutline.sampleapplication

import android.app.Application
import android.support.annotation.VisibleForTesting
import com.byoutline.sampleapplication.di.DaggerGlobalComponent

import com.byoutline.sampleapplication.di.GlobalComponent
import com.byoutline.sampleapplication.di.GlobalModule
import com.byoutline.secretsauce.Settings

/**
 * Application entry point that configures Dagger and SecretSauce.
 */
class SampleApp : Application() {

    override fun onCreate() {
        super.onCreate()
        resetComponents()
    }

    @VisibleForTesting
    fun resetComponents() {
        val mainComponent = createGlobalComponent()
        setComponents(mainComponent)
    }

    private fun createGlobalComponent(): GlobalComponent {
        return DaggerGlobalComponent.builder()
                .globalModule(GlobalModule(this))
                .build()
    }

    @VisibleForTesting
    @Synchronized
    fun setComponents(mainComponent: GlobalComponent) {
        component = mainComponent
        Settings.set(context = this, debug = BuildConfig.DEBUG,
                containerViewId = R.id.container)
    }

    companion object {
        /**
         * Although unusually public static variable is discouraged we keep it,
         * since calls to component look much better as
         * `SampleApp.component.inject(this)`
         *
         * than alternative:
         * `SampleApp.getInstance(context).getComponent().inject(this)`
         */
        lateinit var component: GlobalComponent
    }
}
