# SecretSauce
The format is based on [Keep a Changelog](http://keepachangelog.com/en/1.0.0/)
and this project adheres to [Semantic Versioning](http://semver.org/spec/v2.0.0.html).

## [Unreleased]
### Added
 - Binding Adapter - `srcId` calls `setImageResource`
### Changed
 - `lazyViewModelWithAutoLifecycle` will now autocast `this` to `view` to lower boilerplate on `Fragment` 
 
## [0.6.0]  - 14.12.2017
### Added
 - `ViewModelAutoLifecycle` and extensions functions added.
 - Rx Java `invokeOnStopped` extension functions for Fragment and Activity.
### Changed
 - Converted to Kotlin.
 - due to vast changes artifact name changed from `secretsauce` to `secretsaucekt`.
 - `Settings` renamed to `SecretSauceSettings` to make it more obvious in application what is being set.
 - `NetworkChangeReceiver` - now sets state in `ObservableBoolean` instead of posting to `Otto` bus.
 - toast helpers are now `Context` extension functions.
### Removed 
 - Otto Bus
 - `BaseAppCompatActivity` removed - use Kotlin extension functions from `com.byoutline.secretsauce.activities` instead.
 - `WaitDialog` removed - use `WaitLayout` or other non blocking progress indicator.
 - `DatePickerFragment` removed - creating DatePicker without libs is now easier then before, so there was no longer much to gain by using this implementation.
 - `FontCache`/Custom font views removed - Android now has native support for Fonts.
 - `SharedPrefsLoaderAsyncTask` - with Kolin/lambda support in new Android build tools it is not worth wrapping so small amount in code in lib.
 - Common events removed - everything Otto related was dropped, use Databinding/RxJava instead.
 - `BaseApp` removed - initialization is now needed only for showFragment, Toast and logs, and can 
 be done via `Settings.set(debug = BuildConfig.DEBUG, containerViewId = R.id.container)`.
 - `ss_drawer_shadow` drawables removed.
 
## [0.5.3] - 06.05.2017
### Changed
 - Make listener interface in `CustomClickableSpan` public.
 - `CustomFontTextView` now checks `suffixSpace` attribute that can be used to workaround
       wrong measurement on `wrap_content` when font is italic.
       
## [0.5.2] - 24.01.2016   
### Changed
 - Bump dependencies.
 
## [0.5.1] - 22.12.2015  
### Changed
 - Implementing `NavigationDrawerFragment.NavigationDrawerCallbacks` is now optional for `BaseAppCompatActivity`.
### Removed
 - Deprecated activities.
 - Methods for showing non support fragments removed from `BaseAppCompatActivity`.
 - Support for `WaitDialog` removed from `BaseAppCompatActivity`. Use `WaitDialogHandler` in your activity if you need it.

## [0.5.0] - 21.12.2015
### Changed
 - `getContainerId` and `getDefaultFontName` moved from `BaseApp` to `AppComponentInterface`.

## [0.4.9] - 03.12.2015 
### Changed
 - `@Named(BaseApp.INJECT_NAME_SESSION_ID)` replaced with `@SessionId`.
 - `CheckableFrameLayout` and `ChackableLinearLayout` updated to pass real view instead of null.
 - `ViewUtils.getStyledText` added that allows applying multiple customizations at once.
 - `appcompat` dependencies bump.
### Removed
 - `kotlin`(added in 0.4.8) removed from project
 - Accidental dependency on `retrofit` removed - decreased method count.

## [0.4.8] - 06.11.2015
### Added 
 - Added `kotlin` dependency. (Removed in [0.4.9])
### Changed
 - Changed `MenuOption` from concrete class to interface.
 - Make `NavigationDrawerFragment::selectItem` public.

## [0.4.7] - 26.10.2015
### Added
 - Added no args `CachedField` support to `WaitLayout`.
 - Added `setStyledMsg` to `ViewUtils` that allow to set style for part of text in `TextView`.

