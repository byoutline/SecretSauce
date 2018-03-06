package com.byoutline.sampleapplication.daggerexample

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.view.LayoutInflater
import dagger.Module
import dagger.Provides

/**
 * Generic wrapper to map Activity to [Context], [LayoutInflater], [Resources].
 * To avoid dagger version mismatch for now you have to copy this to your project.
 */
@Module
abstract class ContextModule {

    @Provides
    fun providesContext(activity: Activity): Context = activity

    @Provides
    fun providesLayoutInflater(activity: Activity): LayoutInflater = activity.layoutInflater

    @Provides
    fun resources(activity: Activity): Resources = activity.resources
}