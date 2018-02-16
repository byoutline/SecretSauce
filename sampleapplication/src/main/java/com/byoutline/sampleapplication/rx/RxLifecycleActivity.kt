package com.byoutline.sampleapplication.rx

import android.databinding.ObservableLong
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.byoutline.sampleapplication.R
import com.byoutline.sampleapplication.databinding.RxlifecycleActivityBinding
import com.byoutline.secretsauce.databinding.bindContentView
import com.byoutline.secretsauce.lifecycle.AttachableViewModelRx
import com.byoutline.secretsauce.lifecycle.getVMWithAutoLifecycle
import dagger.android.AndroidInjection
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import io.reactivex.Observable as RxObservable

/**
 * Auto incrementing counter produced by Rx, that survives rotation and does not leak.
 * Locking the screen/moving to other app will pause the counter.
 */
class RxLifecycleActivity : AppCompatActivity() {

    @Inject
    lateinit var tmp: Tmp

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindContentView<RxlifecycleActivityBinding>(R.layout.rxlifecycle_activity).apply {
            viewModel = getVMWithAutoLifecycle(RxLifecycleViewModel::class)
        }
        AndroidInjection.inject(this)
    }
}
class Tmp @Inject  constructor(context: RxLifecycleActivity) {
    init {
        print(context.resources.getString(R.string.abc_capital_off))
    }
}

class RxLifecycleViewModel @Inject constructor() : AttachableViewModelRx<Any>() {

    val value = ObservableLong()
    override fun onAttach(view: Any) {
        RxObservable.interval(1, TimeUnit.SECONDS)
                .subscribe { value.set(value.get() + 1) }
                .disposeOnDetach()
        super.onAttach(view)
    }
}