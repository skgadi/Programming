package com.skgadi.progtransit001;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MainActivity extends AppCompatActivity {


    //Defined By SKGadi
    enum GLOBAL_DEVICE_STATE {
        DATABASES,
        STATES,
        EVENTS,
        SETTINGS,
        PROGRAM
    }
    int EVENTS_COUNT = 12;
    int TOTAL_STATES_COUNT = 160;
    Integer[] SettingsDefault = {
            -1, 6, 30, 1, 4, 1, 10, -10, 600, 400, 4, 80, 1920, 80, 420, 5, 90000, 1, 5, 0
    };
    Integer[][] SettingsLimits = {
            {-1, 1}, {0, 23}, {0, 59}, {1, 31}, {1, 12}, {1, 31}, {1, 12}, {-32767, 32767},
            {0, 2550}, {0, 2550}, {0, 255}, {0, 2550}, {0, 2550}, {0, 2550}, {0, 2550}, {0, 255},
            {0, 90000}, {0, 1}, {0, 23}, {0, 59}
    };
    private LinearLayout MV_Databases;
    private LinearLayout MV_States;
    private LinearLayout MV_Events;
    private LinearLayout MV_Settings;
    private Context MainContext;
    SQLiteDatabase Database;


    private RadioGroup FL_ProfilesList;
    private Button FL_ProfileSelectedBtn;
    private EditText FL_NewProfileEdtBox;
    private Button FL_DeleteProfileBtn;

    Spinner SV_CycleSelect;
    TableLayout SV_StatesTable;
    String[] StatesDBPins={
        "D00", "D01", "D02", "D03", "D04", "D05", "D06", "D07", "D08", "D09", "D10", "D11", "Audio"
    };

    TableLayout EV_EventsTable;

    TableLayout ST_SettingsTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Added by SKGadi ---- Initialization
        MainContext = this;

        MV_Databases = (LinearLayout) findViewById(R.id.MV_Databases);
        MV_States = (LinearLayout) findViewById(R.id.MV_States);
        MV_Events = (LinearLayout) findViewById(R.id.MV_Events);
        MV_Settings = (LinearLayout) findViewById(R.id.MV_Settings);
        FL_ProfilesList = (RadioGroup) findViewById(R.id.FL_ProfilesList);
        FL_ProfileSelectedBtn = (Button) findViewById(R.id.FL_ProfileSelectedBtn);
        FL_NewProfileEdtBox = (EditText) findViewById(R.id.FL_NewProfileEdtBox);
        FL_DeleteProfileBtn =  (Button) findViewById(R.id.FL_DeleteProfileBtn);

        SV_CycleSelect = (Spinner) findViewById(R.id.SV_CycleSelect);
        SV_CycleSelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                SyncStatesViewWithCycleType();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                SyncStatesViewWithCycleType();
            }
        });
        SV_StatesTable = (TableLayout) findViewById(R.id.SV_StatesTable);

        EV_EventsTable =  (TableLayout) findViewById(R.id.EV_EventsTable);

        ST_SettingsTable = (TableLayout) findViewById(R.id.ST_SettingsTable);

        GLOBAL_SetViewState(GLOBAL_DEVICE_STATE.DATABASES);
        ListAllFilesList();
    }
    /*----- Common functions -----*/
    private void GLOBAL_SetViewState (GLOBAL_DEVICE_STATE state) {
        MV_Databases.setVisibility(View.GONE);
        MV_States.setVisibility(View.GONE);
        MV_Events.setVisibility(View.GONE);
        MV_Settings.setVisibility(View.GONE);
        switch (state) {
            case DATABASES:
                MV_Databases_ResetView ();
                MV_Databases.setVisibility(View.VISIBLE);
                return;
            case STATES:
                MV_States_ResetView ();
                MV_States.setVisibility(View.VISIBLE);
                return;
            case EVENTS:
                MV_Eents_ResetView();
                MV_Events.setVisibility(View.VISIBLE);
                return;
            case SETTINGS:
                MV_Settings_ResetView();
                MV_Settings.setVisibility(View.VISIBLE);
                return;
            default:
                return;
        }
    }
    private static boolean doesDatabaseExist(Context context, String dbName) {
        File dbFile = context.getDatabasePath(dbName);
        return dbFile.exists();
    }
    private void InsertCaptionButtonsRow (TableLayout Table, String[] Captions) {
        TableRow TempRow = new TableRow(this);
        Button TempButton;
        for (int i=0; i<Captions.length; i++) {
            TempButton = new Button(this);
            TempButton.setText(Captions[i]);
            TempButton.setEnabled(false);
            TempButton.setBackgroundColor(Color.BLACK);
            TempButton.setTextColor(Color.WHITE);
            TempRow.addView(TempButton);;
        }
        Table.addView(TempRow);
    }
    private void AddDisabledButtonView (TableRow Row, String Value) {
        Button TempButton;
        TempButton = new Button(this);
        TempButton.setText(Value);
        TempButton.setEnabled(false);
        TempButton.setBackgroundColor(Color.WHITE);
        TempButton.setTextColor(Color.BLACK);
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setStroke(2, Color.GRAY);
        drawable.setColor(Color.WHITE);
        TempButton.setGravity(Gravity.LEFT|Gravity.CENTER_VERTICAL);
        TempButton.setPadding(25, 0, 25, 0);
        TempButton.setBackgroundDrawable(drawable);
        Row.addView(TempButton);
    }
    private String StringsToTimeDisplay (String Hours, String Minutes) {
        String Out;
        Out = String.format("%02d", Integer.parseInt(Hours)) + ":" +
                String.format("%02d", Integer.parseInt(Minutes));
        return Out;
    }
    private String StringsToPartialDateDisplay (String Day, String Month) {
        String[] Months = getResources().getStringArray(R.array.MonthsArray);
        String Out;
        Out = String.format("%02d", Integer.parseInt(Day)) + "-" +
                Months[Integer.parseInt(Month)-1];
        return Out;
    }
    public void LinkToDatabases (View v) {
        GLOBAL_SetViewState(GLOBAL_DEVICE_STATE.DATABASES);
    }
    public void LinkToStates (View v) {
        GLOBAL_SetViewState(GLOBAL_DEVICE_STATE.STATES);
    }
    public void LinkToEvents (View v) {
        GLOBAL_SetViewState(GLOBAL_DEVICE_STATE.EVENTS);
    }
    public void LinkToSettings (View v) {
        GLOBAL_SetViewState(GLOBAL_DEVICE_STATE.SETTINGS);
    }
    public void LinkToProgrammingWind (View v) {
        GLOBAL_SetViewState(GLOBAL_DEVICE_STATE.SETTINGS);
    }

    /*----- MV_Databases Functions -----*/
    private void MV_Databases_ResetView () {
        FL_ProfileSelectedBtn.setEnabled(false);
        FL_DeleteProfileBtn.setEnabled(false);
        FL_ProfilesList.clearCheck();
    }
    private void ListAllFilesList () {
        FL_DeleteProfileBtn.setEnabled(false);
        FL_ProfileSelectedBtn.setEnabled(false);
        FL_ProfilesList.removeAllViews();
        RadioButton RadioItem;
        String Databases[] = MainContext.databaseList();
        for (int i=0; i<Databases.length; i++) {
            if (!Databases[i].matches(".+-journal")) {
                String mydata = Databases[i];
                Pattern pattern = Pattern.compile("GSK_(.+)");
                Matcher matcher = pattern.matcher(mydata);
                if (matcher.find()) {
                    RadioItem = new RadioButton(this);
                    RadioItem.setText(matcher.group(1));
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
    }
    public void FL_OC_AddNewProfile (View v) {
        String DatabaseName = FL_NewProfileEdtBox.getText().toString();
        if (doesDatabaseExist(MainContext,"GSK_"+DatabaseName))
            Toast.makeText(MainContext, getResources().getString(R.string.error_DatabaseAlreadyExist), Toast.LENGTH_SHORT).show();
        else{
            SQLiteDatabase NewDatabase = MainContext.openOrCreateDatabase("GSK_" + DatabaseName, MODE_PRIVATE, null);
            CreateTables(NewDatabase);
            PopulateEssentialTables(NewDatabase);
            NewDatabase.close();
        }
        ListAllFilesList();
    }
    private void CreateTables (SQLiteDatabase NewDatabase) {
        NewDatabase.execSQL("CREATE TABLE IF NOT EXISTS `States` (" +
                "`Key` INTEGER PRIMARY KEY ASC," +
                "`CycleType`    int NOT NULL," +
                "`Period`       int NOT NULL," +
                "`D00`          int NOT NULL," +
                "`D01`          int NOT NULL," +
                "`D02`          int NOT NULL," +
                "`D03`          int NOT NULL," +
                "`D04`          int NOT NULL," +
                "`D05`          int NOT NULL," +
                "`D06`          int NOT NULL," +
                "`D07`          int NOT NULL," +
                "`D08`          int NOT NULL," +
                "`D09`          int NOT NULL," +
                "`D10`          int NOT NULL," +
                "`D11`          int NOT NULL," +
                "`Audio`        int NOT NULL" +
                ");");
        NewDatabase.execSQL("CREATE TABLE IF NOT EXISTS `Events` (" +
                "`EventID`          int NOT NULL," +
                "`CycleType`        int NOT NULL," +
                "`StartTimeHour`    int NOT NULL," +
                "`StartTimeMin`     int NOT NULL," +
                "`EndTimeHour`      int NOT NULL," +
                "`EndTimeMin`       int NOT NULL," +
                "`Day0`             int NOT NULL," +
                "`Day1`             int NOT NULL," +
                "`Day2`             int NOT NULL," +
                "`Day3`             int NOT NULL," +
                "`Day4`             int NOT NULL," +
                "`Day5`             int NOT NULL," +
                "`Day6`             int NOT NULL," +
                "PRIMARY KEY (`EventID`)"+
                ");");
        NewDatabase.execSQL("CREATE TABLE IF NOT EXISTS `Settings` (" +
                "`Key`          int NOT NULL," +
                "`Setting000`   int NOT NULL," +
                "`Setting001`   int NOT NULL," +
                "`Setting002`   int NOT NULL," +
                "`Setting003`   int NOT NULL," +
                "`Setting004`   int NOT NULL," +
                "`Setting005`   int NOT NULL," +
                "`Setting006`   int NOT NULL," +
                "`Setting007`   int NOT NULL," +
                "`Setting008`   int NOT NULL," +
                "`Setting009`   int NOT NULL," +
                "`Setting010`   int NOT NULL," +
                "`Setting011`   int NOT NULL," +
                "`Setting012`   int NOT NULL," +
                "`Setting013`   int NOT NULL," +
                "`Setting014`   int NOT NULL," +
                "`Setting015`   int NOT NULL," +
                "`Setting016`   int NOT NULL," +
                "`Setting017`   int NOT NULL," +
                "`Setting018`   int NOT NULL," +
                "`Setting019`   int NOT NULL," +
                "PRIMARY KEY (`Key`)"+
                ");");
    }
    private void PopulateEssentialTables(SQLiteDatabase NewDatabase) {
        ContentValues data;
        //----- Populating Events table
        for (int i = 0; i<EVENTS_COUNT; i++) {
            data = new ContentValues();
            data.put("EventID", i);
            data.put("CycleType",0);
            data.put("StartTimeHour",0);
            data.put("StartTimeMin",0);
            data.put("EndTimeHour",0);
            data.put("EndTimeMin",0);
            data.put("Day0", 0);
            data.put("Day1", 0);
            data.put("Day2", 0);
            data.put("Day3", 0);
            data.put("Day4", 0);
            data.put("Day5", 0);
            data.put("Day6", 0);
            NewDatabase.insert("Events", null, data);
        }
        //----- Populating Settings tabe
        data = new ContentValues();
        data.put("Key", 1);
        for (int i = 0; i<SettingsDefault.length; i++)
            data.put("Setting"+String.format("%03d", i), SettingsDefault[i]);
        NewDatabase.insert("Settings", null, data);
    }
    public void FL_OC_DeleteProfile (View v) {
        String DatabaseToDelete = "GSK_" +
                ((RadioButton) findViewById(FL_ProfilesList.getCheckedRadioButtonId())).getText().toString();
        MainContext.deleteDatabase(DatabaseToDelete);
        ListAllFilesList();
    }
    public void FL_OC_RefreshList (View v) {
        ListAllFilesList ();
    }
    public void FL_OC_ProfileSelected (View v) {
        String DatabaseName = "GSK_" +
                ((RadioButton) findViewById(FL_ProfilesList.getCheckedRadioButtonId())).getText().toString();
        Database = MainContext.openOrCreateDatabase(DatabaseName, MODE_PRIVATE, null);
        GLOBAL_SetViewState(GLOBAL_DEVICE_STATE.STATES);
    }
    /*----- MV_States Functions -----*/
    private void MV_States_ResetView () {
        SV_CycleSelect.setSelection(0);
        SyncStatesViewWithCycleType();
    }
    private void SyncStatesViewWithCycleType () {
        String[] Captions = getResources().getStringArray(R.array.StatesCaptions);
        SV_StatesTable.removeAllViews();
        TableRow TempRow;
        Button TempButton;
        String TempStr;

        Cursor TempCursor = null;
        TempCursor = Database.rawQuery("SELECT * FROM `States` WHERE `CycleType` == " +
                SV_CycleSelect.getSelectedItemPosition() +
                ";", null);
        int Iter = 0;
        if (TempCursor.moveToFirst()) {
            while (!TempCursor.isAfterLast()) {
                //----- Captions at regular intervals
                if (Iter%5 == 0) {
                    InsertCaptionButtonsRow (SV_StatesTable, Captions);
                }
                TempRow = new TableRow(this);
                //----- Key
                String KeyVal= TempCursor.getString(TempCursor.getColumnIndex("Key"));
                //----- Period
                TempStr = TempCursor.getString(TempCursor.getColumnIndex("Period"));
                EditText Temp_Period = new EditText(MainContext);
                Temp_Period.setText(TempStr);
                Temp_Period.setInputType(InputType.TYPE_CLASS_NUMBER);
                Temp_Period.setFilters(new InputFilter[]{ new InputFilterMinMax(MainContext, 0, 255)});
                Temp_Period.addTextChangedListener(new TextChangeUpdateDatabase(Database, "States", "Key", KeyVal, "Period"));
                TempRow.addView(Temp_Period);
                //----- Pins
                for (int i=0; i<StatesDBPins.length; i++) {
                    Spinner TempSpinner = new Spinner(MainContext);
                    SimpleImageArrayAdapter adapter = new SimpleImageArrayAdapter(MainContext,
                            new Integer[]{R.drawable.spinner_pattern_off, R.drawable.spinner_pattern_on, R.drawable.spinner_pattern_on_blink});
                    TempSpinner.setAdapter(adapter);
                    TempSpinner.setMinimumHeight(50);
                    TempSpinner.setMinimumWidth(100);
                    TempSpinner.getOnItemSelectedListener();
                    TempSpinner.setGravity(Gravity.RIGHT);
                    TempSpinner.setOnItemSelectedListener(new SpnnerDatabaseLink(
                            Database, "States", "Key", KeyVal, StatesDBPins[i]
                            ));
                    TempStr = TempCursor.getString(TempCursor.getColumnIndex(StatesDBPins[i]));
                    TempSpinner.setSelection(Integer.parseInt(TempStr));
                    TempRow.addView(TempSpinner);
                }
                TempButton = new Button(this);
                TempButton.setText(R.string.StatesView_Delete);
                TempButton.setBackgroundColor(Color.argb(255, 255, 13, 118));
                TempButton.setOnClickListener(new DeleteSatesRow(
                        Database, "States", "Key", KeyVal, TempRow
                ));
                TempRow.addView(TempButton);

                SV_StatesTable.addView(TempRow);
                TempCursor.moveToNext();
                Iter++;
            }
            //----- End captions
            InsertCaptionButtonsRow (SV_StatesTable, Captions);
        }
    }
    public void AppendAStateRecord (View v) {
        Cursor TempCursor = null;
        TempCursor = Database.rawQuery("SELECT * FROM `States`;", null);
        if (TempCursor.getCount() <= TOTAL_STATES_COUNT) {
            Database.execSQL("INSERT INTO `States` (`CycleType`, `Period`, `D00`, `D01`, `D02`," +
                    " `D03`, `D04`, `D05`, `D06`, `D07`, `D08`, `D09`, `D10`, `D11`, `Audio`)" +
                    " VALUES (" + SV_CycleSelect.getSelectedItemPosition() +
                    ", 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);");
            SyncStatesViewWithCycleType();
        } else {
            Toast.makeText(MainContext,getResources().getString(
                    R.string.Error_ReachedStatesLimit), Toast.LENGTH_SHORT).show();
        }
    }
    /*----- MV_Events Functions -----*/
    private void PopulateEventsTable() {
        String[] Captions = getResources().getStringArray(R.array.EventsTableCaptions);
        TableRow TempRow;
        Button TempButton;
        Spinner TempSpinner;
        CheckBox TempCheckBox;
        String EventID;
        String[] CycleNames = getResources().getStringArray(R.array.CyclesNames);
        EV_EventsTable.removeAllViews();

        Cursor TempCursor = null;
        Database.getPath();
        TempCursor = Database.rawQuery("SELECT * FROM `Events`", null);
        int Iter = 0;
        if (TempCursor.moveToFirst()) {
            while (!TempCursor.isAfterLast()) {
                if (Iter % 5 == 0) {
                    InsertCaptionButtonsRow(EV_EventsTable, Captions);
                }
                TempRow = new TableRow(MainContext);
                //----- Even ID
                EventID = TempCursor.getString(TempCursor.getColumnIndex("EventID"));
                AddDisabledButtonView(TempRow, String.format("%02d", Integer.parseInt(EventID)));
                //----- Cycle Type
                TempSpinner = new Spinner(MainContext);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                        android.R.layout.simple_spinner_item, CycleNames);
                TempSpinner.setAdapter(adapter);
                TempSpinner.setSelection(Integer.parseInt(
                        TempCursor.getString(TempCursor.getColumnIndex("CycleType"))));
                TempSpinner.setOnItemSelectedListener(new SpnnerDatabaseLink(
                        Database, "Events", "EventID", EventID, "CycleType"
                ));
                TempRow.addView(TempSpinner);
                //----- Start Time
                TempButton = new Button(MainContext);
                TempButton.setText(StringsToTimeDisplay(
                        TempCursor.getString(TempCursor.getColumnIndex("StartTimeHour")),
                        TempCursor.getString(TempCursor.getColumnIndex("StartTimeMin"))));
                new SetTimeWithDBLink(TempButton, MainContext, Database, "Events", "EventID", EventID,
                        "StartTimeHour", "StartTimeMin");
                TempRow.addView(TempButton);
                //----- Finish Time
                TempButton = new Button(MainContext);
                TempButton.setText(StringsToTimeDisplay(
                        TempCursor.getString(TempCursor.getColumnIndex("EndTimeHour")),
                        TempCursor.getString(TempCursor.getColumnIndex("EndTimeMin"))));
                new SetTimeWithDBLink(TempButton, MainContext, Database, "Events", "EventID", EventID,
                        "EndTimeHour", "EndTimeMin");
                TempRow.addView(TempButton);
                //------ Days
                for (int j = 0; j < 7; j++) {
                    TempCheckBox = new CheckBox(MainContext);
                    //TempCheckBox.setChecked(true);
                    TempCheckBox.setMinimumWidth(100);
                    TempCheckBox.setChecked(false);
                    if (Integer.parseInt(TempCursor.getString(TempCursor.getColumnIndex("Day"+j)))==1)
                        TempCheckBox.setChecked(true);
                    TempCheckBox.setOnCheckedChangeListener(new CheckboxDBLink(
                            Database, "Events", "EventID", EventID, "Day"+j));
                    TempRow.addView(TempCheckBox);
                    //TempCheckBox.setGravity(Gravity.CENTER_HORIZONTAL);
                }

                //----- Finished All the columns
                EV_EventsTable.addView(TempRow);
                TempCursor.moveToNext();
                Iter++;
            }
            InsertCaptionButtonsRow(EV_EventsTable, Captions);
        }
    }
    private void MV_Eents_ResetView () {
        PopulateEventsTable();
    }
    /*----- MV_Settings Functions -----*/
    private void PopulateSettingsTable (){
        String[] Captions = getResources().getStringArray(R.array.SettingsCaptions);
        String[] Legends = getResources().getStringArray(R.array.SettingsLegends);
        TableRow TempRow;
        EditText TempEditText;
        Button TempButton;
        Spinner TempSpinner;
        CheckBox TempCheckBox;
        Integer Key;
        Integer Value;
        Integer Iter=0;
        ST_SettingsTable.removeAllViews();
        Cursor TempCursor = null;
        TempCursor = Database.rawQuery("SELECT * FROM `Settings`;", null);
        TempCursor.moveToFirst();
        Key = TempCursor.getInt(TempCursor.getColumnIndex("Key"));

        InsertCaptionButtonsRow(ST_SettingsTable, Captions);
        //----- Timezone sign
        TempRow = new TableRow(MainContext);
        AddDisabledButtonView(TempRow, Legends[Iter]);
        String[] Signs = getResources().getStringArray(R.array.Signs);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, Signs);
        TempSpinner = new Spinner(MainContext);
        TempSpinner.setAdapter(adapter);
        Value = TempCursor.getInt(TempCursor.getColumnIndex("Setting000"));
        TempSpinner.setSelection(Value);
        TempSpinner.setOnItemSelectedListener(new SpnnerDatabaseLink(
                Database, "Settings", "Key", Key.toString(), "Setting000"
        ));
        Iter++;
        TempRow.addView(TempSpinner);
        ST_SettingsTable.addView(TempRow);
        //----- Timezone value
        TempRow = new TableRow(MainContext);
        AddDisabledButtonView(TempRow, Legends[Iter]);
        TempButton = new Button(MainContext);
        TempButton.setText(StringsToTimeDisplay(
                TempCursor.getString(TempCursor.getColumnIndex("Setting001")),
                TempCursor.getString(TempCursor.getColumnIndex("Setting002"))));
        new SetTimeWithDBLink(TempButton, MainContext, Database, "Settings", "Key", Key.toString(),
                "Setting001", "Setting002");
        Iter++;
        TempRow.addView(TempButton);
        ST_SettingsTable.addView(TempRow);
        //----- Daylight Start
        TempRow = new TableRow(MainContext);
        AddDisabledButtonView(TempRow, Legends[Iter]);
        TempButton = new Button(MainContext);
        TempButton.setText(StringsToPartialDateDisplay(
                TempCursor.getString(TempCursor.getColumnIndex("Setting003")),
                TempCursor.getString(TempCursor.getColumnIndex("Setting004"))));
        new SetDateWithDBLink(TempButton, MainContext, Database, "Settings", "Key", Key.toString(),
                "Setting003", "Setting004");
        Iter++;
        TempRow.addView(TempButton);
        ST_SettingsTable.addView(TempRow);
        //----- Daylight End
        TempRow = new TableRow(MainContext);
        AddDisabledButtonView(TempRow, Legends[Iter]);
        TempButton = new Button(MainContext);
        TempButton.setText(StringsToPartialDateDisplay(
                TempCursor.getString(TempCursor.getColumnIndex("Setting005")),
                TempCursor.getString(TempCursor.getColumnIndex("Setting006"))));
        new SetDateWithDBLink(TempButton, MainContext, Database, "Settings", "Key", Key.toString(),
                "Setting005", "Setting006");
        Iter++;
        TempRow.addView(TempButton);
        ST_SettingsTable.addView(TempRow);
        //----- EditNumbers
        for (int i=0; i<10; i++) {
            if (Iter%5 == 0)
                InsertCaptionButtonsRow(ST_SettingsTable, Captions);
            TempRow = new TableRow(MainContext);
            AddDisabledButtonView(TempRow, Legends[Iter]);
            TempEditText = new EditText(MainContext);
            TempEditText.setText(TempCursor.getString(TempCursor.getColumnIndex("Setting"+String.format("%03d", i+7))));
            TempEditText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED);
            TempEditText.setFilters(new InputFilter[]{new InputFilterMinMax(
                    MainContext, SettingsLimits[i+7][0], SettingsLimits[i+7][1]
            )});
            TempEditText.addTextChangedListener(new TextChangeUpdateDatabase(
                    Database, "Settings", "Key", Key.toString(), "Setting"+String.format("%03d", i+7)));
            Iter++;
            TempRow.addView(TempEditText);
            ST_SettingsTable.addView(TempRow);
        }
        //----- Sync at the start
        TempRow = new TableRow(MainContext);
        AddDisabledButtonView(TempRow, Legends[Iter]);
        TempCheckBox = new CheckBox(MainContext);
        TempCheckBox.setMinimumWidth(100);
        TempCheckBox.setChecked(false);
        if (TempCursor.getInt(TempCursor.getColumnIndex("Setting017"))==1)
            TempCheckBox.setChecked(true);
        TempCheckBox.setOnCheckedChangeListener(new CheckboxDBLink(
                Database, "Settings", "Key", Key.toString(), "Setting017"));
        Iter++;
        TempRow.addView(TempCheckBox);
        ST_SettingsTable.addView(TempRow);
        //----- Sync every at local time
        InsertCaptionButtonsRow(ST_SettingsTable, Captions);
        TempRow = new TableRow(MainContext);
        AddDisabledButtonView(TempRow, Legends[Iter]);
        TempButton = new Button(MainContext);
        TempButton.setText(StringsToTimeDisplay(
                TempCursor.getString(TempCursor.getColumnIndex("Setting018")),
                TempCursor.getString(TempCursor.getColumnIndex("Setting019"))));
        new SetTimeWithDBLink(TempButton, MainContext, Database, "Settings", "Key", Key.toString(),
                "Setting018", "Setting019");
        Iter++;
        TempRow.addView(TempButton);
        ST_SettingsTable.addView(TempRow);
        //----- End captions
        InsertCaptionButtonsRow(ST_SettingsTable, Captions);
    }
    private void MV_Settings_ResetView () {
        PopulateSettingsTable();
    }
}
