#   WebViewActivity

Simple to use activity with webview inside. Should be used for 
displaying basic full screen webview, that accepts URL and data to post.

### How to use?

- changes in AndroidManifest.xml

```
<uses-permission android:name="android.permission.INTERNET" />

...

<activity
android:name="com.byoutline.secretsauce.activities.WebViewActivityV7"
android:theme="@style/Theme.AppCompat.NoActionBar" />
```

- run activity

```java
 Intent intent = new Intent(this, WebViewActivityV7.class);
 intent.putExtra(WebViewActivityV7.BUNDLE_URL,"https://github.com/byoutline/SecretSauce");
 //intent.putExtra(WebViewActivityV7.BUNDLE_POST_DATA, your_post_data);//if needed
 startActivity(intent);
```
