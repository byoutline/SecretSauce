# SecretSauce
  * 0.4.9 @Named(BaseApp.INJECT_NAME_SESSION_ID) replaced with @SessionId;
    CheckableFrameLayout and ChackableLinearLayout updated to pass real view instead of null;
    ViewUtils.getStyledText added that allows applying multiple customizations at once;
    kotlin(added in 0.4.8) removed from project;
    decreased method count(accidental dependency on retrofit removed);
    appcompat dependencies bump;
  * 0.4.8 Changed MenuOption from concrete class to interface. Make NavigationDrawerFragment::selectItem public.
  * 0.4.7 Added no args CachedField support to WaitLayout. Added setStyledMsg to ViewUtils that allow to set style
  for part of text in TextView.
