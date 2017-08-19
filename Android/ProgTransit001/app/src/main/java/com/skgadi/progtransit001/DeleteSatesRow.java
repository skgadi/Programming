package com.skgadi.progtransit001;

import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.TableRow;

/**
 * Created by gadis on 18-Aug-17.
 */

public class DeleteSatesRow implements View.OnClickListener {
    private SQLiteDatabase Database;
    private String Table, KeyCol, KeyVal;
    private TableRow TempRow;

    public DeleteSatesRow (SQLiteDatabase inDatabase, String inTable, String inKeyCol, String inKeyVal, TableRow inTempRow) {
        Database = inDatabase;
        Table = inTable;
        KeyCol = inKeyCol;
        KeyVal = inKeyVal;
        TempRow = inTempRow;
    }
    @Override
    public void onClick(View view) {
        TempRow.removeAllViews();
        Database.delete(Table, KeyCol + "==" + KeyVal, null);
    }
}
