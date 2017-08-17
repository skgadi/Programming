package com.skgadi.progtransit001;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;


public class MainActivity extends AppCompatActivity {

    private LinearLayout MV_Databases;
    private LinearLayout MV_States;
    private RadioGroup FL_ProfilesList;
    private Button FL_ProfileSelectedBtn;
    private EditText FL_NewProfileEdtBox;
    private Button FL_DeleteProfileBtn;

    Spinner SV_CycleSelect;


    private Context context;

    //Defined By SKGadi
    enum GLOBAL_DEVICE_STATE {
        NONE,
        FILES_LIST,
        STATES,
        CYCLE_TYPES,
        EVENTS,
        PROGRAM
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Added by SKGadi ---- Initialization
        Context context = this;
        MV_Databases = (LinearLayout) findViewById(R.id.MV_Databases);
        MV_States = (LinearLayout) findViewById(R.id.MV_States);
        FL_ProfilesList = (RadioGroup) findViewById(R.id.FL_ProfilesList);
        FL_ProfileSelectedBtn = (Button) findViewById(R.id.FL_ProfileSelectedBtn);
        FL_NewProfileEdtBox = (EditText) findViewById(R.id.FL_NewProfileEdtBox);
        FL_DeleteProfileBtn =  (Button) findViewById(R.id.FL_DeleteProfileBtn);

        Spinner SV_CycleSelect = (Spinner) findViewById(R.id.SV_CycleSelect);
        GLOBAL_SetViewState(GLOBAL_DEVICE_STATE.FILES_LIST);
        ListAllFilesList();
    }
    /*----- Global view functions -----*/
    private void GLOBAL_SetViewState (GLOBAL_DEVICE_STATE state) {
        GLOBAL_SetInitialCondition();
        switch (state) {
            case FILES_LIST:
                MV_Databases.setVisibility(View.VISIBLE);
                return;
            case STATES:
                MV_States.setVisibility(View.VISIBLE);
                return;
            default:
                return;
        }
    }
    private void GLOBAL_SetInitialCondition() {
        MV_Databases.setVisibility(View.GONE);
        MV_States.setVisibility(View.GONE);
        FL_ProfileSelectedBtn.setEnabled(false);
        FL_ProfilesList.clearCheck();
    }
    /*----- MV_Databases Functions -----*/
    private void ListAllFilesList () {
        FL_DeleteProfileBtn.setEnabled(false);
        FL_ProfileSelectedBtn.setEnabled(false);
        FL_ProfilesList.removeAllViews();
        RadioButton RadioItem;
        String Databases[] = getApplicationContext().databaseList();
        for (int i=0; i<Databases.length; i++) {
            if (!Databases[i].matches(".+-journal")) {
                RadioItem = new RadioButton(this);
                RadioItem.setText(Databases[i]);
                RadioItem.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        FL_ProfileSelectedBtn.setEnabled(true);
                        FL_DeleteProfileBtn.setEnabled(true);
                    }
                });
                FL_ProfilesList.addView(RadioItem);
            }
        }
    }
    public void FL_OC_AddNewProfile (View v) {
        String DatabaseName = FL_NewProfileEdtBox.getText().toString();
        if (doesDatabaseExist(getApplicationContext(),"GSK_"+DatabaseName))
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.error_DatabaseAlreadyExist), Toast.LENGTH_SHORT).show();
        else{
            SQLiteDatabase NewDatabase = getApplicationContext().openOrCreateDatabase("GSK_" + DatabaseName, MODE_PRIVATE, null);
            CreateTables(getApplicationContext(), NewDatabase);
            NewDatabase.close();
        }
        ListAllFilesList();
    }
    private void CreateTables (Context Context, SQLiteDatabase Database) {
        Database.execSQL("CREATE TABLE IF NOT EXISTS `States` (" +
                "`CycleType`    int(3) NOT NULL," +
                "`Period`       int(3) NOT NULL," +
                "`D00`          int(1) NOT NULL," +
                "`D01`          int(1) NOT NULL," +
                "`D02`          int(1) NOT NULL," +
                "`D03`          int(1) NOT NULL," +
                "`D04`          int(1) NOT NULL," +
                "`D05`          int(1) NOT NULL," +
                "`D06`          int(1) NOT NULL," +
                "`D07`          int(1) NOT NULL," +
                "`D08`          int(1) NOT NULL," +
                "`D09`          int(1) NOT NULL," +
                "`D10`          int(1) NOT NULL," +
                "`D11`          int(1) NOT NULL," +
                "`Audio`        int(1) NOT NULL" +
                ");");
        Database.execSQL("CREATE TABLE IF NOT EXISTS `Events` (" +
                "`EventID`      int(3) NOT NULL," +
                "`StartTime`    DATETIME NOT NULL," +
                "`EndTime`      DATETIME NOT NULL," +
                "`CycleType`    int(3) NOT NULL," +
                "`Sunday`       int(1) NOT NULL," +
                "`Monday`       int(1) NOT NULL," +
                "`Tuesday`      int(1) NOT NULL," +
                "`Wednesday`    int(1) NOT NULL," +
                "`Thursday`     int(1) NOT NULL," +
                "`Friday`       int(1) NOT NULL," +
                "`Saturday`     int(1) NOT NULL," +
                "PRIMARY KEY (`EventID`)"+
                ");");
        Database.execSQL("CREATE TABLE IF NOT EXISTS `Settings` (" +
                "`Attribute`    VARCHAR(20) NOT NULL," +
                "`Value`        VARCHAR(20) NOT NULL," +
                "PRIMARY KEY (`Attribute`)"+
                ");");
    }
    public void FL_OC_DeleteProfile (View v) {
        String DatabaseToDelete =
                ((RadioButton) findViewById(FL_ProfilesList.getCheckedRadioButtonId())).getText().toString();
        //Toast.makeText(getApplicationContext(), DatabaseToDelete, Toast.LENGTH_SHORT).show();
        getApplicationContext().deleteDatabase(DatabaseToDelete);
        ListAllFilesList();
    }
    public void FL_OC_RefreshList (View v) {
        ListAllFilesList ();
    }
    public void FL_OC_ProfileSelected (View v) {
        GLOBAL_SetViewState(GLOBAL_DEVICE_STATE.STATES);
    }
    /*----- MV_States Functions -----*/
    public void BackToFilesList (View v) {
        GLOBAL_SetViewState(GLOBAL_DEVICE_STATE.FILES_LIST);
    }
    /*----- General Functions -----*/
    private static boolean doesDatabaseExist(Context context, String dbName) {
        File dbFile = context.getDatabasePath(dbName);
        return dbFile.exists();
    }
}
