# SecretSauce
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.byoutline.secretsauce/secretsaucekt/badge.svg?style=flat)](http://mvnrepository.com/artifact/com.byoutline.secretsauce/secretsaucekt)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.byoutline.secretsauce.views/waitlayout/badge.svg?style=flat)](http://mvnrepository.com/artifact/com.byoutline.secretsauce.views/waitlayout)
 master:  [![Build Status](https://travis-ci.org/byoutline/SecretSauce.svg?branch=master)](https://travis-ci.org/byoutline/SecretSauce)
 develop: [![Build Status](https://travis-ci.org/byoutline/SecretSauce.svg?branch=develop)](https://travis-ci.org/byoutline/SecretSauce)
 
Collection of utility classes that are common for multiple android projects.

Contains: 
  * Activity Extension functions for hiding and showing keyboards, showing fragments with animations, default date picker, and more 
  * [WebViewActivity](WebViewActivityUsing.md) (for displaying basic full screen webview, that accepts URL and data to post) 
  * [WaitLayout] - Displays spinner on any item, can synchronize with CachedFields and CachedEndpoints. This part is available in separate artifact
  * PicassoScrollListener (that stops loading images during fast scroll) 
  * LogUtils (for situation when Timber is not appropriate) 
  * [NetworkChangeReceiver] - wrapper from BroadcastReceiver that sets current state to Android ObservableBoolean 
  * AnimationRunnable (helps with custom view transitions between two states) 
  * [Checkable views] 
  * [LinkTextView](CustomViewUse.md) (TextView where URLs are clickable) 
  * [RoundedDrawable, RoundedImageView](RoundedDrawableUsing.md) 
  * [ViewUtils](ViewUtilsUsing.md) (set styled text fragments, convert dp to px, convert dp to pixel, and more)
  * Toast Context extension functions
  * JdkBasedTimeZoneProvider (for faster loading of JodaTime)
  * BindingAdapters - Common Android DataBinding adapters
     * `onClick` that does not pass view to listener (to avoid spill Android dependencies do viewModel)
     * `showView` (`setVisibility` `VISIBLE`/`GONE`)
     * `error` (`editText.setError`)
     * `active` (`setActivated`)
  * RxLifecycle extension functions - allows invoking certain action on Fragment/Activity pause
      (commonly used to detach view from viewModel, or similar cleanups)
  * Android Dagger Lifecycle common classes like [ViewModelFactory] and extension functions

    

[NetworkChangeReceiver]: <NetworkChangeReceiverUsing.md>
[WaitLayout]: <WaitLayoutUsing.md>
[Checkable views]: <CustomViewUse.md>

#### initialization
Some feature are more convenient to use if you declare some parameters up front.
```kotlin
SecretSauceSettings.set(debug = BuildConfig.DEBUG,
                containerViewId = R.id.container,
                bindingViewModelId = BR.viewModel,
                viewModelFactoryProvider = { viewModelFactory })
```
Settings any of those values is optional. If you don't use given feature you can skip given
parameter. However if you attempt to use given feature later you will either have to pass optional
param to function or the exception will be thrown.
`debug` is used by logs and Toasts
`containerViewId` used by showFragment methods
`bindingViewModelId` and `viewModelFactoryProvider` use by `viewModel` extension functions.

#### Do you create new instances of objects inside your fragments/activities by hand, because they require scoped context? Dagger Android support can help you!
  
> [Dagger works best if it can create all the injected objects](https://google.github.io/dagger/android.html)
  
Lets say that we have successfully configured Dagger and we inject our non-Android dependencies.  
Our activity may look something like that:

```kotlin
class MyActivity : AppCompatActivity() {

    @Inject
    lateinit var dependencyA: DependencyA
    @Inject
    lateinit var dependencyB: DependencyB
    val colorMixer by lazy { ColorMixer(this, dependencyA, dependencyB) }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityContextDependencies2Binding = bindContentView(R.layout.myactivity)
        DaggerUtil.inject(this)
        binding.textView.setBackgroundColor(colorMixer.color)
    }
}
```  

It is not that bad, but there are several problems:
- We create ColorMixer inside the activity. This tight coupling makes it easy to pass local context, but unfortunately 
 we lose some of advantages of using [Inversion of control](https://en.wikipedia.org/wiki/Inversion_of_control).
 For example it becomes harder to switch ColorMixer implementation.

- We do inject DependencyA and DependencyB that we do not need just so we can create ColorMixer instance. 
That introduces boilerplate and namespace pollution.
 
- If ColorMixer had two arguments of the same type we risk of passing them in wrong order causing (sometimes) subtle bugs.

You may ask - what if I use application context everywhere instead? In some cases that may work but I would discourage it. 
You will get exception if you try to get layout inflater, and you may get wrong answer if you ask for color (if you use themed activities).
 
 So what can we do? We can use `dagger.android`
#### Do you create new instances of objects inside your fragments/activities by hand, because they require scoped context? Dagger Android support can help you!
  
> [Dagger works best if it can create all the injected objects](https://google.github.io/dagger/android.html)
  
Lets say that we have successfully configured Dagger and we inject our non-Android dependencies.  
Our activity may look something like that:

```kotlin
class MyActivity : AppCompatActivity() {

    @Inject
    lateinit var dependencyA: DependencyA
    @Inject
    lateinit var dependencyB: DependencyB
    val colorMixer by lazy { ColorMixer(this, dependencyA, dependencyB) }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityContextDependencies2Binding = bindContentView(R.layout.myactivity)
        DaggerUtil.inject(this)
        binding.textView.setBackgroundColor(colorMixer.color)
    }
}
```  

It is not that bad, but there are several problems:
- We create ColorMixer inside the activity. This tight coupling makes it easy to pass local context, but unfortunately 
 we lose some of advantages of using [Inversion of control](https://en.wikipedia.org/wiki/Inversion_of_control).
 For example it becomes harder to switch ColorMixer implementation.

- We do inject DependencyA and DependencyB that we do not need just so we can create ColorMixer instance. 
That introduces boilerplate and namespace pollution.
 
- If ColorMixer had two arguments of the same type we risk of passing them in wrong order causing (sometimes) subtle bugs.

You may ask - what if I use application context everywhere instead? In some cases that may work but I would discourage it. 
You will get exception if you try to get layout inflater, and you may get wrong answer if you ask for color (if you use themed activities).
 
 So what can we do? We can use `dagger.android`
