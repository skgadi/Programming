package com.skgadi.progtransit001;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

/**
 * Created by gadis on 18-Aug-17.
 */

public class TextChangeUpdateDatabase implements TextWatcher {
    String Table, KeyCol, KeyVal, Column;
    SQLiteDatabase Database;
    public TextChangeUpdateDatabase(SQLiteDatabase inDatabase, String inTable, String inKeyCol, String inKeyVal, String inColumn) {
        this.KeyCol = inKeyCol;
        this.KeyVal = inKeyVal;
        this.Column = inColumn;
        this.Database = inDatabase;
        this.Table = inTable;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        ContentValues data=new ContentValues();
        data.put(Column,editable.toString());
        Database.update(Table, data, KeyCol+" == "+KeyVal, null);
    }
}
