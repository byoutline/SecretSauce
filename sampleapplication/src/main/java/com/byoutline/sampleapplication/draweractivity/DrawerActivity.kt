package com.byoutline.sampleapplication.draweractivity

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.MenuItem
import com.byoutline.sampleapplication.R
import com.byoutline.sampleapplication.databinding.ActivityExampleBinding
import com.byoutline.secretsauce.activities.clearBackStack
import com.byoutline.secretsauce.activities.showFragment
import com.byoutline.secretsauce.databinding.bindContentView
import com.byoutline.secretsauce.lifecycle.lazyViewModel
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

class ExampleActivity : AppCompatActivity(),
        HasSupportFragmentInjector,
        NavigationView.OnNavigationItemSelectedListener {
    @Inject
    lateinit var dispatchingFragmentInjector: DispatchingAndroidInjector<Fragment>

    override fun supportFragmentInjector(): AndroidInjector<Fragment> = dispatchingFragmentInjector

    val toolbarViewModel by lazyViewModel<ToolbarViewModel>()
    private lateinit var binding: ActivityExampleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = bindContentView(R.layout.activity_example)
        binding.navigationView.apply {
            inflateMenu(R.menu.activity_example_drawer)
            setNavigationItemSelectedListener(this@ExampleActivity)
        }
        setTitle(R.string.baseappcompat_title)
        binding.drawerLayout.openDrawer(Gravity.LEFT)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val fragToShow: Fragment? = when (item.itemId) {
            R.id.nav_first -> FirstFragment()
            R.id.nav_second -> SecondFragment()
            else -> null
        }
        if (fragToShow != null) {
            item.isChecked = true
            clearBackStack()
            showFragment(fragToShow, addToBackStack = false)
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        }
        return false
    }
}
