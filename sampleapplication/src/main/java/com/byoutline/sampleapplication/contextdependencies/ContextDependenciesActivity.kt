package com.byoutline.sampleapplication.contextdependencies

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.support.annotation.ColorInt
import android.util.TypedValue
import com.byoutline.sampleapplication.ClassNameAsToolbarTitleActivity
import com.byoutline.sampleapplication.R
import com.byoutline.sampleapplication.daggerexample.AndroidLibSubmoduleDaggerActivity
import com.byoutline.sampleapplication.daggerexample.ContextModule
import com.byoutline.sampleapplication.databinding.ActivityContextDependencies2Binding
import com.byoutline.sampleapplication.databinding.ActivityContextDependenciesBinding
import com.byoutline.secretsauce.databinding.bindContentView
import com.byoutline.secretsauce.di.Injectable
import dagger.Module
import dagger.Provides
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject
import io.reactivex.Observable as RxObservable

/**
 * This activity has dependencies that require some [Context] scoped data.
 */
class ContextDependenciesActivity : ClassNameAsToolbarTitleActivity(), HasActivityInjector, Injectable {

    @Inject
    lateinit var contextDependency: ContextDependency
    @Inject
    lateinit var resourceDependency: ResourceDependency
    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>

    override fun activityInjector() = activityInjector

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityContextDependenciesBinding = bindContentView(R.layout.activity_context_dependencies)

        binding.contextDependenciesTv.setBackgroundColor(contextDependency.color)
        binding.contextDependenciesTv.text = resourceDependency.string
        binding.contextDependenciesBtn.setOnClickListener {
            startActivity(Intent(this, ContextDependenciesActivity2::class.java))
        }
        binding.submoduleBtn.setOnClickListener {
            startActivity(Intent(this, AndroidLibSubmoduleDaggerActivity::class.java))
        }
    }
}

/**
 * Example of same activity with different theme. By checking that different color was loaded you can verify that
 * correct context was provided.
 */
class ContextDependenciesActivity2 : ClassNameAsToolbarTitleActivity(), Injectable {

    @Inject
    lateinit var contextDependency: ContextDependency

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityContextDependencies2Binding = bindContentView(R.layout.activity_context_dependencies2)
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
    val string: String = resources.getString(R.string.context_dependencies_str)
}

/**
 * Let the Android Dagger know that it can use [ContextDependenciesActivity] as regular activity.
 * [dagger.android.ContributesAndroidInjector] should enforce that this binding is only visible during valid lifecycle.
 * This module is not abstract, because [ContextModule] cannot be abstract.
 */
@Module
class ContextModuleDependenciesActivityModule : ContextModule() {
    @Provides fun act(a: ContextDependenciesActivity): Activity = a
}

@Module
class ContextModuleDependenciesActivity2Module : ContextModule() {
    @Provides fun act(a: ContextDependenciesActivity2): Activity = a
}
