package com.byoutline.sampleapplication.contextdependencies

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.support.annotation.ColorInt
import android.support.v7.app.AppCompatActivity
import android.util.TypedValue
import com.byoutline.sampleapplication.R
import com.byoutline.sampleapplication.databinding.ActivityContextDependencies2Binding
import com.byoutline.sampleapplication.databinding.ActivityContextDependenciesBinding
import com.byoutline.sampleapplication.di.ContextModule
import com.byoutline.secretsauce.databinding.bindContentView
import dagger.Module
import dagger.Provides
import dagger.android.AndroidInjection
import javax.inject.Inject
import io.reactivex.Observable as RxObservable

/**
 * This activity has dependencies that require some [Context] scoped data.
 */
class ContextDependenciesActivity : AppCompatActivity() {

    @Inject
    lateinit var contextDependency: ContextDependency
    @Inject
    lateinit var resourceDependency: ResourceDependency

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityContextDependenciesBinding = bindContentView(R.layout.activity_context_dependencies)
        AndroidInjection.inject(this)

        binding.contextDependenciesTv.setBackgroundColor(contextDependency.color)
        binding.contextDependenciesTv.text = resourceDependency.string
        binding.contextDependenciesBtn.setOnClickListener {
            startActivity(Intent(this, ContextDependenciesActivity2::class.java))
        }
    }
}

/**
 * Example of same activity with different theme. By checking that different color was loaded you can verify that
 * correct context was provided.
 */
class ContextDependenciesActivity2 : AppCompatActivity() {

    @Inject
    lateinit var contextDependency: ContextDependency

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityContextDependencies2Binding = bindContentView(R.layout.activity_context_dependencies2)
        AndroidInjection.inject(this)
        binding.contextDependenciesTv.setBackgroundColor(contextDependency.color)
    }
}

class ContextDependency @Inject constructor(context: Context) {
    @ColorInt val color: Int

    init {
        val typedValue = TypedValue()
        context.theme.resolveAttribute(R.attr.colorPrimary, typedValue, true)
        color = typedValue.data
    }
}

class ResourceDependency @Inject constructor(resources: Resources) {
    val string = resources.getString(R.string.context_dependencies_str)
}


/**
 * Let the Android Dagger know that it can use [ContextDependenciesActivity] as regular activity.
 * [dagger.android.ContributesAndroidInjector] should enforce that this binding is only visible during valid lifecycle.
 * This module is not abstract, because [ContextModule] cannot be abstract.
 */
@Module
class ContextModuleDependenciesActivity : ContextModule() {
    @Provides fun act(activity: ContextDependenciesActivity): Activity = activity
}

@Module
class ContextModuleDependenciesActivity2 : ContextModule() {
    @Provides fun act(activity: ContextDependenciesActivity2): Activity = activity
}