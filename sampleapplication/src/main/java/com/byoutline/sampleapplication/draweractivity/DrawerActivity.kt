package com.byoutline.sampleapplication.draweractivity

import android.os.Bundle
import com.google.android.material.navigation.NavigationView
import androidx.fragment.app.Fragment
import androidx.core.view.GravityCompat
import android.view.Gravity
import android.view.MenuItem
import com.byoutline.sampleapplication.ClassNameAsToolbarTitleActivity
import com.byoutline.sampleapplication.R
import com.byoutline.sampleapplication.databinding.ActivityDrawerBinding
import com.byoutline.secretsauce.activities.clearBackStack
import com.byoutline.secretsauce.activities.showFragment
import com.byoutline.secretsauce.databinding.bindContentView
import com.byoutline.secretsauce.lifecycle.lazyViewModel
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

class DrawerActivity : ClassNameAsToolbarTitleActivity(),
    HasSupportFragmentInjector,
    NavigationView.OnNavigationItemSelectedListener {
    @Inject
    lateinit var dispatchingFragmentInjector: DispatchingAndroidInjector<Fragment>

    override fun supportFragmentInjector(): AndroidInjector<Fragment> = dispatchingFragmentInjector

    val toolbarViewModel by lazyViewModel<ToolbarViewModel>()
    private lateinit var binding: ActivityDrawerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = bindContentView(R.layout.activity_drawer)
        binding.toolbar!!.model = toolbarViewModel
        binding.navigationView.apply {
            inflateMenu(R.menu.activity_example_drawer)
            setNavigationItemSelectedListener(this@DrawerActivity)
        }
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
