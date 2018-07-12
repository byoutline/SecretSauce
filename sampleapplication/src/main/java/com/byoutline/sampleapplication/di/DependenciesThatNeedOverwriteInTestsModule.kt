package com.byoutline.sampleapplication.di

import android.support.v7.app.AppCompatActivity
import com.byoutline.sampleapplication.MainActivityItem
import com.byoutline.sampleapplication.MainActivityItems
import com.byoutline.sampleapplication.contextdependencies.ContextDependenciesActivity
import com.byoutline.sampleapplication.customfontviews.CustomFontViewsActivity
import com.byoutline.sampleapplication.databinding.DataBindingActivity
import com.byoutline.sampleapplication.draweractivity.DrawerActivity
import com.byoutline.sampleapplication.networkchangereceiver.NetworkChangeActivity
import com.byoutline.sampleapplication.roundedimageview.DrawableActivityExample
import com.byoutline.sampleapplication.rx.RxLifecycleActivity
import com.byoutline.sampleapplication.waitlayout.WaitActivity
import dagger.Module
import dagger.Provides
import dagger.Reusable
import kotlin.reflect.KClass

@Module
class DependenciesThatNeedOverwriteInTestsModule {
    @Provides @MainActivityItems @Reusable
    fun examples() = listOf(
        i(DrawerActivity::class, "Drawer with fragments"),
        i(NetworkChangeActivity::class, "Network change observer"),
        i(DrawableActivityExample::class, "Rounded drawables"),
        i(CustomFontViewsActivity::class, "Custom text fonts"),
        i(WaitActivity::class, "Work in progress indicators"),
        i(RxLifecycleActivity::class, "AttachableViewModelRx: RxJava+ViewModel+Android lifecycle"),
        i(DataBindingActivity::class, "Android Databinding+ViewModel+Android lifecycle"),
        i(
            ContextDependenciesActivity::class,
            "Dagger Android: Injecting dependencies that require activity context into activities and fragments"
        )
    )

    private fun i(clazz: KClass<out AppCompatActivity>, displayName: String) = MainActivityItem(clazz.java, displayName)
}
