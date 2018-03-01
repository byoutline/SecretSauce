package com.byoutline.sampleapplication.draweractivity

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.byoutline.sampleapplication.R
import com.byoutline.sampleapplication.databinding.FragmentShowDialogBinding
import com.byoutline.secretsauce.databinding.inflateAndSetVM
import com.byoutline.secretsauce.lifecycle.AttachableViewModel
import com.byoutline.secretsauce.lifecycle.getVMWithAutoLifecycle
import java.util.concurrent.atomic.AtomicInteger
import javax.inject.Inject

class FirstFragment : CountingFragment() {
    override fun getToolbarTitle(): String = getString(R.string.first_fragment)
}

class SecondFragment : CountingFragment() {
    override fun getToolbarTitle(): String = getString(R.string.second_fragment)
}

abstract class CountingFragment : BaseFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflateAndSetVM<FragmentShowDialogBinding>(inflater, container, R.layout.fragment_show_dialog,
                    viewModel = getVMWithAutoLifecycle(CountingViewModel::class, useFragmentViewModelProvider = false)
            ).root
}

class CountingViewModel @Inject constructor() : AttachableViewModel<BaseFragment>() {
    private val activityScoped = AtomicInteger(0)
    private val viewScoped = AtomicInteger(0)

    override fun onAttach(view: BaseFragment) {
        super.onAttach(view)
        viewScoped.set(0)
    }

    fun onClick() {
        val v = view ?: return
        val ctx = v.context ?: return
        val activityClicks = activityScoped.incrementAndGet()
        val viewClicks = viewScoped.incrementAndGet()
        AlertDialog.Builder(ctx).apply {
            setMessage("${v.getToolbarTitle()}\n" +
                    "Activity scoped clicks: $activityClicks\n" +
                    "View scoped clicks: $viewClicks")
            setPositiveButton("OK", null)
            show()
        }
    }
}