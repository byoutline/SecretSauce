package com.byoutline.sampleapplication.daggerexample

import android.arch.lifecycle.ViewModel
import android.content.res.Resources
import android.databinding.ObservableInt
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.byoutline.sampleapplication.daggerexample.databinding.FragmentDaggerExampleBinding
import com.byoutline.secretsauce.databinding.inflateAndSetVM
import com.byoutline.secretsauce.di.Injectable
import com.byoutline.secretsauce.di.ViewModelKey
import com.byoutline.secretsauce.lifecycle.AttachableViewModel
import com.byoutline.secretsauce.lifecycle.getViewModelWithAutoLifecycle
import com.byoutline.submodulelib.DaggerJavaLibModule
import com.byoutline.submodulelib.JavaLibDependencyInterface
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import javax.inject.Inject
import javax.inject.Qualifier

/**
 * Example how to configure Fragment to work with Dagger Android injection and with [ViewModel]
 */
class AndroidLibSubmoduleDaggerFragment : Fragment(), Injectable {

    /**
     *  When using generic types like [String] it is useful to specify [Qualifier] to ensure that
     *  correct dependency will be Injected. In Kotlin use `@field:AnnotationName` instead of just `@AnnotationName`
     */
    @Inject @field:DaggerExampleFragmentLabelText
    lateinit var labelText: String

    @Inject
    lateinit var dependencyFromPureJavaModule: JavaLibDependencyInterface

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate layout using Android DataBinding and pass it viewModel
        return inflateAndSetVM<FragmentDaggerExampleBinding>(
            inflater, container, R.layout.fragment_dagger_example,
            getViewModelWithAutoLifecycle(DaggerExampleFragmentVM::class)
        ).apply {
            // Although rarely needed when using viewModel you can also manually use @Inject
            // and set values by hand
            fragmentLabel.text = labelText
            fragmentPureJavaTv.text =
                getString(R.string.fragment_pure_java_dep, dependencyFromPureJavaModule.javaClass.simpleName)
        }.root
    }
}

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
internal annotation class DaggerExampleFragmentLabelText

@Module
abstract class DaggerExampleFragmentModule {
    @Binds @IntoMap
    @ViewModelKey(DaggerExampleFragmentVM::class)
    abstract fun vm(vm: DaggerExampleFragmentVM): ViewModel
}

class DaggerExampleFragmentVM @Inject constructor(
    val counter: Counter
) : AttachableViewModel<Any>() {

    override fun onAttach(view: Any) {
        super.onAttach(view)
        counter.inc()
    }
}

class Counter @Inject constructor() : ObservableInt(0) {
    fun inc() {
        set(get() + 1)
    }
}

@Module(includes = [DaggerJavaLibModule::class])
class FragmentContextDependencyModule {
    @Provides @DaggerExampleFragmentLabelText
    fun text(resources: Resources): String = resources.getString(R.string.fragment_label)
}

