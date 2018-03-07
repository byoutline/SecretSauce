package com.byoutline.sampleapplication.waitlayout

import android.databinding.BindingAdapter
import android.databinding.DataBindingUtil
import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import android.os.Bundle
import com.byoutline.observablecachedfield.ObservableCachedFieldBuilder
import com.byoutline.observablecachedfield.ObservableCachedFieldWithArg
import com.byoutline.sampleapplication.ClassNameAsToolbarTitleActivity
import com.byoutline.sampleapplication.R
import com.byoutline.sampleapplication.databinding.ActivityWaitLayoutBinding
import com.byoutline.secretsauce.views.WaitLayout


class WaitActivity : ClassNameAsToolbarTitleActivity() {
    private val viewModel = WaitActivityViewModel()
    private lateinit var binding: ActivityWaitLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_wait_layout)
        binding.viewModel = viewModel
    }

    override fun onResume() {
        super.onResume()
        with(viewModel) {
            binding.waitLayout1Wl.showProgressOf(sleepField1.field)
            binding.waitLayout2Wl.showProgressOf(sleepField2.field)
            binding.waitLayoutCombinedWl.showProgressOf(sleepField1.field,
                    sleepField2.field)

        }
    }

    override fun onPause() {
        with(binding) {
            waitLayout1Wl.stopTrackingProgress()
            waitLayout2Wl.stopTrackingProgress()
            waitLayoutCombinedWl.stopTrackingProgress()
        }
        super.onPause()
    }
}

internal class WaitActivityViewModel {
    val sleepField1 = SleepField("1")
    val sleepField2 = SleepField("2")
    val obsBool = ObservableBoolean(false)
}

internal class SleepField(workerName: String) {
    val field: ObservableCachedFieldWithArg<String, Int> = sleepCachedField(workerName)
    val text: ObservableField<String> = field.observable()
    fun onClick(sleepTime: Int) {
        field.refresh(sleepTime)
    }
}

private fun sleepCachedField(workerName: String): ObservableCachedFieldWithArg<String, Int> = ObservableCachedFieldBuilder()
        .withValueProviderWithArg { arg: Int ->
            Thread.sleep(arg.toLong())
            return@withValueProviderWithArg "Worker: $workerName has slept $arg ms."
        }
        .build()


@BindingAdapter("showProgress")
fun showProgress(view: WaitLayout, show: Boolean) {
    if (show) {
        view.showLoadingBar()
    } else {
        view.hideLoadingBar()
    }
}