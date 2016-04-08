# WaitDialogFragment

WaitDialog is a full screen blocking spinner that survives device 
rotations and keeps tracks of multiple invocations.

### Before using

Create Activity that extends BaseAppCompatActivity
(com.byoutline.secretsauce.activities.BaseAppCompatActivity), there are
some useful and necessary methods.

### How to use WaitDialog?

- inject bus to sent event
(it may be helpful to read:[Dagger] and [Otto])

```
   @Inject
    Bus bus;

```

- create and register WaitDialogHandler

WaitDialogHandler provides support for WaitFragment.

```java

private WaitDialogHandler waitDialogHandler;
  
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_dialog_activity_example);
    SampleApp.component.inject(this);
    ButterKnife.bind(this);
    waitDialogHandler = new WaitDialogHandler(this, getSupportFragmentManager());
}

 @Override
    protected void onResume() {
        super.onResume();
        waitDialogHandler.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        waitDialogHandler.onPause();
    }
```

- show and hide

```java
  private void showWaitDialog() {
        /**
         * ShowWaitDialogEvent show dialog fragment to the user by proper event
         */
        bus.post(new ShowWaitFragmentEvent());
        /**
         * betwen post ShowWaitFragmentEvent and HideWaitFragmentEvent
         * wait dialog will be visible to the user
         *
         * to simulate some work, we use postDelayed(),
         * the dialog dismisses after 4 seconds
         */
        waitDialogBtn.postDelayed(new Runnable() {
            @Override
            public void run() {
                bus.post(new HideWaitFragmentEvent());
            }
        },4000);
    }
```
[Dagger]: <http://square.github.io/dagger/>
[Otto]: <http://square.github.io/otto/>