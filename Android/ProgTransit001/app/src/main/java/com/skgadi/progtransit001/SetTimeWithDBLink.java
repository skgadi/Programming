package com.skgadi.progtransit001;

import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;

/**
 * Created by gadis on 19-Aug-17.
 */

class SetTimeWithDBLink implements View.OnClickListener, TimePickerDialog.OnTimeSetListener {

    private Button Button;
    Context ctx;
    String Table, KeyCol, KeyVal, HrsColumn, MinColumn;
    SQLiteDatabase Database;
    public SetTimeWithDBLink(Button Button, Context ctx, SQLiteDatabase Database, String Table,
                             String KeyCol, String KeyVal, String HrsColumn, String MinColumn){
        this.Button = Button;
        this.Button.setOnClickListener(this);
        this.ctx = ctx;

        this.Database = Database;
        this.Table = Table;
        this.KeyCol = KeyCol;
        this.KeyVal = KeyVal;
        this.HrsColumn = HrsColumn;
        this.MinColumn = MinColumn;
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        this.Button.setText( String.format("%02d", hourOfDay) + ":" + String.format("%02d", minute));

        ContentValues data=new ContentValues();
        data.put(HrsColumn, hourOfDay);
        data.put(MinColumn, minute);
        Database.update(Table, data, KeyCol+" == "+KeyVal, null);
    }

    @Override
    public void onClick(View view) {
        String TimeStr = Button.getText().toString();
        String[] TimeSplit = TimeStr.split(":");
        new TimePickerDialog(ctx, this, Integer.parseInt(TimeSplit[0]),
                Integer.parseInt(TimeSplit[1]), true).show();
    }
}


















