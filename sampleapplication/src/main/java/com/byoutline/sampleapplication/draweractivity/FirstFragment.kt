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
class FirstFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = FragmentMainBinding.inflate(inflater, container, false)
        binding.mainBtn.setOnClickListener { onClick() }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun getToolbarTitle(): String =  getString(R.string.first_fragment)

    fun onClick() {
        val builder = AlertDialog.Builder(context)
        builder.setMessage("Welcome First Time!")
        builder.setPositiveButton("OK", null)
        builder.show()
    }
}
