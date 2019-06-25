package com.byoutline.sampleapplication

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.byoutline.sampleapplication.databinding.ActivityMainBinding
import com.byoutline.secretsauce.activities.WebViewActivityV7
import com.byoutline.secretsauce.databinding.bindContentView
import com.byoutline.secretsauce.lifecycle.AttachableViewModel
import com.byoutline.secretsauce.lifecycle.getVMWithAutoLifecycle
import me.tatarka.bindingcollectionadapter2.ItemBinding
import javax.inject.Inject
import javax.inject.Qualifier

class MainActivity : ClassNameAsToolbarTitleActivity(), MainActivityItemClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindContentView<ActivityMainBinding>(R.layout.activity_main).viewModel =
            getVMWithAutoLifecycle(MainActivityVM::class)
    }

    override fun onItemClick(item: MainActivityItem) {
        val intent = Intent(this, item.clazz)
        if (item.clazz == WebViewActivityV7::class.java) {
            intent.putExtra(WebViewActivityV7.BUNDLE_URL, "https://github.com/byoutline/SecretSauce")
        }
        startActivity(intent)
    }
}

class MainActivityVM @Inject constructor(
    @MainActivityItems val items: List<MainActivityItem>
) : AttachableViewModel<MainActivityItemClickListener>() {
    private val listener: MainActivityItemClickListener = object : MainActivityItemClickListener {
        override fun onItemClick(item: MainActivityItem) {
            view?.onItemClick(item)
        }
    }
    val itemBinding: ItemBinding<MainActivityItem> =
        ItemBinding.of<MainActivityItem>(BR.item, R.layout.main_activity_item)
            .bindExtra(BR.listener, listener)
}

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
internal annotation class MainActivityItems

interface MainActivityItemClickListener {
    fun onItemClick(item: MainActivityItem)
}

data class MainActivityItem(val clazz: Class<out AppCompatActivity>, private val name: String) {
    fun displayName() = "$name (${clazz.simpleName})"
}