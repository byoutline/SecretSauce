# SecretSauce
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.byoutline.secretsauce/secretsauce/badge.svg?style=flat)](http://mvnrepository.com/artifact/com.byoutline.secretsauce/secretsauce)
 master:  [![Build Status](https://travis-ci.org/byoutline/SecretSauce.svg?branch=master)](https://travis-ci.org/byoutline/SecretSauce)
 develop: [![Build Status](https://travis-ci.org/byoutline/SecretSauce.svg?branch=develop)](https://travis-ci.org/byoutline/SecretSauce)
 
Collection of utility classes that are common for multiple android projects.

Contains: 
  * Activity Extension functions for hiding and showing keyboards, showing fragments with animations, default date picker, and more 
  * [WebViewActivity](https://github.com/byoutline/SecretSauce/blob/feature/sample/WebViewActivityUsing.md) (for displaying basic full screen webview, that accepts URL and data to post) 
  * [WaitLayout] -Displays spinner on any item, can synchronize with CachedFields and CachedEndpoints 
  * PicassoScrollListener (that stops loading images during fast scroll) 
  * LogUtils (for situation when Timber is not appropriate) 
  * [NetworkChangeReceiver] - wrapper from BroadcastReceiver that sets current state to Android ObservableBoolean 
  * AnimationRunnable (helps with custom view transitions between two states) 
  * [Checkable views] 
  * [LinkTextView](https://github.com/byoutline/SecretSauce/blob/feature/sample/CustomViewUse.md) (TextView where URLs are clickable) 
  * [RoundedDrawable, RoundedImageView](https://github.com/byoutline/SecretSauce/blob/feature/sample/RoundedDrawableUsing.md) 
  * [ViewUtils](https://github.com/byoutline/SecretSauce/blob/feature/sample/ViewUtilsUsing.md) (that show toasts, sets styled text fragments, converts dp to px, converts dp to pixel, and more) 
  * JdkBasedTimeZoneProvider (for faster loading of JodaTime)
  * BindingAdapters - Common Android DataBinding adapters
     * `onClick` that does not pass view to listener (to avoid spill Android dependencies do viewModel)
     * `showView` (`setVisibility` `VISIBLE`/`GONE`)
     * `error` (`editText.setError`)
     * `active` (`setActivated`)
  * RxLifecycle extension functions - allows invoking certain action on Fragment/Activity pause
      (commonly used to detach view from viewModel, or similar cleanups)
    

[NetworkChangeReceiver]: <NetworkChangeReceiverUsing.md>
[WaitLayout]: <WaitLayoutUsing.md>
[DatePickerFragment]: <DatePickerFragmentUsing.md>
[Custom font views]: <CustomViewUse.md>
[Checkable views]: <CustomViewUse.md>

*** initialization ***
To use showFragment extension functions initialize container id via:
```kotlin
Settings.set(debug = BuildConfig.DEBUG, containerViewId = R.id.container)
```
(Debug flag is used for logs and Toasts)
