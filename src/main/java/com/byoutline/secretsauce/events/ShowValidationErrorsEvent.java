package com.byoutline.secretsauce.events;

import java.util.Map;

public class ShowValidationErrorsEvent {
    public final String screenName;
    public Map<String, String[]> validationErrors;

    public ShowValidationErrorsEvent(String screenName, Map<String, String[]> validationErrors) {
        this.screenName = screenName;
        this.validationErrors = validationErrors;
    }
}