# SecretSauce
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.byoutline.secretsauce/secretsauce/badge.svg?style=flat)](http://mvnrepository.com/artifact/com.byoutline.secretsauce/secretsauce)
 master:  [![Build Status](https://travis-ci.org/byoutline/SecretSauce.svg?branch=master)](https://travis-ci.org/byoutline/SecretSauce)
 develop: [![Build Status](https://travis-ci.org/byoutline/SecretSauce.svg?branch=develop)](https://travis-ci.org/byoutline/SecretSauce)
 
Collection of utility classes that are common for multiple android projects.

Contains: 
    * BaseAppCompatActivity that handles WaitDialog default drawer setup, hiding and showing keyboards, showing fragments with animations, default date picker, and more) 
    * WebViewActivity (for displaying basic full screen webview, that accepts URL and data to post) 
    * WaitDialog (Full screen blocking spinner that survives device rotations and keeps tracks of multiple invocations) 
    * SimpleDialog (that displays layout id in dialog) 
    * WaitLayout (Displays spinner on any item, can synchronize with CachedFields and CachedEndpoints) 
    * DatePickerFragment 
    * PicassoScrollListener (that stops loading images during fast scroll) 
    * FontCache 
    * LogUtils (for situation when Timber is not appropriate) 
    * NetworkChangeReceiver (that uses Bus to post Android broadcasts) 
    * SharedPrefsLoaderAsyncTask(for loading SharedPreferences in background thread) 
    * AnimationRunnable (helps with custom view transitions between two states) 
    * Custom font views 
    * Checkable views 
    * LinkTextView (TextView where URLs are clickable) 
    * RoundedDrawable, RoundedImageView 
    * ViewUtils (that show toasts, sets styled text fragments, converts dp to px, converts dp to pixel, and more) 
    * JdkBasedTimeZoneProvider (for faster loading of JodaTime) 
    * Common events (For usage with Otto bus)
    * BaseApp (to ensure initialization) 
    * Some deprecated classes that are still there for backwards compatibility