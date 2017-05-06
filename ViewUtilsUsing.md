#   ViewUtils

A collection of static methods that help and simplify everyday work. 

###  which can be useful to me the most?

 * public static void showToast(String text)
 
    display toast without repeating the same long code every time

 * public static float convertDpToPixel(float dp, Context context),
 
    convert dp value to number of pixels, needed when setup views
    programmatically (for instance toolbar elevation),
    we have also `public static float convertPixelsToDp(float px, Context context)`
    
 *  public static void showView(View view, boolean visibility),
    
    set visibility of view without worry about actual view state
    (checking actual visibility, NPE)
     
    
 * public static void removeFromLayout(RelativeLayout view)
 
    remove view from layout if it is needed
