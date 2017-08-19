package com.skgadi.progtransit001;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.AdapterView;

/**
 * Created by gadis on 18-Aug-17.
 */

public class SpnnerDatabaseLink implements AdapterView.OnItemSelectedListener {
    String Table, KeyCol, KeyVal, Column;
    SQLiteDatabase Database;
    public SpnnerDatabaseLink (SQLiteDatabase inDatabase, String inTable, String inKeyCol, String inKeyVal, String inColumn) {
        this.KeyCol = inKeyCol;
        this.KeyVal = inKeyVal;
        this.Column = inColumn;
        this.Database = inDatabase;
        this.Table = inTable;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        ContentValues data=new ContentValues();
        data.put(Column, Integer.toString(i));
        Database.update(Table, data, KeyCol+" == "+KeyVal, null);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
