package com.byoutline.sampleapplication.rx

import android.databinding.ObservableLong
import android.os.Bundle
import com.byoutline.sampleapplication.ClassNameAsToolbarTitleActivity
import com.byoutline.sampleapplication.R
import com.byoutline.sampleapplication.databinding.RxlifecycleActivityBinding
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
class RxLifecycleActivity : ClassNameAsToolbarTitleActivity() {

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindContentView<RxlifecycleActivityBinding>(R.layout.rxlifecycle_activity).apply {
            viewModel = getVMWithAutoLifecycle(RxLifecycleVM::class)
        }
    }
}

open class RxLifecycleVM @Inject constructor() : AttachableViewModelRx<Any>() {

    val value = ObservableLong()
    override fun onAttach(view: Any) {
        RxObservable.interval(1, TimeUnit.SECONDS)
            .subscribe { value.set(value.get() + 1) }
            .disposeOnDetach()
        super.onAttach(view)
    }
}