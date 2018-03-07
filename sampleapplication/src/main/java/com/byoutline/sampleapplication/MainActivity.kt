package com.byoutline.sampleapplication

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.byoutline.sampleapplication.contextdependencies.ContextDependenciesActivity
import com.byoutline.sampleapplication.customfontviews.CustomFontViewsActivity
import com.byoutline.sampleapplication.databinding.ActivityMainBinding
import com.byoutline.sampleapplication.databinding.DataBindingActivity
import com.byoutline.sampleapplication.draweractivity.DrawerActivity
import com.byoutline.sampleapplication.networkchangereceiver.NetworkChangeActivity
import com.byoutline.sampleapplication.roundedimageview.DrawableActivityExample
import com.byoutline.sampleapplication.rx.RxLifecycleActivity
import com.byoutline.sampleapplication.waitlayout.WaitActivity
import com.byoutline.secretsauce.activities.WebViewActivityV7
import com.byoutline.secretsauce.databinding.bindContentView
import com.byoutline.secretsauce.lifecycle.AttachableViewModel
import com.byoutline.secretsauce.lifecycle.getVMWithAutoLifecycle
import me.tatarka.bindingcollectionadapter2.ItemBinding
import javax.inject.Inject


class MainActivity : ClassNameAsToolbarTitleActivity(), MainActivityItemClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindContentView<ActivityMainBinding>(R.layout.activity_main).viewModel = getVMWithAutoLifecycle(MainActivityVM::class)
    }

    override fun onItemClick(item: MainActivityItem) {
        val intent = Intent(this, item.clazz)
        if (item.clazz == WebViewActivityV7::class.java) {
            intent.putExtra(WebViewActivityV7.BUNDLE_URL, "https://github.com/byoutline/SecretSauce")
        }
        startActivity(intent)
    }
}


class MainActivityVM @Inject constructor() : AttachableViewModel<MainActivityItemClickListener>() {
    val items: List<MainActivityItem> = listOf(
            I(DrawerActivity::class.java, "Drawer with fragments"),
            I(NetworkChangeActivity::class.java, "Network change observer"),
            I(DrawableActivityExample::class.java, "Rounded drawables"),
            I(CustomFontViewsActivity::class.java, "Custom text fonts"),
            I(WaitActivity::class.java, "Work in progress indicators"),
            I(RxLifecycleActivity::class.java, "AttachableViewModelRx: RxJava+ViewModel+Android lifecycle"),
            I(DataBindingActivity::class.java, "Android Databinding+ViewModel+Android lifecycle"),
            I(ContextDependenciesActivity::class.java, "Dagger Android: Injecting dependencies that require activity context into activities and fragments")
    )
    private val listener: MainActivityItemClickListener = object : MainActivityItemClickListener {
        override fun onItemClick(item: MainActivityItem) {
            view?.onItemClick(item)
        }
    }
    val itemBinding: ItemBinding<MainActivityItem> = ItemBinding.of<MainActivityItem>(BR.item, R.layout.main_activity_item)
            .bindExtra(BR.listener, listener)
}

interface MainActivityItemClickListener {
    fun onItemClick(item: MainActivityItem)
}

private typealias I = MainActivityItem

data class MainActivityItem(val clazz: Class<out AppCompatActivity>, private val name: String) {
    fun displayName() = "$name (${clazz.simpleName})"
}