package com.example.islamicapp;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class TimePickerDialog extends DialogFragment {
    private android.app.TimePickerDialog.OnTimeSetListener timeSetListener; // listener object to get calling fragment listener
    android.app.TimePickerDialog  myTimePicker;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        timeSetListener = (android.app.TimePickerDialog.OnTimeSetListener)getTargetFragment(); // getting passed fragment
        myTimePicker = new android.app.TimePickerDialog(getActivity(), timeSetListener, hour,minute,false); // DatePickerDialog gets callBack listener as 2nd parameter
        // Create a new instance of DatePickerDialog and return it
        return myTimePicker;
    }
}
