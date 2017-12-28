# SecretSauce
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.byoutline.secretsauce/secretsaucekt/badge.svg?style=flat)](http://mvnrepository.com/artifact/com.byoutline.secretsauce/secretsaucekt)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.byoutline.secretsauce.views/waitlayout/badge.svg?style=flat)](http://mvnrepository.com/artifact/com.byoutline.secretsauce.views/waitlayout)
 master:  [![Build Status](https://travis-ci.org/byoutline/SecretSauce.svg?branch=master)](https://travis-ci.org/byoutline/SecretSauce)
 develop: [![Build Status](https://travis-ci.org/byoutline/SecretSauce.svg?branch=develop)](https://travis-ci.org/byoutline/SecretSauce)
 
Collection of utility classes that are common for multiple android projects.

Contains: 
  * Activity Extension functions for hiding and showing keyboards, showing fragments with animations, default date picker, and more 
  * [WebViewActivity](https://github.com/byoutline/SecretSauce/blob/feature/sample/WebViewActivityUsing.md) (for displaying basic full screen webview, that accepts URL and data to post) 
  * [WaitLayout] - Displays spinner on any item, can synchronize with CachedFields and CachedEndpoints. This part is available in separate artifact
  * PicassoScrollListener (that stops loading images during fast scroll) 
  * LogUtils (for situation when Timber is not appropriate) 
  * [NetworkChangeReceiver] - wrapper from BroadcastReceiver that sets current state to Android ObservableBoolean 
  * AnimationRunnable (helps with custom view transitions between two states) 
  * [Checkable views] 
  * [LinkTextView](https://github.com/byoutline/SecretSauce/blob/feature/sample/CustomViewUse.md) (TextView where URLs are clickable) 
  * [RoundedDrawable, RoundedImageView](https://github.com/byoutline/SecretSauce/blob/feature/sample/RoundedDrawableUsing.md) 
  * [ViewUtils](https://github.com/byoutline/SecretSauce/blob/feature/sample/ViewUtilsUsing.md) (set styled text fragments, convert dp to px, convert dp to pixel, and more)
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

