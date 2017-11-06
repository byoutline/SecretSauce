#   NetworkChangeReceiver

Wrapper from BroadcastReceiver that uses Bus to post Android broadcasts instead of doing the job itself.
If you prefer to use the Bus instead of the standard broadcast, this is for you.

###How to use?

(it may be helpful to read about:[Dagger] and [Android DataBinding])

- add provides methods to a module:


```java
    @Provides
    @Singleton
    public ConnectivityManager providesConnectivityManager(Context context) {
        return (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    @Provides
    @Singleton
    NetworkChangeReceiver providesNetworkChangeReceiver(ConnectivityManager connectivityManager) {
        return new NetworkChangeReceiver(connectivityManager);
    }
```
- add injecting to the Activity where it will be used

```java
      @Inject
      NetworkChangeReceiver networkChangeReceiver;
```

- in that activity override onResume() and onPause() methods

```java
  @Override
  protected void onResume() {
      super.onResume();
      networkChangeReceiver.register(this);
  }    
  
  @Override
  protected void onPause() {
      networkChangeReceiver.unregister(this);
      super.onPause();
  }   
```
- then bind network status with view

```java
binding.setConnected(networkChangeReceiver.getConnectedOrConnecting());
```

```xml
    <data>

        <variable
            name="connected"
            type="android.databinding.ObservableBoolean" />
    </data>
            android:text="@{connected ? @string/network_change_receiver_network_available : @string/network_change_receiver_network_not_available }"
```

[Dagger]: <http://square.github.io/dagger/>
[Otto]: <https://developer.android.com/tools/data-binding/guide.html>
