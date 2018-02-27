package com.skgadi.controltoolbox;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.hardware.usb.UsbDevice;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.warkiz.widget.IndicatorSeekBar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.aflak.arduino.Arduino;
import me.aflak.arduino.ArduinoListener;


enum SCREENS {
    MAIN_SCREEN,
    SETTINGS,
    PID,
    IDENTIFICATION0,
    IDENTIFICATION1,
    IDENTIFICATION2
}

enum SIMULATION_STATUS {
    DISABLED,
    OFF,
    ON,
    OFF_REQUESTED
}

public class MainActivity extends AppCompatActivity {

    private LinearLayout.LayoutParams DefaultLayoutParams;
    private LinearLayout[] Screens;
    SCREENS PresentScreen = SCREENS.MAIN_SCREEN;

    SCREENS PreviousScreen;
    private boolean CloseApp;
    protected String[] ScreensList;
    MenuItem SettingsButton;
    MenuItem SimulateButton;

    LinearLayout ModelView;
    IndicatorSeekBar[] ModelParamsSeekBars;
    GraphView[] ModelGraphs;
    int[] ColorTable = {
            Color.RED,
            Color.BLUE,
            Color.GREEN,
            Color.BLACK
    };

    //--- Database Related
    SQLiteDatabase GSK_Database;
    String DatabaseName = "gsk_settings.db";
    //--- Settings Related
    Integer[] SettingsDefault = {
            100, 100, 10, 200
    };
    Integer[][] SettingsLimits = {
            {10, 1000}, {10, 10000}, {1, 100}, {25, 1000}
    };
    Integer[] PreviousSettings = {
            0, 0, 0, 0
    };
    String[] SettingsDBColumns = {"SamplingTime", "ChartHistoryLength", "ZoomXWindow", "ChartWindowHeight"};
    IndicatorSeekBar[] SettingsSeekBars;


    //----- Communication and other from prev program
    /*protected UsbManager manager;
    ProbeTable customTable;*/
    //UsbSerialProber prober; // Not required for arduino
//    List<UsbSerialDriver> drivers;
//    UsbSerialDriver driver;
//    UsbDeviceConnection connection;
//    private UsbSerialPort port;
    public Arduino arduino;
    boolean DeviceConnected = false;
    SIMULATION_STATUS SimulationState;

    SimulationView Model;
    FunctionGenerator[] GeneratedSignals;

    Simulate SimHandle;



    //--- Back button handling
    @Override
    public void onBackPressed() {
        if (SimulationState == SIMULATION_STATUS.ON) {
            Toast.makeText(getApplicationContext(),
                    getResources().getStringArray(R.array.TOASTS)[9],
                    Toast.LENGTH_SHORT).show();
        } else {
            if (CloseApp && PresentScreen == SCREENS.MAIN_SCREEN)
                finish();
            else
                CloseApp = false;
            //super.onBackPressed();
            if (PresentScreen == SCREENS.MAIN_SCREEN) {
                CloseApp = true;
                Toast.makeText(getApplicationContext(),
                        getResources().getStringArray(R.array.TOASTS)[0],
                        Toast.LENGTH_SHORT).show();
            } else if (PresentScreen == SCREENS.SETTINGS) {
                Toast.makeText(MainActivity.this,
                        getResources().getStringArray(R.array.TOASTS)[12],
                        Toast.LENGTH_SHORT).show();
            } else {
                SetScreenTo(SCREENS.MAIN_SCREEN);
            }
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //--- Database
        //getApplicationContext().deleteDatabase(DatabaseName); // Use only to reset database while programming
        ConnectToDatabase();
        //--- Generate Settings window
        GenerateSettingsView ();
        //--- Var vals
        ModelView = (LinearLayout)findViewById(R.id.ModelView);
        Screens = new LinearLayout[SCREENS.values().length];
        Screens[0] = (LinearLayout) findViewById(R.id.Main);
        Screens[1] = (LinearLayout) findViewById(R.id.Settings);
        Screens[2] = (LinearLayout) findViewById(R.id.PID);
        Screens[3] = (LinearLayout) findViewById(R.id.IDENTIFICATION0);
        Screens[4] = (LinearLayout) findViewById(R.id.IDENTIFICATION1);
        Screens[5] = (LinearLayout) findViewById(R.id.IDENTIFICATION2);
        DefaultLayoutParams =  new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.FILL_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        SimulationState = SIMULATION_STATUS.DISABLED;
        //--- Add buttons
        ScreensList = getResources().getStringArray(R.array.SCREENS_LIST);
        Button ButtonForMainScreen;
        for (int i=2; i<ScreensList.length; i++) {
            ButtonForMainScreen = new Button(this);
            ButtonForMainScreen.setText(ScreensList[i]);
            ButtonForMainScreen.setLayoutParams(DefaultLayoutParams);
            ButtonForMainScreen.setOnClickListener(new OnMainWindowButton(i));
            Screens[SCREENS.MAIN_SCREEN.ordinal()].addView(ButtonForMainScreen);
        }
        //--- USB Connection
        arduino = new Arduino(getApplicationContext(), 115200);

        arduino.setArduinoListener(new ArduinoListener() {
            @Override
            public void onArduinoAttached(UsbDevice device) {
                arduino.open(device);
                DeviceConnected = false;
            }

            @Override
            public void onArduinoDetached() {
                DeviceConnected = false;
            }

            @Override
            public void onArduinoMessage(byte[] bytes) {
                //((TextView)findViewById(R.id.textView)).setText("Message");
                DoThisWhenReceivedDataFromUSB(bytes);
            }

            @Override
            public void onArduinoOpened() {
                DeviceConnected = true;
            }

            @Override
            public void onUsbPermissionDenied() {
                arduino.reopen();
                DeviceConnected = false;
            }
        });

    }
    public void DoThisWhenReceivedDataFromUSB (final byte[] bytes) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //DataRecUpdateForHex(bytes);
                DataRecUpdate(bytes);
            }
        });
    }
    public void SendToUSB (final byte[] bytes) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                arduino.send(bytes);
            }
        });
    }

    private void GenerateSettingsView () {
        TextView TempTextView;
        SettingsSeekBars = new IndicatorSeekBar[SettingsDefault.length];
        for (int i=0; i<SettingsDefault.length; i++) {
            TempTextView = new TextView(getApplicationContext());
            TempTextView.setTextColor(Color.BLACK);
            if (getResources().getStringArray(R.array.SETTINGS_WINDOW)[i].contains(">>")) {
                String[] TempTitles = getResources().getStringArray(R.array.SETTINGS_WINDOW)[i].split(">>");
                TempTextView.setText(TempTitles[0]);
                TempTextView.setTextSize(18);
                TempTextView.setTypeface(null, Typeface.BOLD);
                ((LinearLayout) findViewById(R.id.SettingsSeekBars)).addView(TempTextView);
                TempTextView = new TextView(getApplicationContext());
                TempTextView.setTextColor(Color.BLACK);
                TempTextView.setText(TempTitles[1]);
            } else
                TempTextView.setText(getResources().getStringArray(R.array.SETTINGS_WINDOW)[i]);
            ((LinearLayout) findViewById(R.id.SettingsSeekBars)).addView(TempTextView);
            SettingsSeekBars[i] = new IndicatorSeekBar.Builder(getApplicationContext())
                    .setMin(SettingsLimits[i][0])
                    .setMax(SettingsLimits[i][1])
                    .thumbProgressStay(true)
                    .build();
            ((LinearLayout) findViewById(R.id.SettingsSeekBars)).addView(SettingsSeekBars[i]);
        }
        ChangeSettingsPositionsTo(ReadSettingsFromDatabase());
    }

    private void ChangeSettingsPositionsTo (Integer[] Values) {
        for (int i=0; i< SettingsDefault.length; i++) {
            Log.i("DBaseStuff", "Got " + Values[i]);
            SettingsSeekBars[i].setProgress(Values[i]);
        }
    }

    private Integer[] ReadSettingsPositions () {
        Integer[] Values = new Integer[SettingsDefault.length];
        for (int i=0; i< SettingsDefault.length; i++)
            Values[i] = SettingsSeekBars[i].getProgress();
        return Values;

    }

    private void WriteSettingsToDatabase (Integer[] Values) {
        ContentValues data;
        data = new ContentValues();
        //Log.i("DBaseStuff", "Got " + Values[0]);
        data.put("Key", 1);
        for (int i=0; i<SettingsDefault.length; i++)
            data.put (SettingsDBColumns[i], Values[i]);
        Cursor TempCursor;
        TempCursor = GSK_Database.rawQuery("SELECT * FROM `Settings`", null);
        if (TempCursor.moveToFirst())
            GSK_Database.update("Settings", data, "Key == 1" ,null);
        else {
            GSK_Database.insert("Settings", null, data);
            WriteSettingsToDatabase(SettingsDefault);
        }
        TempCursor.close();

    }

    private Integer[] ReadSettingsFromDatabase () {
        Integer[] Values = new Integer[SettingsDefault.length];
        Cursor TempCursor;
        TempCursor = GSK_Database.rawQuery("SELECT * FROM `Settings`", null);
        Log.i("DBaseStuff", "Cursor count: " + TempCursor.getCount());
        if (TempCursor.moveToFirst()) {
            Log.i("DBaseStuff", "Got raw query For Key:"
                    + TempCursor.getInt(TempCursor.getColumnIndex("Key")));

            for (int i=0; i<SettingsDefault.length; i++)
                Values[i] = TempCursor.getInt(TempCursor.getColumnIndex(SettingsDBColumns[i]));
        } else {
            Log.i("DBaseStuff", "No raw query");
            CreateAandPopulateRecordForSettingsTable();
        }
        return Values;
    }
    public void SettingsSave (View v) {
        WriteSettingsToDatabase(ReadSettingsPositions());
        SetScreenTo(PreviousScreen);
    }

    public void SettingsReset (View v) {
        ChangeSettingsPositionsTo(SettingsDefault);
    }

    public void SettingsResetNSave (View v) {
        ChangeSettingsPositionsTo(SettingsDefault);
        SettingsSave(v);
    }

    public void SettingsCancel (View v) {
        ChangeSettingsPositionsTo(ReadSettingsFromDatabase());
        SetScreenTo(PreviousScreen);
    }

    private void ConnectToDatabase () {
        GSK_Database = getApplicationContext().openOrCreateDatabase(DatabaseName ,
                MODE_PRIVATE, null);
        if (GSK_Database.isOpen()) {
            Log.i("DBaseStuff", "Database Opened");
            if (isTableExists(GSK_Database, "Settings"))
                Log.i("DBaseStuff", "Table is found");
            else {
                Log.i("DBaseStuff", "Table does not exist");
                CreateAandPopulateRecordForSettingsTable();
            }
        } else
            Log.i("DBaseStuff", "Unable to open Database");
    }

    private void CreateAandPopulateRecordForSettingsTable () {
        String Query = "CREATE TABLE IF NOT EXISTS `Settings` (" +
                "`Key`              INTEGER PRIMARY KEY ASC";
        for (int i=0; i<SettingsDefault.length; i++)
            Query += ", `" + SettingsDBColumns[i] + "`  INTEGER NOT NULL";
        Query += ");";
        //Log.i("DBaseStuff", "Query String: "+Query);
        GSK_Database.execSQL(Query);
        WriteSettingsToDatabase(SettingsDefault);
    }
    public boolean isTableExists(SQLiteDatabase mDatabase, String tableName) {
        Cursor cursor = mDatabase.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '"+tableName+"'", null);
        if(cursor!=null) {
            if(cursor.getCount()>0) {
                cursor.close();
                return true;
            }
            cursor.close();
        }
        return false;
    }

    private void SetScreenTo (SCREENS Screen) {
        PreviousScreen = PresentScreen;
        for (int i=0; i<SCREENS.values().length; i++)
            Screens[i].setVisibility(View.GONE);
        PresentScreen = Screen;
        Screens[PresentScreen.ordinal()].setVisibility(View.VISIBLE);
        if ((Screen == SCREENS.MAIN_SCREEN) || (Screen == SCREENS.SETTINGS))
            ChangeStateToSimulateDisabled();
        else {
            ChangeStateToNotSimulating();
        }
        if (Screen == SCREENS.MAIN_SCREEN)
            setTitle(getResources().getString(R.string.app_name));
        else
            setTitle(getResources().getString(R.string.app_name)
                    + ": "
                    + getResources().getStringArray(R.array.SCREENS_LIST)[PresentScreen.ordinal()]);
        switch (Screen) {
            case PID:
                PreparePIDModel();
                GenerateViewFromModel();
                Screens[2].setVisibility(View.GONE);
                break;
        }
    }

    private void DrawALine(LinearLayout ParentView) {
        View TempView = new View(getApplicationContext());
        TempView.setMinimumHeight(2);
        TempView.setBackgroundColor(Color.BLACK);
        ParentView.addView(TempView);
    }

    private void GenerateViewFromModel () {
        //Removing previous view
        if (ModelView.getChildCount() >0 )
            ModelView.removeAllViews();
        //ModelView.getBackgroundTintList()
        ModelView.setBackgroundColor(Color.WHITE);
        //Declaring TempVariables
        TextView TempTextView;
        LinearLayout TempLayout;
        Switch TempSwitchForLayout;
        DrawALine(ModelView);
        //Add an Image
        DrawALine(ModelView);
        for (int i=0; i<Model.Images.length; i++) {
            TempLayout = new LinearLayout(getApplicationContext());
            TempLayout.setOrientation(LinearLayout.VERTICAL);
            TempSwitchForLayout = new Switch(getApplicationContext());
            TempSwitchForLayout.setTextColor(Color.BLACK);
            TempSwitchForLayout.setBackgroundColor(Color.LTGRAY);
            TempSwitchForLayout.setChecked(true);
            TempSwitchForLayout.setText(getResources().getStringArray(R.array.SIM_VIEW_HEADS)[0]
                    + ": " + Model.ImageNames[i]);
            TempSwitchForLayout.setTextSize(18);
            TempSwitchForLayout.setTypeface(null, Typeface.BOLD);
            TempSwitchForLayout.setOnCheckedChangeListener(new LayoutSwitch(TempLayout));

            ImageView TempImgView = new ImageView(getApplicationContext());
            TempImgView.setImageResource(Model.Images[i]);
            TempLayout.addView(TempImgView);


            ModelView.addView(TempSwitchForLayout);
            ModelView.addView(TempLayout);
            TempSwitchForLayout.setChecked(false);
            DrawALine(ModelView);
        }
        //Parameters IndicatorSeekBars
        TempLayout = new LinearLayout(getApplicationContext());
        TempLayout.setOrientation(LinearLayout.VERTICAL);
        TempSwitchForLayout = new Switch(getApplicationContext());
        TempSwitchForLayout.setTextColor(Color.BLACK);
        TempSwitchForLayout.setBackgroundColor(Color.LTGRAY);
        TempSwitchForLayout.setChecked(true);
        TempSwitchForLayout.setText(getResources().getStringArray(R.array.SIM_VIEW_HEADS)[1]);
        TempSwitchForLayout.setTextSize(18);
        TempSwitchForLayout.setTypeface(null, Typeface.BOLD);
        TempSwitchForLayout.setOnCheckedChangeListener(new LayoutSwitch(TempLayout));

        //--- Sampling time
        TempTextView = new TextView(getApplicationContext());
        TempTextView.setTextColor(Color.BLACK);
        TempTextView.setText(getString(R.string.SAMPLING_TIME)+" = "
                +SettingsSeekBars[0].getProgress()
                +" ms");
        DrawALine(ModelView);
        TempTextView.setTypeface(null, Typeface.BOLD);
        TempLayout.addView(TempTextView);
        //--- Others
        ModelParamsSeekBars = new IndicatorSeekBar[Model.Parameters.length];
        for (int i=0; i<Model.Parameters.length; i++) {
            ModelParamsSeekBars[i] = new IndicatorSeekBar.Builder(getApplicationContext())
                    .setMin(Model.Parameters[i].Min)
                    .setMax(Model.Parameters[i].Max)
                    .setProgress(Model.Parameters[i].DefaultValue)
                    .isFloatProgress(true)
                    .thumbProgressStay(true)
                    .build();
            TempTextView = new TextView(getApplicationContext());
            TempTextView.setTextColor(Color.BLACK);
            if (Model.Parameters[i].Name.contains(">>")) {
                String[] TempTitles = Model.Parameters[i].Name.split(">>");
                TempTextView.setText(TempTitles[0]);
                TempTextView.setTypeface(null, Typeface.BOLD);
                TempLayout.addView(TempTextView);
                TempTextView = new TextView(getApplicationContext());
                TempTextView.setTextColor(Color.BLACK);
                TempTextView.setText(TempTitles[1]);
            } else
                TempTextView.setText(Model.Parameters[i].Name);
            TempLayout.addView(TempTextView);
            TempLayout.addView(ModelParamsSeekBars[i]);
        }

        ModelView.addView(TempSwitchForLayout);
        ModelView.addView(TempLayout);
        TempSwitchForLayout.setChecked(false);
        DrawALine(ModelView);

        //Function generator
        DrawALine(ModelView);
        GeneratedSignals = new FunctionGenerator[Model.SignalGenerators.length];
        for (int i=0; i<Model.SignalGenerators.length; i++) {
            TempLayout = new LinearLayout(getApplicationContext());
            TempLayout.setOrientation(LinearLayout.VERTICAL);
            TempSwitchForLayout = new Switch(getApplicationContext());
            TempSwitchForLayout.setTextColor(Color.BLACK);
            TempSwitchForLayout.setBackgroundColor(Color.LTGRAY);
            TempSwitchForLayout.setChecked(true);
            TempSwitchForLayout.setText(getResources().getStringArray(R.array.SIM_VIEW_HEADS)[2]
                    + ": "
                    + Model.SignalGenerators[i]
                    + "=0"
            );
            TempSwitchForLayout.setTextSize(18);
            TempSwitchForLayout.setTypeface(null, Typeface.BOLD);
            TempSwitchForLayout.setOnCheckedChangeListener(new LayoutSwitch(TempLayout));

            //SignalType
            Spinner TempFunctionsView = new Spinner(getApplicationContext());
            final List<String> SignalsList =
                    new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.AVAILABLE_SIGNALS)));
            final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                    this,R.layout.spinner_item,SignalsList);
            TempFunctionsView.setAdapter(spinnerArrayAdapter);
            TempLayout.addView(TempFunctionsView);
            GeneratedSignals[i] = new FunctionGenerator();
            TempFunctionsView.setOnItemSelectedListener(
                    new SignalTypeListener(GeneratedSignals[i], TempSwitchForLayout));
            //Floats
            for (int j=0; j<5; j++) {
                TempTextView = new TextView(getApplicationContext());
                TempTextView.setTextColor(Color.BLACK);
                TempTextView.setText(getResources().getStringArray(R.array.SIGNAL_GENERATOR_PARAMETERS)[j]);
                IndicatorSeekBar TempSeekBar = new IndicatorSeekBar.Builder(getApplicationContext())
                        .setMin(GeneratedSignals[i].MinMaxDefaultsForFloats[j][0])
                        .setMax(GeneratedSignals[i].MinMaxDefaultsForFloats[j][1])
                        .setProgress(GeneratedSignals[i].MinMaxDefaultsForFloats[j][2])
                        .isFloatProgress(true)
                        .thumbProgressStay(true)
                        .build();
                TempSeekBar.setOnSeekChangeListener(
                        new SeekBarListenerForFunctionGenerator(GeneratedSignals[i], j, TempSwitchForLayout));
                TempLayout.addView(TempTextView);
                TempLayout.addView(TempSeekBar);
            }
            //Compliment
            Switch TempSwitchForCompliment = new Switch(getApplicationContext());
            TempSwitchForCompliment.setChecked(false);
            TempSwitchForCompliment.setText(R.string.INVERT_SIGNAL);
            TempSwitchForCompliment.setOnCheckedChangeListener(
                    new SignalComplimentListener(GeneratedSignals[i], TempSwitchForLayout));
            TempLayout.addView(TempSwitchForCompliment);


            ModelView.addView(TempSwitchForLayout);
            ModelView.addView(TempLayout);
            TempSwitchForLayout.setChecked(false);
            DrawALine(ModelView);
        }

        //Graphs
        DrawALine(ModelView);
        ModelGraphs = new GraphView[Model.Figures.length];
        for (int i=0; i<ModelGraphs.length; i++) {
            TempLayout = new LinearLayout(getApplicationContext());
            TempLayout.setOrientation(LinearLayout.VERTICAL);
            TempSwitchForLayout = new Switch(getApplicationContext());
            TempSwitchForLayout.setTextColor(Color.BLACK);
            TempSwitchForLayout.setBackgroundColor(Color.LTGRAY);
            TempSwitchForLayout.setChecked(true);
            TempSwitchForLayout.setText(getResources().getStringArray(R.array.SIM_VIEW_HEADS)[3]
                    + " " +((int)i+1) + ": "
                    + Model.Figures[i].Name);
            TempSwitchForLayout.setTextSize(18);
            TempSwitchForLayout.setTypeface(null, Typeface.BOLD);
            TempSwitchForLayout.setOnCheckedChangeListener(new LayoutSwitch(TempLayout));

            ModelGraphs[i] = new GraphView(getApplicationContext());
            ModelGraphs[i].getGridLabelRenderer().setGridColor(Color.BLACK);
            ModelGraphs[i].getGridLabelRenderer().setHorizontalLabelsColor(Color.BLACK);
            ModelGraphs[i].getGridLabelRenderer().setVerticalLabelsColor(Color.BLACK);
            ModelGraphs[i].getGridLabelRenderer().setHorizontalAxisTitleColor(Color.BLACK);
            ModelGraphs[i].getGridLabelRenderer().setVerticalAxisTitleColor(Color.BLACK);
            ModelGraphs[i].getGridLabelRenderer().setVerticalLabelsSecondScaleColor(Color.BLACK);

            for (int j=0; j<Model.Figures[i].Trajectories.length; j++) {
                LineGraphSeries<DataPoint> GraphSeries = new LineGraphSeries<>();
                GraphSeries.setColor(ColorTable[j]);
                GraphSeries.setTitle(Model.Figures[i].Trajectories[j]);
                ModelGraphs[i].addSeries(GraphSeries);
            }
            ModelGraphs[i].getViewport().setScalable(true);
            ModelGraphs[i].getViewport().setScalableY(true);
            ModelGraphs[i].getViewport().setScrollable(true);
            ModelGraphs[i].getViewport().setScrollableY(true);
            ModelGraphs[i].getViewport().setMinX(0);
            ModelGraphs[i].getViewport().setMaxX(
                    ReadSettingsPositions()[Arrays.asList(SettingsDBColumns)
                            .indexOf("ZoomXWindow")
                            ]);
            ModelGraphs[i].setMinimumHeight(
                    ReadSettingsPositions()[Arrays.asList(SettingsDBColumns)
                            .indexOf("ChartWindowHeight")
                            ]);
            ModelGraphs[i].getLegendRenderer().setVisible(true);
            ModelGraphs[i].getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
            TempLayout.addView(ModelGraphs[i]);

            ModelView.addView(TempSwitchForLayout);
            ModelView.addView(TempLayout);
            TempSwitchForLayout.setChecked(false);
            DrawALine(ModelView);
        }
        DrawALine(ModelView);
    }

    private void PreparePIDModel() {
        Model = new SimulationView() {
            @Override
            public float[] RunAlgorithms(
                    float[] Parameters,
                    float[][] Generated,
                    float[][] Input,
                    float[][] Output
            ){
                float K_P = Parameters[0];
                float K_I = Parameters[1];
                float K_D = Parameters[2];
                float a = K_P + K_I* T_S /2.0f + K_D/T_S;
                float b = -K_P + K_I*T_S/2.0f - 2.0f*K_D/T_S;
                float c = K_D/T_S;
                float[] E = new float[3];
                for (int i=0; i<3; i++) {
                    E[i] = ((Generated[0][i] + Generated[1][i] + Generated[2][i]) - Input[0][i]);
                }
                float [] OutSignals = new float[1];
                OutSignals[0] = Output[0][1]
                        + a * E[0]
                        + b * E[1]
                        + c * E[2];
                OutSignals[0] = K_P*E[0];//Generated[2][0];
                return OutSignals;
            }

            @Override
            public float[] OutGraphSignals(
                    float[] Parameters,
                    float[][] Generated,
                    float[][] Input,
                    float[][] Output
            )
            {
                float[] Trajectories = new float[4];
                Trajectories[0] = Generated[0][0] + Generated[1][0] + Generated[2][0];
                Trajectories[1] = Input[0][0];
                Trajectories[2] = Trajectories[0]-Input[0][0];
                Trajectories[3] = Output[0][0];
                return Trajectories;
            }
        };
        Model.NoOfInputs=1;
        Model.NoOfOutputs=1;
        Model.NoOfPastInputsRequired = 2;
        Model.NoOfPastOuputsRequired = 1;
        Model.NoOfPastGeneratedValuesRequired = 2;
        Model.OutPut = new float[1];
        Model.OutPut[0]=0;
        Model.Images = new int[1    ];
        Model.Images[0] = R.drawable.pid;
        //Model.Images[1] = R.drawable.pid;
        Model.ImageNames = new String[1];
        Model.ImageNames[0] = "Closed loop system";
        //Model.ImageNames[1] = "Reference Value details";
        Model.Ports = new String[2];
        Model.Ports[0] = "Control signal u(t)";
        Model.Ports[1] = "Plant's output y(t)";
        Model.SignalGenerators = new String[3];
        Model.SignalGenerators[0] = "R1(t)";
        Model.SignalGenerators[1] = "R2(t)";
        Model.SignalGenerators[2] = "R3(t)";
        Model.Figures = new Figure[2];
        String[] TempTrajectories = new String[2];
        TempTrajectories[0]= "Reference r(t)";
        TempTrajectories[1]= "Output y(t)";
        Model.Figures[0] = new Figure("Reference r(t) and Output y(t)", TempTrajectories);
        TempTrajectories = new String[2];
        TempTrajectories[0]= "Error e(t)";
        TempTrajectories[1]= "Control u(t)";
        Model.Figures[1] = new Figure("Error e(t) and Control u(t)", TempTrajectories);
        Model.Parameters = new Parameter [3];
        Model.Parameters[0] = new Parameter("Controller parameters>>K_P", 0, 100, 0.1f);
        Model.Parameters[1] = new Parameter("K_I", 0, 10, 0);
        Model.Parameters[2] = new Parameter("K_D", 0, 1, 0);
        Model.T_S = ReadSettingsPositions()[Arrays.asList(SettingsDBColumns).indexOf("SamplingTime")]/1000.0f;
    }
    public class OnMainWindowButton implements View.OnClickListener {
        int ScreenNumber;
        public OnMainWindowButton (int ScreenNumber) {
            this.ScreenNumber = ScreenNumber;
        }
        @Override
        public void onClick(View v) {
            SetScreenTo (SCREENS.values()[ScreenNumber]);
        }
    };

    //--- Menu handling
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.con_sim_menu, menu);
        SettingsButton = menu.getItem(0);
        SimulateButton = menu.getItem(1);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings:
                if (SimulationState == SIMULATION_STATUS.ON)
                    Toast.makeText(MainActivity.this,
                            getResources().getStringArray(R.array.TOASTS)[10],
                            Toast.LENGTH_SHORT).show();
                else
                    if (PresentScreen != SCREENS.SETTINGS)
                        SetScreenTo(SCREENS.SETTINGS);
                break;
            case R.id.simulate:
                if(SimulationState == SIMULATION_STATUS.ON) {
                    SimHandle.cancel(true);
                }
                if (SimulationState == SIMULATION_STATUS.DISABLED) {
                    Toast.makeText(MainActivity.this,
                            getResources().getStringArray(R.array.TOASTS)[8],
                            Toast.LENGTH_SHORT).show();
                }
                if (SimulationState == SIMULATION_STATUS.OFF) {
                    if (DeviceConnected) {
                        ChangeStateToSimulating();
                        SimulateParams SParams = new SimulateParams(
                                //port,
                                Model,
                                GeneratedSignals,
                                ModelParamsSeekBars,
                                ModelGraphs,
                                PresentScreen);
                        SimHandle = new Simulate();
                        SimHandle.execute(SParams);
                        Toast.makeText(MainActivity.this,
                                "started simulating",
                                Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    void ChangeStateToSimulateDisabled () {
        SimulationState = SIMULATION_STATUS.DISABLED;
        SimulateButton.setIcon(R.drawable.icon_simulate_disabled);
    }
    void ChangeStateToSimulating () {
        SimulationState = SIMULATION_STATUS.ON;
        SimulateButton.setIcon(R.drawable.icon_simulate_stop);
        SettingsButton.setIcon(R.drawable.icon_settings_disabled);
    }
    void ChangeStateToNotSimulating () {
        SimulationState = SIMULATION_STATUS.OFF;
        SimulateButton.setIcon(R.drawable.icon_simulate_start);
        SettingsButton.setIcon(R.drawable.icon_settings);
    }
    //--- USB Programming
    private int ConnectUSB () {
        /*try {
            manager = (UsbManager) getSystemService(Context.USB_SERVICE);
            //drivers = prober.findAllDrivers(manager); // Not required for arduino
            drivers =  UsbSerialProber.getDefaultProber().findAllDrivers(manager);;
        } catch (Exception e) {
            Toast.makeText(MainActivity.this,
                    getResources().getStringArray(R.array.TOASTS)[7],
                    Toast.LENGTH_SHORT).show();
            return 0;
        }
        if (drivers.isEmpty()) {
            Toast.makeText(MainActivity.this,
                    getResources().getStringArray(R.array.TOASTS)[1],
                    Toast.LENGTH_SHORT).show();
            return 0;
        }
        driver = drivers.get(0);
        connection = manager.openDevice(driver.getDevice());
        if (connection == null) {
            // You probably need to call UsbManager.requestPermission(driver.getDevice(), ..)
            String ACTION_USB_PERMISSION = "com.skgadi.controltoolbox.USB_PERMISSION";
            PendingIntent mPermissionIntent =
                    PendingIntent.getBroadcast(MainActivity.this,
                            0,
                            new Intent(ACTION_USB_PERMISSION),
                            0);
            manager.requestPermission(driver.getDevice(), mPermissionIntent);
            Toast.makeText(MainActivity.this,
                    getResources().getStringArray(R.array.TOASTS)[2],
                    Toast.LENGTH_SHORT).show();
            return 1;
        }
        port = driver.getPorts().get(0);
        try {
            port.open(connection);
            port.setParameters(115200,
                    8,
                    UsbSerialPort.STOPBITS_1,
                    UsbSerialPort.PARITY_NONE);
            Toast.makeText(MainActivity.this,
                    getResources().getStringArray(R.array.TOASTS)[3],
                    Toast.LENGTH_SHORT).show();
            return 2;
        } catch (IOException e) {
            Toast.makeText(MainActivity.this,
                    getResources().getStringArray(R.array.TOASTS)[4],
                    Toast.LENGTH_SHORT).show();
            e.printStackTrace();
            return 0;
        }*/
        return 2;
    }
    void DisconnectUSB () {
        /*try {
            port.close();
            Toast.makeText(MainActivity.this,
                    getResources().getStringArray(R.array.TOASTS)[5],
                    Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(MainActivity.this,
                    getResources().getStringArray(R.array.TOASTS)[6],
                    Toast.LENGTH_SHORT).show();
        }*/
    }

    //mserialio
    float[] RecData = new float[3];
    boolean Purged = false;
    boolean isValidRead=false;
    String PrevString="";

    //private ExecutorService mExecutor;// = Executors.newSingleThreadExecutor();
    //private SerialInputOutputManager mSerialIoManager;
    /*private final SerialInputOutputManager.Listener mListener =
            new SerialInputOutputManager.Listener() {

                @Override
                public void onRunError(Exception e) {
                    //Log.d("USBRec", "Runner stopped.");
                }

                @Override
                public void onNewData(final byte[] data) {
                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //DataRecUpdateForHex(data);
                            DataRecUpdate(data);
                        }
                    });
                }
            };*/
    private void PurgeReceivedBuffer() {
        try {
            Thread.sleep(10);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Purged = true;
    }
    private void DataRecUpdate (byte[] data) {
        //Log.i("Timing", "Previous String: " + PrevString);
        String Rec = PrevString + new String(data);
        Log.i("Timing", "Received String: " + Rec);
        Log.i("Timing", "Last Digit"+Rec.substring(Rec.length()-1));/**/
        if (Rec.contains("E") && Rec.contains("[") && Rec.contains("]")) {
            PrevString = "";
            /*Rec = Rec.substring(0, Rec.indexOf("E"));
            //Log.i("USBRec", "Received String with validation: " + Rec);
            String[] RecStrs = Rec.split(";");
            for (int i=0; i<RecStrs.length; i++) {
                try {
                    RecData[i] = Float.parseFloat(RecStrs[i]) / 1024 * 5;
                } catch (Exception e) {
                    Log.i("Timing", "Error in parse");
                }
            }*/
            try {
                String result = Rec.substring(Rec.indexOf("[") + 1, Rec.indexOf("]"));
                RecData[0] = Float.parseFloat(result) / 1024 * 5;
                isValidRead = true;
            } catch (Exception e) {
                Log.i("Timing", "Error in parse");
            }
        } else if (Purged)
            PrevString = Rec;
    }
    byte PrevByte;
    boolean IsPrevByteSet=true;
    private void DataRecUpdateForHex (byte[] data) {
        if (data.length>=2) {
            short TempVal = (short) ((data[0] & 0xff) | (data[1] << 8));
            RecData[0] = PutBetweenRange(TempVal/1024f*5f, -5, 5);
            Log.i("Timing", "New Data: " + TempVal);
            isValidRead = true;
        } /*else if (data.length==1) {
            if (IsPrevByteSet) {
                IsPrevByteSet = false;
                byte UpdatedData[] = {PrevByte, data[0]};
                short TempVal = (short) ((UpdatedData[0] & 0xff) | (UpdatedData[1] << 8));
                RecData[0] = PutBetweenRange(TempVal/1024f*5f, -5, 5);
                Log.i("Timing", "Found second byte making a total value: " + TempVal);
                isValidRead = true;
            } else {
                IsPrevByteSet = true;
                PrevByte = data[0];
                Log.i("Timing", "Found first byte.");
            }
        }*/ else {
            Log.i("Timing", "Received data size: " + data.length);
        }
    }
    /*
    Async task for implementing algorithms in real time
    */
    private static class SimulateParams {
        //UsbSerialPort Port;
        SimulationView SimulationModel;
        FunctionGenerator[] GeneratedSignals;
        IndicatorSeekBar[] ModelParamsSeekBars;
        GraphView[] ModelGraphs;
        SCREENS Screen;
        SimulateParams (
                //UsbSerialPort port,
                SimulationView simulationModel,
                FunctionGenerator[] generatedSignals,
                IndicatorSeekBar[] modelParamsSeekBars,
                GraphView[] modelGraphs,
                SCREENS screen
                ) {
            //Port = port;
            SimulationModel = simulationModel;
            GeneratedSignals = generatedSignals;
            ModelParamsSeekBars = modelParamsSeekBars;
            ModelGraphs = modelGraphs;
            Screen = screen;
        }
    }
    public float PutBetweenRange (float value, float MinValue, float MaxValue) {
        if (value>MaxValue)
            return MaxValue;
        if (value<MinValue)
            return MinValue;
        return value;
    }

    private static class ProgressParams {
        float[] Values;
        ProgressParams (float[] values) {
            Values = values;
        }
    }
    private class Simulate extends AsyncTask <SimulateParams, ProgressParams, Integer> {
        //UsbSerialPort port;
        float[][] Input;
        float[][] Output;
        float[][] PreparedSignals;
        float Time;
        float[] ReadTimes = {0,0,0,0};



        LineGraphSeries<DataPoint> GraphSeries0 = new LineGraphSeries<>();
        LineGraphSeries<DataPoint> GraphSeries1 = new LineGraphSeries<>();
        LineGraphSeries<DataPoint> GraphSeries2 = new LineGraphSeries<>();

        boolean IsProgressFirstIteration=true;
        DataPoint[] DataPoints;
        boolean WaitedTS = true;
        int MissedTicks = 0;



        @Override
        protected Integer doInBackground(SimulateParams... Params) {
            float[] PValues = new float[6];
            //series = new LineGraphSeries<DataPoint>();
            DataPoints = new DataPoint[1000];

            ProgressParams PParams = new ProgressParams(PValues);
            //port = Params[0].Port;
            //mExecutor = Executors.newSingleThreadExecutor();
            //mSerialIoManager = new SerialInputOutputManager(port, mListener);
            //mExecutor.submit(mSerialIoManager);
            //PurgeReceivedBuffer();

            long StartTime = System.currentTimeMillis();
            float TestOutVal0=-5;
            int TestOutVal1=0;
            int NoOfIterations=0;
            int NotOfTimesSend = 1;
            float TSPresent = 0;
            float TS1Delay = 0;
            float TS2Delay = 0;
            float TS3Delay = 0;
            final byte[] PKT_STOP_SNIF = { (byte) 0xFB, '\n' };

            Log.i("Timing", "Started work");

            isValidRead = true;
            long LastWrittenTime=0;

            while(!this.isCancelled() || !DeviceConnected) {
                if (!Purged)
                    PurgeReceivedBuffer();
                Time = (System.currentTimeMillis()-StartTime)/1000.0f;
                if (((((int)(System.currentTimeMillis() - StartTime))
                        %Math.round(Model.T_S*1000)) == 0) && (System.currentTimeMillis() != LastWrittenTime)) {
                    LastWrittenTime = System.currentTimeMillis();
                    float[] TempOutput = Model.RunAlgorithms(
                            GetParameters(),
                            PreparedSignals,
                            Input,
                            Output
                    );
                    for (int i=0; i<TempOutput.length; i++)
                        Output[i] = PutElementToFIFO(Output[i], PutBetweenRange(TempOutput[i], -5, 5));
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    //for (int i=0; i<NotOfTimesSend; i++)
                        WriteToUSB(Output[0][0]);
                    WaitedTS = false;
                    Log.i("Timing", "T_S: "+Math.round(Model.T_S*1000));
                }
                if (isValidRead) {
                    isValidRead  = false;
                    PutElementToFIFO(ReadTimes, Time);
                    float MovingAverageTS=0;
                    for (int i=0; i<(ReadTimes.length-1); i++)
                        MovingAverageTS = MovingAverageTS + (ReadTimes[i]-ReadTimes[i+1]);
                    MovingAverageTS = MovingAverageTS/(ReadTimes.length-1);
                    if ((MovingAverageTS > 1.2*Model.T_S) && (NotOfTimesSend<10))
                        NotOfTimesSend++;
                    if ((MovingAverageTS < 0.8*Model.T_S) && (NotOfTimesSend>1))
                        NotOfTimesSend--;
                    Log.i("Timing", "TS past: " + ReadTimes[0] );
                    Log.i("Timing", "TS present: " + ReadTimes[1]);
                    Log.i("Timing", "Moving TS Average: " + MovingAverageTS );
                    Log.i("Timing", "Number of times send write: " + NotOfTimesSend);/**/
                    for (int i=0; i<Input.length; i++)
                        Input[i] = PutElementToFIFO(Input[i], RecData[i]);
                    for (int i = 0; i< PreparedSignals.length; i++)
                        PreparedSignals[i] = PutElementToFIFO(PreparedSignals[i],
                                GeneratedSignals[i].GetValue(Time));
                    publishProgress(PParams);
                }


                /*int LoopCycle = 0;
                boolean ReadAValidInThisCycle = false;
                while ((((int)(System.currentTimeMillis() - StartTime))
                                %(Math.round(Model.T_S*1000))) != 0) {
                    if (isValidRead && WaitedTS) {
                        isValidRead  = false;
                        WaitedTS = false;
                        ReadAValidInThisCycle = true;
                        //TS3Delay = TS2Delay;
                        //TS2Delay = TS1Delay;
                        TS1Delay = TSPresent;
                        TSPresent = (System.currentTimeMillis()-StartTime)/1000.0f - Time;
                        float MovingAverageTS = ((0 * TS3Delay) + (0 * TS2Delay) + TS1Delay + TSPresent) / 4f;
                        if ((MovingAverageTS > 1.2*Model.T_S) && (NotOfTimesSend<10))
                            NotOfTimesSend++;
                        if ((MovingAverageTS < 0.8*Model.T_S) && (NotOfTimesSend>1))
                            NotOfTimesSend--;
                        Log.i("Timing", "Moving TS Average: " + MovingAverageTS );
                        Log.i("Timing", "Number of times send write: " + NotOfTimesSend);

                        Time = (System.currentTimeMillis()-StartTime)/1000.0f;
                        for (int i=0; i<Input.length; i++)
                            Input[i] = PutElementToFIFO(Input[i], RecData[i]);
                        for (int i = 0; i< PreparedSignals.length; i++)
                            PreparedSignals[i] = PutElementToFIFO(PreparedSignals[i],
                                    GeneratedSignals[i].GetValue(Time));
                        float[] TempOutput = Model.RunAlgorithms(
                                GetParameters(),
                                PreparedSignals,
                                Input,
                                Output
                        );
                        for (int i=0; i<TempOutput.length; i++)
                            Output[i] = PutElementToFIFO(Output[i], PutBetweenRange(TempOutput[i], -5, 5));
                        publishProgress(PParams);
                    }
                    if (LoopCycle==0) {
                        try {
                            for (int i=0; i<NotOfTimesSend; i++)
                                WriteToUSB(Output[0][0]);
                            MissedTicks = 0;
                            Log.i("Timing", "Write success");
                        } catch (Exception e) {
                            MissedTicks++;
                            Log.i("Timing", "Writing error");
                            e.printStackTrace();
                        }
                    }
                    LoopCycle++;
                }
                if (MissedTicks>=5)
                    this.cancel(true);
                WaitedTS = true;
                NoOfIterations++;*/
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(ProgressParams... Params) {
            float[] SignalsToPlot = Model.OutGraphSignals(
                    GetParameters(),
                    PreparedSignals,
                    Input,
                    Output
            );
            int Iteration=0;
            for (int i=0; i<ModelGraphs.length; i++) {
                for (int j=0; j< ModelGraphs[i].getSeries().size(); j++) {
                    ((LineGraphSeries<DataPoint>)(ModelGraphs[i].getSeries().get(j))).appendData(
                            new DataPoint(
                                    Time, SignalsToPlot[Iteration]), true,
                            ReadSettingsPositions()[Arrays.asList(SettingsDBColumns)
                                    .indexOf("ChartHistoryLength")]
                    );
                    Iteration++;
                }
            }
        }

        @Override
        protected void onPreExecute () {
            Purged = false;
            Model.T_S = ReadSettingsPositions()[Arrays.asList(SettingsDBColumns).indexOf("SamplingTime")]/1000.0f;
            Input = new float[Model.NoOfInputs][Model.NoOfPastInputsRequired+1];
            Output = new float[Model.NoOfOutputs][Model.NoOfPastOuputsRequired+1];
            PreparedSignals = new float[Model.SignalGenerators.length][Model.NoOfPastGeneratedValuesRequired+1];
            for (int i=0; i<Input.length; i++) {
                for (int j=0; j<Input[i].length; j++)
                    Input[i][j] = 0;
            }
            for (int i=0; i<Output.length; i++) {
                for (int j=0; j<Output[i].length; j++)
                    Output[i][j] = 0;
            }
            for (int i = 0; i< PreparedSignals.length; i++) {
                for (int j = 0; j< PreparedSignals[i].length; j++)
                    PreparedSignals[i][j] = 0;
            }


            for (int i=0; i<ModelGraphs.length; i++) {
                for (int j=0; j< ModelGraphs[i].getSeries().size(); j++) {
                    ((LineGraphSeries<DataPoint>)(ModelGraphs[i].getSeries().get(j))).resetData(new DataPoint[0]);
                }
            }
        }
        protected float[] GetParameters () {
            float[] ParameterValues = new float[Model.Parameters.length];
            for (int i=0; i<ParameterValues.length; i++) {
                ParameterValues[i] = ModelParamsSeekBars[i].getProgressFloat();
            }
            return ParameterValues;
        }
        protected float[] PutElementToFIFO (float[] array, float element){
            for (int i=(array.length-1); i>0; i--) {
                array[i] = array[i-1];
            }
            array[0] = element;
            return array;
        }
        protected void onCancelled() {
            /*try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
            /*Toast.makeText(getApplicationContext(),
                    "Cancelled...",
                    Toast.LENGTH_SHORT).show();*/
            /*if (mSerialIoManager != null) {
                mSerialIoManager.stop();
                mSerialIoManager = null;
            }
            mExecutor.shutdown();
            Toast.makeText(getApplicationContext(),
                    "Waiting to exit...",
                    Toast.LENGTH_SHORT).show();
            while (!mExecutor.isShutdown()) {

            }*/
            //DisconnectUSB();
            /*Toast.makeText(getApplicationContext(),
                    "Finished",
                    Toast.LENGTH_SHORT).show();*/
            ChangeStateToNotSimulating();
        }
        private void WriteToUSB(Float Value) {
            arduino.send(ConvertToIntTSendBytes(ConvertFloatToIntForAO(Value)));
            Log.i("Timing", "Writing Command");
            //SendToUSB(ConvertToIntTSendBytes(ConvertFloatToIntForAO(Value)));
            //port.write(ConvertToIntTSendBytes(ConvertFloatToIntForAO(Value)),10);//Math.round(Model.T_S*1000f));
            //mSerialIoManager.writeAsync("AA".getBytes());
        }
        private int ConvertFloatToIntForAO (Float OutFloat) {
            return Math.round(OutFloat*51f);
        }
        private byte[] ConvertToIntTSendBytes (int Out) {
            byte[] OutBytes= {0,0,(byte)0x31};
            if (Math.abs(Out)>=255)
                OutBytes[0] = (byte) 0xff;
            else
                OutBytes[0] = (byte) (Math.abs(Out) & 0x0ff);
            if (Out>0)
                OutBytes[1] = 0x00;
            else
                OutBytes[1] = 0x01;
            //Log.i("Timing", String.format("ing string: 0x%2X, 0x%2X, 0x%2X", OutBytes[0], OutBytes[1], OutBytes[2]));
            return OutBytes;
        }
    }

}