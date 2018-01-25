package com.byoutline.sampleapplication.draweractivity

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.byoutline.sampleapplication.R
import com.byoutline.sampleapplication.databinding.FragmentShowDialogBinding
import com.byoutline.secretsauce.di.AttachableViewModel
import com.byoutline.secretsauce.di.lazyViewModelWithAutoLifecycle
import java.util.concurrent.atomic.AtomicInteger

class FirstFragment : CountingFragment() {
    override fun getToolbarTitle(): String = getString(R.string.first_fragment)
}

class SecondFragment : CountingFragment() {
    override fun getToolbarTitle(): String = getString(R.string.second_fragment)
}

abstract class CountingFragment : BaseFragment() {
    val viewModel by lazyViewModelWithAutoLifecycle(CountingViewModel::class)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = FragmentShowDialogBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        return binding.root
    }
}

class CountingViewModel : AttachableViewModel<BaseFragment>() {
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