package com.byoutline.sampleapplication.draweractivity

import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField
import android.os.Bundle
import android.support.v4.app.Fragment
import com.byoutline.secretsauce.activities.hideKeyboard

abstract class BaseFragment : Fragment() {
    lateinit var toolbarViewModel: ToolbarViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        toolbarViewModel = getBaseActivity().toolbarViewModel
    }

    abstract fun getToolbarTitle(): String
    override fun onResume() {
        super.onResume()
        toolbarViewModel.setTitle(getToolbarTitle())
        activity?.hideKeyboard()
    }

    private fun getBaseActivity() = activity as ExampleActivity
}

class ToolbarViewModel: ViewModel() {
    val title = ObservableField<String>()

    fun setTitle(title: String) {
        this.title.set(title)
    }
}