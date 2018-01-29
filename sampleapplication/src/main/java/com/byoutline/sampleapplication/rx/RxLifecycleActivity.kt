package com.byoutline.sampleapplication.rx

import android.databinding.ObservableLong
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.byoutline.sampleapplication.R
import com.byoutline.sampleapplication.databinding.RxlifecycleActivityBinding
import com.byoutline.secretsauce.lifecycle.AttachableViewModelRx
import com.byoutline.secretsauce.lifecycle.bindContentView
import com.byoutline.secretsauce.lifecycle.lazyViewModelWithAutoLifecycle
import java.util.concurrent.TimeUnit
import io.reactivex.Observable as RxObservable

/**
 * Auto incrementing counter produced by Rx, that survives rotation and does not leak.
 * Locking the screen/moving to other app will pause the counter.
 */
class RxLifecycleActivity : AppCompatActivity() {

    private val viewModel by lazyViewModelWithAutoLifecycle(RxLifecycleViewModel::class)

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = bindContentView<RxlifecycleActivityBinding>(R.layout.rxlifecycle_activity)
        binding.viewModel = viewModel
    }
}


class RxLifecycleViewModel : AttachableViewModelRx<Any>() {
    val value = ObservableLong()
    override fun onAttach(view: Any) {
        RxObservable.interval(1, TimeUnit.SECONDS)
                .subscribe { value.set(value.get() + 1) }
                .disposeOnDetach()
        super.onAttach(view)
    }
}