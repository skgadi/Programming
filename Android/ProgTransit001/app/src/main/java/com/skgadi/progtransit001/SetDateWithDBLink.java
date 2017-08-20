package com.skgadi.progtransit001;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.util.Calendar;

/**
 * Created by gadis on 19-Aug-17.
 */

public class SetDateWithDBLink implements View.OnClickListener, DatePickerDialog.OnDateSetListener {
    private android.widget.Button Button;
    private Calendar myCalendar;
    Context ctx;
    String Table, KeyCol, KeyVal, DayColumn, MonthColumn;
    SQLiteDatabase Database;
    String[] Months;
    public SetDateWithDBLink(Button Button, Context ctx, SQLiteDatabase Database, String Table,
                             String KeyCol, String KeyVal, String DayColumn, String MonthColumn) {
        this.Button = Button;
        this.Button.setOnClickListener(this);
        this.myCalendar = Calendar.getInstance();
        this.ctx = ctx;

        this.Database = Database;
        this.Table = Table;
        this.KeyCol = KeyCol;
        this.KeyVal = KeyVal;
        this.DayColumn = DayColumn;
        this.MonthColumn = MonthColumn;
        Months = ctx.getResources().getStringArray(R.array.MonthsArray);
    }

    @Override
    public void onDateSet(DatePicker datePicker, int Year, int Month, int Day) {
        this.Button.setText(String.format("%02d", Day) + "-" + Months[Month]);

        ContentValues data=new ContentValues();
        data.put(DayColumn, Day);
        data.put(MonthColumn, Month+1);
        Database.update(Table, data, KeyCol+" == "+KeyVal, null);
    }

    @Override
    public void onClick(View view) {
        int year = myCalendar.get(Calendar.YEAR);
        String DateStr = Button.getText().toString();
        String[] DateSplit = DateStr.split("-");
        int month = 0;
        for(int i = 0; i < Months.length; i++) {
            if(Months[i].toUpperCase().equals(DateSplit[1].toUpperCase())) {
                month = i;
                break;
            }
        }
        new DatePickerDialog(ctx, this, year, month, Integer.parseInt(DateSplit[0])).show();
    }

}
