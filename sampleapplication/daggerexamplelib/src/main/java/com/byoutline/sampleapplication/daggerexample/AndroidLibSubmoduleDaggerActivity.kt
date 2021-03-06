package com.byoutline.sampleapplication.daggerexample

import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.annotation.ColorInt
import androidx.fragment.app.Fragment
import androidx.core.content.ContextCompat
import com.byoutline.sampleapplication.ClassNameAsToolbarTitleActivity
import com.byoutline.sampleapplication.daggerexample.databinding.ActivityDaggerExampleMainBinding
import com.byoutline.secretsauce.activities.showFragment
import com.byoutline.secretsauce.databinding.bindContentView
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

/**
 * This class demonstrates that Dagger Android can inject Context dependency even if Activity/Module is declared in
 * different Gradle module.
 */
class AndroidLibSubmoduleDaggerActivity : ClassNameAsToolbarTitleActivity(), HasSupportFragmentInjector {
    override fun supportFragmentInjector() = fragmentInjector

    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>
    @Inject
    lateinit var colorMixer: ColorMixer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindContentView<ActivityDaggerExampleMainBinding>(R.layout.activity_dagger_example_main).apply {
            daggerExampleActivityLbl.setBackgroundColor(colorMixer.color)
            daggerExampleShowFragmentBtn.setOnClickListener {
                showFragment(AndroidLibSubmoduleDaggerFragment(), addToBackStack = false, replace = true)
            }
        }
    }
}

class DependencyA @Inject constructor()
class DependencyB @Inject constructor()
class ColorMixer @Inject constructor(context: Context, dependencyA: DependencyA, dependencyB: DependencyB) {
    @ColorInt val color = ContextCompat.getColor(context, R.color.button_material_light)
}

@Module(includes = [DaggerExampleMainActivityFragmentsModule::class])
class DaggerExampleMainActivityModule : com.byoutline.sampleapplication.daggerexample.ContextModule() {
    @Provides fun act(a: AndroidLibSubmoduleDaggerActivity): Activity = a
}

@Module
abstract class DaggerExampleMainActivityFragmentsModule {
    @ContributesAndroidInjector(modules = [FragmentContextDependencyModule::class])
    abstract fun frag(): AndroidLibSubmoduleDaggerFragment
}
