package com.byoutline.secretsauce.fragments;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;
import com.byoutline.secretsauce.Settings;
import com.byoutline.secretsauce.events.DateSetEvent;
import com.byoutline.secretsauce.utils.LogUtils;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    private static final String TAG = LogUtils.internalMakeLogTag(DatePickerFragment.class);

    public final Calendar calendar = Calendar.getInstance();

    public void setDateString(String dateString) {
        try {
            Date date = Settings.getDisplayDateFormat().parse(dateString);
            calendar.setTime(date);
        } catch (ParseException e) {
            LogUtils.LOGE(TAG, "", e);
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, monthOfYear, dayOfMonth);

        String date = Settings.getDisplayDateFormat().format(calendar.getTime());
        Settings.BUS.post(new DateSetEvent(date));
    }
}
