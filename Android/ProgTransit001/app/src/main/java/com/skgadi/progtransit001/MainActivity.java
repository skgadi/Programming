package com.skgadi.progtransit001;

import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbManager;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.hoho.android.usbserial.driver.CdcAcmSerialDriver;
import com.hoho.android.usbserial.driver.ProbeTable;
import com.hoho.android.usbserial.driver.UsbSerialDriver;
import com.hoho.android.usbserial.driver.UsbSerialPort;
import com.hoho.android.usbserial.driver.UsbSerialProber;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MainActivity extends AppCompatActivity {


    //Defined By SKGadi
    private  enum GLOBAL_DEVICE_STATE {
        NONE,
        DATABASES,
        STATES,
        EVENTS,
        SETTINGS,
        PROGRAM
    }
    int EVENTS_COUNT = 24;
    int STATES_COUNT = 160;
    int CYCLE_TYPES_COUNT = 17;
    Integer[] SettingsDefault = {
            0, 6, 30, 1, 4, 1, 10, -10, 600, 400, 4, 80, 1920, 80, 420, 5, 1, 90000, 5, 0
    };
    Integer[][] SettingsLimits = {
            {-1, 1}, {0, 23}, {0, 59}, {1, 31}, {1, 12}, {1, 31}, {1, 12}, {-32767, 32767},
            {0, 2550}, {0, 2550}, {0, 255}, {0, 2550}, {0, 2550}, {0, 2550}, {0, 2550}, {0, 255},
            {0, 1}, {0, 90000}, {0, 23}, {0, 59}
    };
    byte[] Code;
    String BaseTitle;
    String[] Toasts;
    String[] Logs;
    String[] Titles;
    String SelectedDatabase;
    //private LinearLayout MV_Loading;
    private LinearLayout MV_Databases;
    private LinearLayout MV_States;
    private LinearLayout MV_Events;
    private LinearLayout MV_Settings;
    private LinearLayout MV_Program;
    private Context MainContext;
    SQLiteDatabase Database;
    private GLOBAL_DEVICE_STATE PreviousView;
    Integer BackHitsToExit=0;
    boolean EnableBackButton;
    @Override
    public void onBackPressed() {
        if (EnableBackButton) {
            if (((PreviousView == GLOBAL_DEVICE_STATE.NONE) && (BackHitsToExit > 0))
                    || (PreviousView != GLOBAL_DEVICE_STATE.NONE)) {
                BackHitsToExit = 0;
                GLOBAL_SetViewState(PreviousView);
            } else {
                Toast.makeText(MainContext, Toasts[3], Toast.LENGTH_SHORT).show();
                BackHitsToExit++;
            }
        }
    }


    private RadioGroup FL_ProfilesList;
    private Button FL_ProfileSelectedBtn;
    private EditText FL_NewProfileEdtBox;
    private Button FL_DeleteProfileBtn;
    private Button FL_ShareDatabase;

    Spinner SV_CycleSelect;
    TableLayout SV_StatesTable;
    String[] StatesDBPins={
        "D00", "D01", "D02", "D03", "D04", "D05", "D06", "D07", "D08", "D09", "D10", "D11", "Audio"
    };

    TableLayout EV_EventsTable;

    TableLayout ST_SettingsTable;

    EditText PR_Delay;
    Button PR_LinkToSettings;
    Button PR_LinkToDatabses;
    Button PR_LinkToStates;
    Button PR_LinkToEvents;
    //Button PR_Connect;
    //Button PR_Disconnect;
    Button PR_Program;
    TextView PR_Log;
    TableLayout PR_DelayTable;

    //----- Communication and other from prev program
    protected UsbManager manager;
    ProbeTable customTable;
    UsbSerialProber prober;
    List<UsbSerialDriver> drivers;
    UsbSerialDriver driver;
    UsbDeviceConnection connection;
    private UsbSerialPort port;
    private ProgressBar ProgramProgressBar;
    ToggleButton PR_ToggleConnect;
    Button ProgramButton;
    private enum DEVICE_STATE {
        DISCONNECTED,
        CONNECTED
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //----- Initialization
        MainContext = this;
        BaseTitle = getResources().getString(R.string.app_name);
        Titles = getResources().getStringArray(R.array.Titles);
        Toasts = getResources().getStringArray(R.array.Toasts);
        Logs = getResources().getStringArray(R.array.Logs);
        EnableBackButton = true;
        //MV_Loading = (LinearLayout) findViewById(R.id.MV_Loading);
        MV_Databases = (LinearLayout) findViewById(R.id.MV_Databases);
        MV_States = (LinearLayout) findViewById(R.id.MV_States);
        MV_Events = (LinearLayout) findViewById(R.id.MV_Events);
        MV_Settings = (LinearLayout) findViewById(R.id.MV_Settings);
        MV_Program = (LinearLayout) findViewById(R.id.MV_Program);

        FL_ProfilesList = (RadioGroup) findViewById(R.id.FL_ProfilesList);
        FL_ProfileSelectedBtn = (Button) findViewById(R.id.FL_ProfileSelectedBtn);
        FL_NewProfileEdtBox = (EditText) findViewById(R.id.FL_NewProfileEdtBox);
        FL_DeleteProfileBtn =  (Button) findViewById(R.id.FL_DeleteProfileBtn);
        FL_ShareDatabase = (Button) findViewById(R.id.FL_ShareDatabase);


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

        PR_LinkToSettings = (Button) findViewById(R.id.PR_LinkToSettings);
        PR_LinkToDatabses = (Button) findViewById(R.id.PR_LinkToDatabses);
        PR_LinkToStates = (Button) findViewById(R.id.PR_LinkToStates);
        PR_LinkToEvents = (Button) findViewById(R.id.PR_LinkToEvents);
        //PR_Connect = (Button) findViewById(R.id.PR_Connect);
        //PR_Disconnect = (Button) findViewById(R.id.PR_Disconnect);
        PR_Program = (Button) findViewById(R.id.PR_Program);
        PR_Log = (TextView) findViewById(R.id.PR_Log);
        PR_DelayTable = (TableLayout) findViewById(R.id.PR_DelayTable);
        //----- Taken from another program
        ProgramButton = (Button) findViewById(R.id.PR_Program);
        PR_ToggleConnect = (ToggleButton) findViewById(R.id.PR_ToggleConnect);
        ProgramProgressBar = (ProgressBar) findViewById(R.id.PR_ProgressBar);

        customTable = new ProbeTable();
        customTable.addProduct(0x4D8, 0x000A, CdcAcmSerialDriver.class);
        prober = new UsbSerialProber(customTable);
        PR_ToggleConnect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    int status = ConnectUSB ();
                    if (status == 2) setViewToConnect(true);
                    else setViewToConnect(false);
                } else {
                    try {
                        setViewToConnect(false);;
                        port.close();
                        Toast.makeText(MainActivity.this, R.string.success_device_disconnected,Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(MainActivity.this, R.string.error_device_disconnect,Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        GLOBAL_SetViewState(GLOBAL_DEVICE_STATE.DATABASES);
        ListAllDatabases();
    }
    /*----- Common functions -----*/
    private void GLOBAL_SetViewState (GLOBAL_DEVICE_STATE state) {
        MV_Databases.setVisibility(View.GONE);
        MV_States.setVisibility(View.GONE);
        MV_Events.setVisibility(View.GONE);
        MV_Settings.setVisibility(View.GONE);
        MV_Program.setVisibility(View.GONE);
        switch (state) {
            case NONE:
                finish();
                return;
            case DATABASES:
                PreviousView = GLOBAL_DEVICE_STATE.NONE;
                MV_Databases_ResetView ();
                MV_Databases.setVisibility(View.VISIBLE);
                return;
            case STATES:
                PreviousView = GLOBAL_DEVICE_STATE.DATABASES;
                MV_States_ResetView ();
                MV_States.setVisibility(View.VISIBLE);
                return;
            case EVENTS:
                PreviousView = GLOBAL_DEVICE_STATE.STATES;
                MV_Events_ResetView();
                MV_Events.setVisibility(View.VISIBLE);
                return;
            case SETTINGS:
                PreviousView = GLOBAL_DEVICE_STATE.EVENTS;
                MV_Settings_ResetView();
                MV_Settings.setVisibility(View.VISIBLE);
                return;
            case PROGRAM:
                PreviousView = GLOBAL_DEVICE_STATE.EVENTS;
                MV_Program_ResetView();
                MV_Program.setVisibility(View.VISIBLE);
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
    public void LinkToProgramming (View v) {
        GLOBAL_SetViewState(GLOBAL_DEVICE_STATE.PROGRAM);
    }

    /*----- MV_Databases Functions -----*/
    private void MV_Databases_ResetView () {
        setEnabledDBLinkedButtons(false);
        FL_ProfilesList.clearCheck();
    }
    private void setEnabledDBLinkedButtons (boolean set) {
        getSupportActionBar().setTitle(BaseTitle + " :: " + Titles[0] + " :: " + SelectedDatabase);
        FL_ProfileSelectedBtn.setEnabled(set);
        FL_DeleteProfileBtn.setEnabled(set);
        FL_ShareDatabase.setEnabled(set);
    }
    private void ListAllDatabases() {
        SelectedDatabase = "";
        MV_Databases_ResetView();
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
                            SelectedDatabase =
                                    ((RadioButton) findViewById(FL_ProfilesList.getCheckedRadioButtonId())).getText().toString();
                            setEnabledDBLinkedButtons(true);
                        }
                    });
                    FL_ProfilesList.addView(RadioItem);
                }
            }
        }
    }
    public void FL_OC_AddNewProfile (View v) {
        String DatabaseName = FL_NewProfileEdtBox.getText().toString();
        if (DatabaseName.isEmpty())
            Toast.makeText(MainContext, Toasts[4], Toast.LENGTH_SHORT).show();
        else {
            if (doesDatabaseExist(MainContext, "GSK_" + DatabaseName))
                Toast.makeText(MainContext, Toasts[0], Toast.LENGTH_SHORT).show();
            else {
                SQLiteDatabase NewDatabase = MainContext.openOrCreateDatabase("GSK_" + DatabaseName, MODE_PRIVATE, null);
                CreateTables(NewDatabase);
                PopulateEssentialTables(NewDatabase);
                NewDatabase.close();
                ListAllDatabases();
            }
        }
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
            data.put("StartTimeHour",-1);
            data.put("StartTimeMin",-1);
            data.put("EndTimeHour",-1);
            data.put("EndTimeMin",-1);
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
        ListAllDatabases();
    }
    public void FL_OC_RefreshList (View v) {
        ListAllDatabases();
    }
    public void FL_OC_ProfileSelected (View v) {
        String DatabaseName = "GSK_" +
                ((RadioButton) findViewById(FL_ProfilesList.getCheckedRadioButtonId())).getText().toString();
        Database = MainContext.openOrCreateDatabase(DatabaseName, MODE_PRIVATE, null);
        GLOBAL_SetViewState(GLOBAL_DEVICE_STATE.STATES);
    }
    public void FL_ShareProfile (View v) {
    }
    /*----- MV_States Functions -----*/
    private void MV_States_ResetView () {
        getSupportActionBar().setTitle(BaseTitle + " :: " + Titles[1] + " :: " + SelectedDatabase);
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
                //----- State Num
                AddDisabledButtonView(TempRow, String.format("%02d", Iter+1));
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
                            new Integer[]{R.drawable.spinner_pattern_off,
                                    R.drawable.spinner_pattern_on,
                                    R.drawable.spinner_pattern_on_blink});
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
        if (TempCursor.getCount() <= STATES_COUNT) {
            Database.execSQL("INSERT INTO `States` (`CycleType`, `Period`, `D00`, `D01`, `D02`," +
                    " `D03`, `D04`, `D05`, `D06`, `D07`, `D08`, `D09`, `D10`, `D11`, `Audio`)" +
                    " VALUES (" + SV_CycleSelect.getSelectedItemPosition() +
                    ", 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);");
            SyncStatesViewWithCycleType();
        } else {
            Toast.makeText(MainContext,Toasts[1], Toast.LENGTH_SHORT).show();
        }
    }
    public void SV_RefreshStatesList (View v) {
        SyncStatesViewWithCycleType();
    }
    /*----- MV_Events Functions -----*/
    private void MV_Events_ResetView() {
        getSupportActionBar().setTitle(BaseTitle + " :: " + Titles[2] + " :: " + SelectedDatabase);
        PopulateEventsTable();
    }
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
                AddDisabledButtonView(TempRow, String.format("%02d", Integer.parseInt(EventID)+1));
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
    /*----- MV_Settings Functions -----*/
    private void MV_Settings_ResetView () {
        getSupportActionBar().setTitle(BaseTitle + " :: " + Titles[3] + " :: " + SelectedDatabase);
        PopulateSettingsTable();
    }
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
        TempCursor = Database.rawQuery("SELECT * FROM `Settings` WHERE `Key` == 1;", null);
        TempCursor.moveToFirst();
        Key = 1;//TempCursor.getInt(TempCursor.getColumnIndex("Key"));

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
        for (int i=0; i<9; i++) {
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
        if (TempCursor.getInt(TempCursor.getColumnIndex("Setting016"))==1)
            TempCheckBox.setChecked(true);
        TempCheckBox.setOnCheckedChangeListener(new CheckboxDBLink(
                Database, "Settings", "Key", Key.toString(), "Setting016"));
        Iter++;
        TempRow.addView(TempCheckBox);
        ST_SettingsTable.addView(TempRow);
        //----- GPS_SYNC_EVERY_x_SECONDS
        TempRow = new TableRow(MainContext);
        AddDisabledButtonView(TempRow, Legends[Iter]);
        TempEditText = new EditText(MainContext);
        TempEditText.setText(TempCursor.getString(TempCursor.getColumnIndex("Setting017")));
        TempEditText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED);
        TempEditText.setFilters(new InputFilter[]{new InputFilterMinMax(
                MainContext, SettingsLimits[17][0], SettingsLimits[17][1]
        )});
        TempEditText.addTextChangedListener(new TextChangeUpdateDatabase(
                Database, "Settings", "Key", Key.toString(), "Setting017"));
        Iter++;
        TempRow.addView(TempEditText);
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
        //Iter++;
        TempRow.addView(TempButton);
        ST_SettingsTable.addView(TempRow);
        //----- End captions
        InsertCaptionButtonsRow(ST_SettingsTable, Captions);
    }
    /*----- MV_Program Functions -----*/
    private void MV_Program_ResetView() {
        getSupportActionBar().setTitle(BaseTitle + " :: " + Titles[4] + " :: " + SelectedDatabase);
        setViewToConnect(false);
        //----- Delay setting update option
        PR_DelayTable.removeAllViews();
        PR_Delay = new EditText(MainContext);
        Cursor TempCursor = null;
        TempCursor = Database.rawQuery("SELECT * FROM `Settings` WHERE `Key` == 1;", null);
        TempCursor.moveToFirst();
        PR_Delay.setText(TempCursor.getString(TempCursor.getColumnIndex("Setting007")));
        TempCursor.close();
        PR_Delay.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED);
        PR_Delay.setFilters(new InputFilter[]{new InputFilterMinMax(
                MainContext, SettingsLimits[7][0], SettingsLimits[7][1]
        )});
        PR_Delay.addTextChangedListener(new TextChangeUpdateDatabase(
                Database, "Settings", "Key", "1", "Setting007"));
        PR_Delay.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        PR_DelayTable.addView(PR_Delay);
    }
    private void setViewToConnect(boolean set) {
        EnableBackButton = !set;
        PR_LinkToSettings.setEnabled(!set);
        PR_LinkToDatabses.setEnabled(!set);
        PR_LinkToStates.setEnabled(!set);
        PR_LinkToEvents.setEnabled(!set);
        PR_DelayTable.setEnabled(!set);
        for (int i = 0; i < PR_DelayTable.getChildCount(); i++) {
            View child = PR_DelayTable.getChildAt(i);
            child.setEnabled(!set);
        }
        PR_ToggleConnect.setChecked(set);
        PR_Program.setEnabled(set);
    }
    private void GenerateCodeFromDatabase (SQLiteDatabase Database) {
        Code = new byte[1024];
        byte[] TempIntArr;
        Integer TempInt;
        Integer Pointer00=0;
        Integer Pointer01;
        Integer[] TempSizeOfVars = {1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 3, 1, 1};
        Integer[] TempDevideVar =  {1, 1, 1, 1, 1, 10, 10, 1, 10, 10, 10, 10, 1, 1, 1, 1, 1};
        Cursor TempCursor = null;
        TempCursor = Database.rawQuery("SELECT * FROM `Settings` WHERE `Key` == 1;", null);
        TempCursor.moveToFirst();
        //----- TimeZone
        TempInt = 60* GetIntFromCursor(TempCursor, "Setting001") +
                GetIntFromCursor(TempCursor, "Setting002");
        if (GetIntFromCursor(TempCursor, "Setting000")==0) {
            TempInt = -1*TempInt;
        }
        TempIntArr = SplitIntToByteArray(TempInt, 2);
        Code[Pointer00++] = TempIntArr[0];
        Code[Pointer00++] = TempIntArr[1];
        //----- DayLightStart and DayLightEnd
        for (int i=0; i<4; i++) {
            TempInt = GetIntFromCursor(TempCursor, "Setting"+String.format("%03d", i+3));
            TempIntArr = SplitIntToByteArray(TempInt/TempDevideVar[i], TempSizeOfVars[i]);
            for (int j=0; j< TempSizeOfVars[i]; j++) {
                Code[Pointer00++] = TempIntArr[j];
            }
        }
        //----- Century
        TempInt = Calendar.getInstance().get(Calendar.YEAR);
        Code[Pointer00++] = (byte)(TempInt/100);
        //----- CycleDelay, BlinkTimings, etc.
        for (int i=4; i<15; i++) {
            TempInt = GetIntFromCursor(TempCursor, "Setting"+String.format("%03d", i+3));
            TempIntArr = SplitIntToByteArray(TempInt/TempDevideVar[i], TempSizeOfVars[i]);
            for (int j=0; j< TempSizeOfVars[i]; j++) {
                Code[Pointer00++] = TempIntArr[j];
            }
        }
        //----- GPS_SYNC_EVERY_DAY_AT_LOCAL_TIME
        Code[Pointer00++] = (byte) GetIntFromCursor(TempCursor, "Setting019");
        Code[Pointer00++] = (byte) GetIntFromCursor(TempCursor, "Setting018");
        //Log.d("I", Pointer00.toString());
        //----- STATES DATABASE
        Pointer00 = 23;
        TempCursor.close();
        Pointer01 = 0;
        for (int i = 0; i< CYCLE_TYPES_COUNT; i++) {
            TempCursor = Database.rawQuery("SELECT * FROM `States` WHERE `CycleType` == " +
                    i + ";", null);
            TempCursor.moveToFirst();
            for (int j=0; j<TempCursor.getCount(); j++) {
                TempInt = GetIntFromCursor(TempCursor, "Audio");
                //----- AUDIO NORMAL
                if (TempInt == 1) Code[Pointer00 + (Pointer01/8)] = (byte)
                            (Code[Pointer00 + (Pointer01/8)] | (1 << (Pointer01 % 8)));
                //----- AUDIO SPECIAL
                if (TempInt == 2) Code[Pointer00 + (Pointer01/8) + 20] = (byte)
                            (Code[Pointer00 + (Pointer01/8) + 20] | (1 << (Pointer01 % 8)));
                //----- STATES
                TempInt = GetIntFromCursor(TempCursor, "Period");
                Code[Pointer00+Pointer01*5+201] = TempInt.byteValue();
                for (int k=0; k<2; k++) {
                    for (int l=0; l<6; l++) {
                        TempInt = GetIntFromCursor(TempCursor, "D"+String.format("%02d", (k*6+l)));
                        if (TempInt == 1) Code[Pointer00+Pointer01*5+k+202] = (byte)
                                (Code[Pointer00+Pointer01*5+k+202] | (1<<(l%8)));
                        if (TempInt == 2) Code[Pointer00+Pointer01*5+k+204] = (byte)
                                (Code[Pointer00+Pointer01*5+k+204] | (1<<(l%8)));
                    }
                }
                Pointer01++;
                TempCursor.moveToNext();
            }
            //----- CYCLE_TYPES
            if (i==0) Code[Pointer00 + i + 40] = (byte) (TempCursor.getCount() - 1);
            else  Code[Pointer00+i+40] = (byte) (Code[Pointer00+i+40-1] + TempCursor.getCount());
            TempCursor.close();
        }
        //----- EVENTS DATABASE
        Pointer00 = 80;
        TempCursor = Database.rawQuery("SELECT * FROM `Events`;", null);
        TempCursor.moveToFirst();
        for (int i=0; i<EVENTS_COUNT; i++) {
            Code[Pointer00+i*6+0] = (byte) GetIntFromCursor(TempCursor, "StartTimeMin");
            Code[Pointer00+i*6+1] = (byte) GetIntFromCursor(TempCursor, "StartTimeHour");
            Code[Pointer00+i*6+2] = (byte) GetIntFromCursor(TempCursor, "EndTimeMin");
            Code[Pointer00+i*6+3] = (byte) GetIntFromCursor(TempCursor, "EndTimeHour");
            Code[Pointer00+i*6+4] = (byte) GetIntFromCursor(TempCursor, "CycleType");
            for (int j=0; j<7; j++) {
                TempInt = GetIntFromCursor(TempCursor, "Day"+j);
                if (TempInt == 1) Code[Pointer00+i*6+5] = (byte)
                        (Code[Pointer00+i*6+5] | (0x40>>j));
            }
            TempCursor.moveToNext();
        }
        TempCursor.close();
    }
    private int GetIntFromCursor (Cursor Cursor, String Column) {
        return Cursor.getInt(Cursor.getColumnIndex(Column));
    }
    private byte[] SplitIntToByteArray (Integer inInteger, Integer size) {
        byte[] OutBytes = new byte[size];
        for (int i=0; i<OutBytes.length; i++) {
            OutBytes[i] = (byte) ((inInteger>>(8*i) & (0xff)));
        }
        return OutBytes;
    }
    private void PrintHorizontalAddress () {
        PR_Log.append("\n\t\t\t\t");
        for (int i=0; i<16; i++) {
            PR_Log.append(String.format("0x%1$02X\t",i));
            if ((i+1)%4 ==0) PR_Log.append("\t");
            if ((i+1)%8 ==0) PR_Log.append("\t");
        }
        PR_Log.append("\n\n");
    }
    private void PrintCodeToLog () {
        for (int i=0; i<(Code.length+1)/16; i++) {
            if(i%5==0) PrintHorizontalAddress();
            PR_Log.append(String.format("0x%1$03X:\t",i*16));
            for (int j=0; j<16; j++) {
                PR_Log.append(String.format("\t0x%1$02X", Code[i * 16 + j]));
                if ((j+1)%4 ==0) PR_Log.append("\t");
                if ((j+1)%8 ==0) PR_Log.append("\t");
            }
            PR_Log.append("\n");
        }
    }
    public void onConnectClick (View v) {
        //PR_Log.setText("");
        GenerateCodeFromDatabase(Database);
        PrintCodeToLog();
    }
    public void WriteToLog (String str) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy | HH:mm:ss.SSS: ", Locale.ENGLISH);
        PR_Log.setText(sdf.format(new Date())+str+"\n"+PR_Log.getText());
    }

    /*----- Taken from another program and adapted -----*/
    private int ConnectUSB () {
        manager = (UsbManager) getSystemService(Context.USB_SERVICE);
        drivers = prober.findAllDrivers(manager);
        if (drivers.isEmpty()) {
            Toast.makeText(MainActivity.this, R.string.error_device_not_connected,Toast.LENGTH_SHORT).show();
            return 0;
        }
        driver = drivers.get(0);
        connection = manager.openDevice(driver.getDevice());
        if (connection == null) {
            // You probably need to call UsbManager.requestPermission(driver.getDevice(), ..)
            String ACTION_USB_PERMISSION = "com.android.example.USB_PERMISSION";
            PendingIntent mPermissionIntent = PendingIntent.getBroadcast(MainActivity.this, 0, new Intent(ACTION_USB_PERMISSION), 0);
            manager.requestPermission(driver.getDevice(), mPermissionIntent);
            Toast.makeText(MainActivity.this, R.string.error_device_unable_to_connect,Toast.LENGTH_SHORT).show();
            return 1;
        }
        port = driver.getPorts().get(0);
        try {
            port.open(connection);
            port.setParameters(9600, 8, UsbSerialPort.STOPBITS_1, UsbSerialPort.PARITY_NONE);
            Toast.makeText(MainActivity.this, R.string.success_device_connected,Toast.LENGTH_SHORT).show();
            return 2;
        } catch (IOException e) {
            Toast.makeText(MainActivity.this, R.string.error_device_open_port,Toast.LENGTH_SHORT).show();
            e.printStackTrace();
            return 0;
        }
    }
    private static class ProgramTaskParams {
        byte[] Code;
        UsbSerialPort Port;
        ProgramTaskParams(byte[] Code, UsbSerialPort Port) {
            this.Code = Code;
            this.Port = Port;
        }
    }
    public void SendAndVerify (View v) {
        PR_ToggleConnect.setEnabled(false);
        PR_Program.setEnabled(false);
        GenerateCodeFromDatabase(Database);
        WriteToLog(MainContext.getResources().getString(R.string.PR_CodeGenSuccess));
        ProgramTaskParams Params = new ProgramTaskParams(Code, port);
        new ProgramDeviceTask().execute(Params);
        WriteToLog(MainContext.getResources().getString(R.string.PR_ProgramStart)+
                " = " + PR_Delay.getText()+".");
    }
    private void setProgressPercent(Integer Percent) {
        ProgramProgressBar.setProgress(Percent);
    }
    //----- AsyncTask for programming
    private class ProgramDeviceTask extends AsyncTask<ProgramTaskParams, Integer, Integer> {
        private boolean isSuccessful = true;
        private byte SendBuffer[] = new byte[6];
        private boolean isCommSuccessDisplayed = false;
        private void PrepareSendBufferToWrite(int address, int data) {
            SendBuffer[0] = 0x32;
            SendBuffer[1] = (byte) ((address & 0xff0000)>>16);
            SendBuffer[2] = (byte) ((address & 0x00ff00)>>8);
            SendBuffer[3] = (byte) ((address & 0x0000ff));
            SendBuffer[4] = (byte) ((data & 0xff00)>>8);
            SendBuffer[5] = (byte) ((data & 0x00ff));
        }
        private void PrepareStartCommand(){
            SendBuffer[0] = 0x30;
        }
        private void PrepareEndCommand(){
            SendBuffer[0] = 0x31;
        }
        protected Integer doInBackground(ProgramTaskParams... Params) {
            UsbSerialPort port;
            port = Params[0].Port;
            byte[] Code = Params[0].Code;
            byte RecBuffer[] = new byte[4];
            int MAddress = 0x310000;
            //----- StartCommand
            for (int i=0; i<50; i++) {
                PrepareStartCommand();
                try {
                    port.purgeHwBuffers(true, true);
                    port.write(SendBuffer, 10);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                PrepareSendBufferToWrite(MAddress, Code[0]);
                try {
                    port.purgeHwBuffers(true, true);
                    port.write(SendBuffer, 100);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    RecBuffer[0] = 3;
                    port.read(RecBuffer, 100);
                    if (RecBuffer[0]==0x31) {
                        break;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            for (int i=0; i<1024; i++) {
                PrepareSendBufferToWrite(MAddress++, Code[i]);
                try {
                    port.purgeHwBuffers(true, true);
                    port.write(SendBuffer, 100);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    RecBuffer[0] = 3;
                    port.read(RecBuffer, 100);
                    if (RecBuffer[0]!=0x31) {
                        isSuccessful = false;
                        break;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                publishProgress(((i+1)*100)/1024);
            }
            //----- EndCommand
            for (int i=0; i<2; i++) {
                PrepareEndCommand();
                try {
                    port.purgeHwBuffers(true, true);
                    port.write(SendBuffer, 10);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            /*try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            PrepareEndCommand();
            try {
                port.purgeHwBuffers(true, true);
                port.write(SendBuffer, 10);
            } catch (IOException e) {
                e.printStackTrace();
            }*/
            return 1;
        }
        protected void onProgressUpdate(Integer... progress) {
            setProgressPercent(progress[0]);
            if (!isCommSuccessDisplayed) {
                WriteToLog(MainContext.getResources().getString(R.string.PR_CommSuccess));
                isCommSuccessDisplayed = true;
            }
        }
        protected void onPostExecute(Integer result) {
            if (isSuccessful)
                WriteToLog(MainContext.getResources().getString(R.string.PR_Success));
            else
                WriteToLog(MainContext.getResources().getString(R.string.PR_Fail));
            PR_ToggleConnect.setEnabled(true);
            PR_Program.setEnabled(true);
            setProgressPercent(0);
        }
    }

}