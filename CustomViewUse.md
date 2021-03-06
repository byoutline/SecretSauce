#   Checkable/Link Views

 
### How to use CheckableCustomFontTextView?

- add CheckableCustomFontTextView to your layout file, set style,
  set layout clickable, and set some selector if you wish
 
```
<com.byoutline.secretsauce.views.CheckableCustomFontTextView
            android:layout_width="match_parent"
            android:layout_height="50dp"
            android:layout_marginTop="16dp"
            style="@style/customFontStyle2"
            android:clickable="true"
            app:textAngle="5"
            android:gravity="center"
            android:background="@color/check_selector"
            android:text="checkable custom font text view"/>
```

### How to use LinkTextView?

- add LinkTextView to yout layout file, for example like that:

```
 <com.byoutline.secretsauce.views.LinkTextView
            android:id="@+id/link_listener"
            android:layout_width="match_parent"
            android:layout_height="40dp"
            android:layout_marginTop="16dp"
            android:clickable="true"
            android:text="hello"
            android:textSize="16sp" />
```

- add listener and set text with url in activity/fragment

```java
 LinkTextView linkListener =(LinkTextView)findViewById(R.id.link_listener);
        String htmlString = "Click on <a href=https://github.com/byoutline/SecretSauce>" +
               "https://github.com/byoutline/SecretSauce </a> or click <a href=here>here</a> to see toast";
        
        linkListener.setText(htmlString, TextView.BufferType.NORMAL);
        linkListener.setOnLinkClickListener(new LinkClickListener() {
            @Override
            public void onClick(String url) {
                Toast.makeText(getApplicationContext(),"Click on URL in textView",Toast.LENGTH_SHORT).show();
            }
        });
```

### How to use CheckableLinearLayout?

- add CheckableLinearLayout to your layout file, set CheckableLinearLayout clickable
  and set some selector to see difference

```
<com.byoutline.secretsauce.views.CheckableLinearLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_marginTop="16dp"
            android:background="@color/check_selector"
            android:clickable="true"
            android:orientation="vertical">

            <com.byoutline.secretsauce.views.CustomFontTextView
                style="@style/customFontStyle2"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:text="CustomFontTextView in CheckableLinearLayout"
                android:textSize="18sp" />

            <com.byoutline.secretsauce.views.CustomFontTextView
                style="@style/customFontStyle2"
                android:layout_marginTop="16dp"
                android:layout_width="match_parent"
                android:layout_gravity="center"
                android:layout_height="wrap_content"
                android:text="CustomFontTextView in CheckableLinearLayout"
                android:textSize="18sp" />
        </com.byoutline.secretsauce.views.CheckableLinearLayout>
```

- You can use CheckableFrameLayout in the same way.