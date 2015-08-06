package com.byoutline.secretsauce.events;

public class DateSetEvent {
    public final String dateString;

    public DateSetEvent(String dateString) {
        this.dateString = dateString;
    }
}