package com.example.bloodbankapp.helper;

import android.app.DatePickerDialog;
import android.content.Context;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;

public class CalendrHelper {
Context context;


public CalendrHelper(Context context){this.context=context;}

public void showCalendr(final TextView text){
    final Calendar calendar = Calendar.getInstance();
    int yy=calendar.get(Calendar.YEAR);
    int mm=calendar.get(Calendar.MONTH);
    int dd=calendar.get(Calendar.DAY_OF_MONTH);
    final DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            String date=
                    String.valueOf(year)+"-0"+ String.valueOf(monthOfYear+"-"+ String.valueOf(dayOfMonth));
            text.setText(date);
        }
    },yy,mm,dd);
    datePickerDialog.show();







}



}
