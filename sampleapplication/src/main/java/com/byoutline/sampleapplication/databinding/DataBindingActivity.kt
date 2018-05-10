package com.byoutline.sampleapplication.databinding

import android.databinding.Bindable
import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import android.os.Bundle
import com.byoutline.sampleapplication.BR
import com.byoutline.sampleapplication.ClassNameAsToolbarTitleActivity
import com.byoutline.sampleapplication.R
import com.byoutline.secretsauce.databinding.DataBindingObservable
import com.byoutline.secretsauce.databinding.DataBindingObservableImpl
import com.byoutline.secretsauce.databinding.bindContentView
import com.byoutline.secretsauce.lifecycle.AttachableViewModelRx
import com.byoutline.secretsauce.lifecycle.getVMWithAutoLifecycle
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import io.reactivex.Observable as RxObservable

/**
 * Auto incrementing counter produced by Rx, that survives rotation and does not leak.
 * Locking the screen/moving to other app will pause the counter.
 */
class DataBindingActivity : ClassNameAsToolbarTitleActivity() {

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindContentView<DatabindingActivityBinding>(R.layout.databinding_activity).apply {
            viewModel = getVMWithAutoLifecycle(DataBindingViewModel::class)
        }
    }
}

class DataBindingViewModel @Inject constructor(
) : AttachableViewModelRx<Any>(),
        DataBindingObservable by DataBindingObservableImpl() {
    // Assume that this value is changed externally
    private val externalObs = ExternalObs()

    // There is little reason to mix ObservableBoolean/Field and extending Observable
    // but it was done here to show that all of those work
    @get:Bindable
    var bool: Boolean by observable(false, BR.bool)
        private set
    @get:Bindable
    var field1: Long by observable(0, BR.field1)
        private set
    @get:Bindable
    var field2: String by observable("", BR.field2)
        private set

    override fun onAttach(view: Any) {
        simulateExternalChange()
        // lets assume that we have to map values before passing them to views
        // (otherwise there would be no point of duplicating observables)
        externalObs.subscribeTillDetach(BR.field1) { this@DataBindingViewModel.field1 = field1 }
        externalObs.bool.subscribeBoolTillDetach { bool = it }
        externalObs.field2.subscribeFieldTillDetach { field2 = it }
        super.onAttach(view)
    }

    private fun simulateExternalChange() {
        RxObservable.interval(1, TimeUnit.SECONDS)
                .subscribe { externalObs.increment() }
                .disposeOnDetach()
    }
}

class ExternalObs : DataBindingObservableImpl() {
    @get:Bindable
    var field1: Long by observable(5, BR.field1)
        private set
    val bool = ObservableBoolean()
    val field2 = ObservableField<String>("")

    fun increment() {
        field1 += 1
        bool.set(field1 % 2 == 0L)
        field2.set("str ${field1 - 2}")
    }
}
