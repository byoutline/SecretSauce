#   Rounded Image View

We find here a special ImageView that can show images in rounded form.
RoundedImageView uses [RoundedDrawable](https://github.com/byoutline/SecretSauce/blob/feature/sample/SecretSauce/src/main/java/com/byoutline/secretsauce/views/RoundedDrawable.java)
class from our library.
### How to use?

Create a view in the xml file, as well as other views.

```
<com.byoutline.secretsauce.views.RoundedImageView
            android:id="@+id/roundedView"
            android:layout_width="100dp"
            android:layout_height="100dp"
            android:layout_gravity="center"
            android:src="@mipmap/ic_launcher"
            app:all_rounded_corners="true"
            app:border_color="@color/colorAccent"
            app:border_width="5dp"
            app:corner_radius="10dp"
            app:is_oval="false"/>

        <com.byoutline.secretsauce.views.RoundedImageView
            android:id="@+id/roundedView2"
            android:layout_width="100dp"
            android:layout_height="100dp"
            android:layout_gravity="center"
            android:layout_marginTop="20dp"
            android:src="@mipmap/ic_launcher"
            app:all_rounded_corners="true"
            app:border_color="@color/colorAccent"
            app:border_width="5dp"
            app:corner_radius="10dp"
            app:is_oval="true"/>

        <com.byoutline.secretsauce.views.RoundedImageView
            android:id="@+id/roundedView3"
            android:layout_width="100dp"
            android:layout_height="100dp"
            android:layout_gravity="center"
            android:layout_marginTop="20dp"
            android:background="@color/colorPrimary"
            android:src="@mipmap/ic_launcher"
            app:all_rounded_corners="true"
            app:border_color="@color/colorAccent"
            app:border_width="10dp"
            app:corner_radius="10dp"
            app:is_oval="true"/>

        <com.byoutline.secretsauce.views.RoundedImageView
            android:id="@+id/roundedView4"
            android:layout_width="150dp"
            android:layout_height="80dp"
            android:layout_gravity="center"
            android:layout_marginTop="20dp"
            android:background="@color/colorPrimary"
            android:scaleType="centerCrop"
            android:src="@mipmap/ic_launcher"
            app:all_rounded_corners="true"
            app:border_color="@color/colorAccent"
            app:border_width="5dp"
            app:corner_radius="10dp"
            app:is_oval="false"
            app:round_background="true"/>

        <com.byoutline.secretsauce.views.RoundedImageView
            android:id="@+id/roundedView5"
            android:layout_width="200dp"
            android:layout_height="80dp"
            android:layout_gravity="center"
            android:layout_marginTop="20dp"
            android:background="@color/colorPrimary"
            android:elevation="6dp"
            android:padding="10dp"
            android:scaleType="centerCrop"
            android:src="@mipmap/ic_launcher"
            app:all_rounded_corners="true"
            app:border_color="@color/colorAccent"
            app:border_width="5dp"
            app:corner_radius="30dp"
            app:is_oval="false"
            app:round_background="false"/>
```