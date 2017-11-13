# SecretSauce
  * 0.6.0 Converted to Kotlin, dropped Otto Bus and many older classes:
     * `BaseAppCompatActivity` removed - use Kotlin extension functions from `com.byoutline.secretsauce.activities` instead
     * `WaitDialog` removed - use `WaitLayout` or other non blocking progress indicator
     * `DatePickerFragment` removed - creating DatePicker without libs is now easier then before, so there was no longer much to gain by using this implementation
     * `FontCache`/Custom font views removed - Android now has native support for Fonts
     * `NetworkChangeReceiver` - now sets state in `ObservableBoolean` instaed of posting to `Otto` bus
     * `SharedPrefsLoaderAsyncTask` - with Kolin/lambda support in new Android build tools it is not worth wrapping so small amount in code in lib
     * Common events removed - everything Otto related was dropped, use Databinding/RxJava instead
     * `BaseApp` removed - initialization is now needed only for showFragment, Toast and logs, and can
     be done via `Settings.set(debug = BuildConfig.DEBUG, containerViewId = R.id.container)`
     * toast helpers are now `Context` extension functions
     * due to vast changes artifact name changed from `secretsauce` to `secretsaucekt`
     * `Settings` renamed to `SecretSauceSettings` to make it more obvious in application what is being set.
     * `ViewModelAutoLifecycle` and extensions functions added
     * `ss_drawer_shadow` drawables removed
  * 0.5.3 _06.05.2017_ Make listener interface in `CustomClickableSpan` public.
    `CustomFontTextView` now checks `suffixSpace` attribute that can be used to workaround
    wrong measurement on `wrap_content` when font is italic.
  * 0.5.2 Bump dependencies.
  * 0.5.1 implementing `NavigationDrawerFragment.NavigationDrawerCallbacks` is now optional for `BaseAppCompatActivity`.
    Deprecated activities removed.
    Methods for showing non support fragments removed from `BaseAppCompatActivity`
    Support for `WaitDialog` removed from `BaseAppCompatActivity`. Use `WaitDialogHandler` in your activity if you need it.
  * 0.5.0 `getContainerId` and `getDefaultFontName` moved from `BaseApp` to `AppComponentInterface`
  * 0.4.9 `@Named(BaseApp.INJECT_NAME_SESSION_ID)` replaced with `@SessionId`;
    `CheckableFrameLayout` and `ChackableLinearLayout` updated to pass real view instead of null;
    `ViewUtils.getStyledText` added that allows applying multiple customizations at once;
    `kotlin`(added in 0.4.8) removed from project;
    decreased method count(accidental dependency on `retrofit` removed);
    `appcompat` dependencies bump;
  * 0.4.8 Changed `MenuOption` from concrete class to interface. Make `NavigationDrawerFragment::selectItem` public. Added `kotlin` dependency.
  * 0.4.7 Added no args `CachedField` support to `WaitLayout`. Added `setStyledMsg` to `ViewUtils` that allow to set style
  for part of text in `TextView`.
