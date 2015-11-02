# WaitLayout

ViewGroup which may have directly one child, and can display spinner on it. 
Particularly useful when data is loaded when the view is displayed on the screen.

###How to use?

You can use it as any other layout.For example:

```xml
<com.byoutline.secretsauce.views.WaitLayout
        android:id="@+id/example_wait_layout"
        android:layout_width="match_parent"
        android:layout_height="wrap_content">
        
        <LinearLayout
            android:id="@+id/example_linear_layout"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:orientation="vertical">
            <CheckBox
                style="@style/checkBoxStyle"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                tools:text="@string/nothing" />

            <CheckBox
                style="@style/checkBoxStyle"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                tools:text="@string/something" />

        </LinearLayout>
</com.byoutline.secretsauce.views.WaitLayout>
```

In addition, to make it possible to react on CachedField changes, you must add:
```
...
WaitLayout exampleWaitLayout =(WaitLayout)findViewById(R.id.exampleWaitLayout);
...
exampleWaitLayout.showProgressOf(cachedField);
...
```
in code where data is beeing changed. Also, you can manually use method to show and hide loading bar:

```
exampleWaitLayout.showLoadingBar();
//or
exampleWaitLayout.hideLoadingBar();
```
