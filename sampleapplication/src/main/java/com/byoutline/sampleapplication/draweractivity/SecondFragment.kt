package com.byoutline.sampleapplication.draweractivity

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.byoutline.sampleapplication.R
import com.byoutline.sampleapplication.databinding.FragmentMainBinding

/**
 * Created by michalp on 12.04.16.
 */
class SecondFragment : BaseFragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = FragmentMainBinding.inflate(inflater, container, false)
        binding.mainBtn.setOnClickListener { onClick() }
        return binding.root
    }

    override fun getToolbarTitle(): String = getString(R.string.second_fragment)

    fun onClick() {
        val builder = AlertDialog.Builder(context)
        builder.setMessage("Welcome Second Time!")
        builder.setPositiveButton("OK", null)
        builder.show()
    }
}
