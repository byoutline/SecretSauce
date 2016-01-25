#   NetworkChangeReceiver

Wrapper from BroadcastReceiver that uses Bus to post Android broadcasts instead of doing the job itself.
If you prefer to use the Bus instead of the standard broadcast, this is for you.

###How to use?

(it may be helpful to read:[Dagger] and [Otto])

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
      networkChangeReceiver.onResume(this);
  }    
  
  @Override
  protected void onPause() {
      networkChangeReceiver.onPause(this);
      super.onPause();
  }   
```
- to listen when network status changed add @Subscribe method

```java
      @Subscribe
      public void onInternetConnectionChanged(InternetStateChangedEvent event){
         //your code
         //event.isOrWillBeEnabled==true when network is or will be enabled, otherwise false
      } 
```

[Dagger]: <http://square.github.io/dagger/>
 [Otto]: <http://square.github.io/otto/>
