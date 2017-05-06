#   DatePickerFragment

Simple to use fragment with DatePicker. DatePickerFragment sents 
a DateSetEvent when date is chosen.

### How to use?

- Inject and register bus to receive event 

```java
    @Inject
    Bus bus;

    @Override
    protected void onResume() {
        super.onResume();
        bus.register(this);
    }

    @Override
    protected void onPause() {
        bus.unregister(this);
        super.onPause();
    }
```

-show DatePickerFragment

```java
DatePickerFragment fragment = new DatePickerFragment();
        fragment.show(getFragmentManager(), TAG);
```

- receive event with information about chosen date 

```java
 @Subscribe
    public void receiveDateChanges(DateSetEvent event){
        /*
         * get chosen date from event and
         * for example set as text in TextView
         */
        dateTv.setText(event.dateString);
    }
```

