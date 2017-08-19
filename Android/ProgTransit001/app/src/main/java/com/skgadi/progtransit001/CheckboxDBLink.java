package com.skgadi.progtransit001;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.widget.CompoundButton;

/**
 * Created by gadis on 19-Aug-17.
 */

public class CheckboxDBLink implements CompoundButton.OnCheckedChangeListener {
    String Table, KeyCol, KeyVal, Column;
    SQLiteDatabase Database;
    public CheckboxDBLink (SQLiteDatabase inDatabase, String inTable, String inKeyCol, String inKeyVal, String inColumn) {
        this.KeyCol = inKeyCol;
        this.KeyVal = inKeyVal;
        this.Column = inColumn;
        this.Database = inDatabase;
        this.Table = inTable;
    }
    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        ContentValues data=new ContentValues();
        if (b)
            data.put(Column, 1);
        else
            data.put(Column, 0);
        Database.update(Table, data, KeyCol+" == "+KeyVal, null);
    }
}
