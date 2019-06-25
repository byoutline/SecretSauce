package com.byoutline.sampleapplication.draweractivity

import androidx.lifecycle.ViewModel
import androidx.databinding.ObservableField
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.byoutline.secretsauce.activities.hideKeyboard
import javax.inject.Inject

/**
 * Fragments that sets Toolbar name and hides keyboard on exit.
 */
abstract class BaseFragment : Fragment() {
    lateinit var toolbarViewModel: ToolbarViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        toolbarViewModel = getBaseActivity().toolbarViewModel
    }

    abstract fun getToolbarTitle(): String
    override fun onResume() {
        super.onResume()
        toolbarViewModel.title.set(getToolbarTitle())
        activity?.hideKeyboard()
    }

    private fun getBaseActivity() = activity as DrawerActivity
}

class ToolbarViewModel @Inject constructor() : ViewModel() {
    val title = ObservableField<String>()
}