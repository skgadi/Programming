package com.skgadi.controltoolbox;

import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbManager;
import android.os.AsyncTask;
import android.support.annotation.DrawableRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.hoho.android.usbserial.driver.ProbeTable;
import com.hoho.android.usbserial.driver.UsbSerialDriver;
import com.hoho.android.usbserial.driver.UsbSerialPort;
import com.hoho.android.usbserial.driver.UsbSerialProber;
import com.hoho.android.usbserial.util.SerialInputOutputManager;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.warkiz.widget.IndicatorSeekBar;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


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

    public LineChart chart;
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
    protected UsbManager manager;
    ProbeTable customTable;
    //UsbSerialProber prober; // Not required for arduino
    List<UsbSerialDriver> drivers;
    UsbSerialDriver driver;
    UsbDeviceConnection connection;
    private UsbSerialPort port;
    boolean DeviceConnected = false;
    SIMULATION_STATUS SimulationState;

    SimulationView Model;

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
    }

    private void GenerateSettingsView () {
        TextView TempTextView;
        SettingsSeekBars = new IndicatorSeekBar[SettingsDefault.length];
        for (int i=0; i<SettingsDefault.length; i++) {
            TempTextView = new TextView(getApplicationContext());
            if (getResources().getStringArray(R.array.SETTINGS_WINDOW)[i].contains(">>")) {
                String[] TempTitles = getResources().getStringArray(R.array.SETTINGS_WINDOW)[i].split(">>");
                TempTextView.setText(TempTitles[0]);
                TempTextView.setTextSize(18);
                TempTextView.setTypeface(null, Typeface.BOLD);
                ((LinearLayout) findViewById(R.id.SettingsSeekBars)).addView(TempTextView);
                TempTextView = new TextView(getApplicationContext());
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
        else
            ChangeStateToNotSimulating();
        if (Screen == SCREENS.MAIN_SCREEN)
            setTitle(getResources().getString(R.string.app_name));
        else
            setTitle(getResources().getString(R.string.app_name)
                    + " >> "
                    + getResources().getStringArray(R.array.SCREENS_LIST)[PresentScreen.ordinal()]);
        switch (Screen) {
            case PID:
                PreparePIDModel();
                GenerateViewFromModel();
                break;
        }
    }

    private void GenerateViewFromModel () {
        //Removing previous view
        if (ModelView.getChildCount() >0 )
            ModelView.removeAllViews();
        //Add an Image
        ImageView TempImgView = new ImageView(getApplicationContext());
        TempImgView.setImageResource(Model.Images[0]);
        ModelView.addView(TempImgView);
        //Sampling time
        TextView TempTextView;
        TempTextView = new TextView(getApplicationContext());
        TempTextView.setText(getString(R.string.SAMPLING_TIME)+": "
                +SettingsSeekBars[0].getProgress()
                +" ms");
        TempTextView.setGravity(Gravity.RIGHT);
        ModelView.addView(TempTextView);
        //Parameters IndicatorSeekBars
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
            if (Model.Parameters[i].Name.contains(">>")) {
                String[] TempTitles = Model.Parameters[i].Name.split(">>");
                TempTextView.setText(TempTitles[0]);
                TempTextView.setTextSize(18);
                TempTextView.setTypeface(null, Typeface.BOLD);
                ModelView.addView(TempTextView);
                TempTextView = new TextView(getApplicationContext());
                TempTextView.setText(TempTitles[1]);
            } else
                TempTextView.setText(Model.Parameters[i].Name);
            ModelView.addView(TempTextView);
            ModelView.addView(ModelParamsSeekBars[i]);
        }
        //Graphs
        TempTextView = new TextView(getApplicationContext());
        TempTextView.setText(getString(R.string.GRAPHS));
        TempTextView.setTextSize(18);
        TempTextView.setTypeface(null, Typeface.BOLD);
        ModelView.addView(TempTextView);
        ModelGraphs = new GraphView[Model.Figures.length];
        for (int i=0; i<ModelGraphs.length; i++) {
            ModelGraphs[i] = new GraphView(getApplicationContext());
            for (int j=0; j<Model.Figures[i].Trajectories.length; j++) {
                LineGraphSeries<DataPoint> GraphSeries = new LineGraphSeries<>();
                GraphSeries.setColor(ColorTable[j]);
                ModelGraphs[i].addSeries(GraphSeries);
            }
            ModelGraphs[i].getViewport().setScalable(true);
            ModelGraphs[i].getViewport().setScalableY(true);
            ModelGraphs[i].getViewport().setScrollable(true);
            ModelGraphs[i].getViewport().setScrollableY(true);
            ModelGraphs[i].getViewport().setMinX(0);
            ModelGraphs[i].getViewport().setMaxX(
                    ReadSettingsFromDatabase()[Arrays.asList(SettingsDBColumns)
                            .indexOf("ZoomXWindow")
                            ]);
            ModelGraphs[i].setMinimumHeight(
                    ReadSettingsFromDatabase()[Arrays.asList(SettingsDBColumns)
                            .indexOf("ChartWindowHeight")
                            ]);
            ModelView.addView(ModelGraphs[i]);
            TempTextView = new TextView(getApplicationContext());
            TempTextView.setText(getString(R.string.GRAPH_CAPTION) + " " + ((int)i+1) + ": "
                    + Model.Figures[i].Name);
            TempTextView.setTextSize(18);
            TempTextView.setTypeface(null, Typeface.BOLD);
            TempTextView.setGravity(Gravity.CENTER);
            ModelView.addView(TempTextView);
        }
    }

    private void PreparePIDModel() {
        Model = new SimulationView() {
            @Override
            public float[] RunAlgorithms(float[] Parameters, float[] Generated,
                                         float[] In, float[] In1Delay, float[] In2Delay,
                                         float[] Out1Delay, float[] Out2Delay) {
                float K_P = Parameters[0];
                float K_I = Parameters[1];
                float K_D = Parameters[2];
                float a = K_P + K_I* T_S /2 + K_D/T_S;
                float b = -K_P + K_I*T_S/2 - 2*K_D/T_S;
                float c = K_D/T_S;
                float [] OutSignals = new float[1];
                OutSignals[0] = Out1Delay[0] + a*In[0] + b*In1Delay[0] + c*In2Delay[0];
                return OutSignals;
            }

            @Override
            public float[] OutGraphSignals(float[] Generated, float[] In, float[] Out) {
                float[] Trajectories = new float[4];
                Trajectories[0] = Generated[0];
                Trajectories[1] = In[0];
                Trajectories[2] = Generated[0]-In[0];
                Trajectories[3] = Out[0];
                return Trajectories;
            }
        };
        Model.Images = new int[1];
        Model.Images[0] = R.drawable.pid;
        Model.Ports = new String[2];
        Model.Ports[0] = "Control signal u(t)";
        Model.Ports[1] = "Plant's output y(t)";
        Model.SignalGenerators = new String[1];
        Model.SignalGenerators[0] = "Reference r(t)";
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
        Model.Parameters[0] = new Parameter("Controller parameters>>K_P", 0, 100, 1);
        Model.Parameters[1] = new Parameter("K_I", 0, 10, 1);
        Model.Parameters[2] = new Parameter("K_D", 0, 10, 0);
        Model.T_S = ReadSettingsFromDatabase()[Arrays.asList(SettingsDBColumns).indexOf("SamplingTime")];
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
                    if (ConnectUSB() == 2) {
                        ChangeStateToSimulating();
                        SimulateParams SParams = new SimulateParams(port, PresentScreen);
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
        try {
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
        }
    }
    void DisconnectUSB () {
        try {
            port.close();
            Toast.makeText(MainActivity.this,
                    getResources().getStringArray(R.array.TOASTS)[5],
                    Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(MainActivity.this,
                    getResources().getStringArray(R.array.TOASTS)[6],
                    Toast.LENGTH_SHORT).show();
        }
    }

    /*
    Async task for implementing algorithms in real time
    */
    private static class SimulateParams {
        UsbSerialPort Port;
        SCREENS Screen;
        SimulateParams (UsbSerialPort Prt, SCREENS Srn) {
            Port = Prt;
            Screen = Srn;
        }
    }
    private static class ProgressParams {
        float[] Values;
        ProgressParams (float[] values) {
            Values = values;
        }

    }
    private class Simulate extends AsyncTask <SimulateParams, ProgressParams, Integer> {
        UsbSerialPort port;
        LineGraphSeries<DataPoint> GraphSeries0 = new LineGraphSeries<>();
        LineGraphSeries<DataPoint> GraphSeries1 = new LineGraphSeries<>();
        LineGraphSeries<DataPoint> GraphSeries2 = new LineGraphSeries<>();

        boolean IsProgressFirstIteration=true;
        DataPoint[] DataPoints;
        float[] RecData = new float[3];
        boolean isValidRead=false;
        String PrevString="";
        boolean Purged = false;
        int MissedTicks = 0;


        private ExecutorService mExecutor = Executors.newSingleThreadExecutor();
        private SerialInputOutputManager mSerialIoManager;
        private final SerialInputOutputManager.Listener mListener =
                new SerialInputOutputManager.Listener() {

                    @Override
                    public void onRunError(Exception e) {
                        //Log.d("USBRec", "Runner stopped.");
                    }

                    @Override
                    public void onNewData(final byte[] data) {
                        DataRecUpdate(data);
                        /*MainActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                MainActivity.this.updateReceivedData(data);
                            }
                        });*/
                    }
                };
        private void PurgeReceivedBuffer() {
            try {
                Thread.sleep(10);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Purged = true;
        }
        private void DataRecUpdate (byte[] data) {
            //Log.i("USBRec", "Previous String: " + PrevString);
            String Rec = PrevString + new String(data);
            /*Log.i("USBRec", "Received String: " + Rec);
            Log.i("USBRec", "Last Digit"+Rec.substring(Rec.length()-1));*/
            if (Rec.substring(Rec.length()-1).contains("E")) {
                isValidRead = true;
                PrevString = "";
                Rec = Rec.substring(0, Rec.indexOf("E"));
                //Log.i("USBRec", "Received String with validation: " + Rec);
                String[] RecStrs = Rec.split(";");
                for (int i=0; i<RecStrs.length; i++) {
                    RecData[i] = Float.parseFloat(RecStrs[i])/1024*5;
                }
                /*Log.i("USBRec", "Size: "+RecStrs.length);
                Log.i("USBRec", RecStrs [0]);
                Log.i("USBRec", RecStrs [1]);
                Log.i("USBRec", RecStrs [2]);*/
            } else if (Purged)
                PrevString = Rec;
            //readCount++;
        }
        private void DataRecUpdateForHex (byte[] data) {
            for (int i=0; i<data.length; i++)
                Log.i("USBRec", String.format("Before prepending: %02X", data[i]));
            String Rec = PrevString + new String(data);


            for (int i=0; i<Rec.length(); i++)
                Log.i("USBRec", String.format("After prepending: %02X", Rec.getBytes()[i]));

            if ((Rec.length()==7) && (Rec.substring(Rec.length()-1).contentEquals("E"))) {
                isValidRead = true;
                PrevString = "";
                for (int i=0; i<3; i++) {
                    //RecData[i] = ConvertBytesToInt(Rec.getBytes()[i+2],Rec.getBytes()[i + 1]);//---this is wrong
                }
            } else
                PrevString = Rec;
        }

        @Override
        protected Integer doInBackground(SimulateParams... Params) {
            float[] PValues = new float[6];
            //series = new LineGraphSeries<DataPoint>();
            DataPoints = new DataPoint[1000];

            ProgressParams PParams = new ProgressParams(PValues);
            port = Params[0].Port;

            mSerialIoManager = new SerialInputOutputManager(port, mListener);
            mExecutor.submit(mSerialIoManager);
            PurgeReceivedBuffer();

            long StartTime = System.currentTimeMillis();
            float TestOutVal0=-5;
            int TestOutVal1=0;


            while(!this.isCancelled()) {
                boolean RecedData = false;
                do {
                    if (isValidRead) {
                        isValidRead  = false;
                        RecedData = true;
                        PValues[0]  = (System.currentTimeMillis()-StartTime)/1000.0f;
                        PValues[1] = RecData[0];
                        PValues[2]  = (System.currentTimeMillis()-StartTime)/1000.0f;
                        PValues[3] = RecData[1];
                        PValues[4]  = (System.currentTimeMillis()-StartTime)/1000.0f;
                        PValues[5] = RecData[2];
                        publishProgress(PParams);
                    }
                } while ((((System.currentTimeMillis() - StartTime))%10) != 0);
                if (RecedData)
                    Log.i("Timing", String.valueOf(PValues[0]));

                try {
                    WriteToUSB(5*(float)Math.sin(2*Math.PI*0.00010*7*(System.currentTimeMillis() - StartTime)));
                    TestOutVal1++;
                    if (TestOutVal1 == 1) {
                        TestOutVal1 = 0;
                        TestOutVal0 += 0.1;
                    }
                    /*if (TestOutVal0 >= 5)
                        TestOutVal0 = -5;*/
                    Log.i("Timing", "Out: " + TestOutVal0);
                    //Thread.sleep(1);
                    MissedTicks=0;
                } catch (Exception e) {
                    Log.i("USBRec", "Error found");
                    e.printStackTrace();
                    if (++MissedTicks >=5) {
                        this.cancel(true);
                    }
                }
            }/**/
            return null;
        }

        @Override
        protected void onProgressUpdate(ProgressParams... Params) {
            GraphView graph = (GraphView) findViewById(R.id.pid_chart00);
            GraphSeries0.appendData(new DataPoint(Params[0].Values[0], Params[0].Values[1]), true, 100);
            GraphSeries1.appendData(new DataPoint(Params[0].Values[2], Params[0].Values[3]), true, 100);
            GraphSeries2.appendData(new DataPoint(Params[0].Values[4], Params[0].Values[5]), true, 100);
            if (IsProgressFirstIteration) {
                IsProgressFirstIteration=false;
                graph.addSeries(GraphSeries0);
                //graph.addSeries(GraphSeries1);
                //graph.addSeries(GraphSeries2);
                graph.getViewport().setScalable(true);
                graph.getViewport().setScalableY(true);
                graph.getViewport().setScrollable(true);
                graph.getViewport().setScrollableY(true);
                graph.getViewport().setMinX(0);
                graph.getViewport().setMaxX(5);
            }
        }

        @Override
        protected void onPreExecute () {
            GraphView graph = (GraphView) findViewById(R.id.pid_chart00);
            graph.removeAllSeries();
            IsProgressFirstIteration = true;
            GraphSeries0.setColor(Color.parseColor("#ff0000"));
            GraphSeries1.setColor(Color.parseColor("#00ff00"));
            GraphSeries2.setColor(Color.parseColor("#0000ff"));

        }
        @Override
        protected void onPostExecute(Integer result) {
            Toast.makeText(getApplicationContext(),
                    "Waiting to exit...",
                    Toast.LENGTH_SHORT).show();
            while (!mExecutor.isShutdown()) {

            }
            Toast.makeText(getApplicationContext(),
                    "Finished",
                    Toast.LENGTH_SHORT).show();
            ChangeStateToNotSimulating();
        }

        protected void onCancelled() {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Toast.makeText(getApplicationContext(),
                    "Cancelled...",
                    Toast.LENGTH_SHORT).show();
            if (mSerialIoManager != null) {
                mSerialIoManager.stop();
                mSerialIoManager = null;
            }
            mExecutor.shutdown();
            Toast.makeText(getApplicationContext(),
                    "Waiting to exit...",
                    Toast.LENGTH_SHORT).show();
            while (!mExecutor.isShutdown()) {

            }
            DisconnectUSB();
            Toast.makeText(getApplicationContext(),
                    "Finished",
                    Toast.LENGTH_SHORT).show();
            ChangeStateToNotSimulating();
        }
        private void WriteToUSB(Float Value) throws IOException {
            port.write(ConvertToIntTSendBytes(ConvertFloatToIntForAO(Value)),10);
        }
        private int ConvertFloatToIntForAO (Float OutFloat) {
            return Math.round(OutFloat*51);
        }
        private byte[] ConvertToIntTSendBytes (int Out) {
            byte[] OutBytes= {0,0};
            if (Math.abs(Out)>=255)
                OutBytes[0] = (byte) 0xff;
            else
                OutBytes[0] = (byte) (Math.abs(Out) & 0x0ff);
            if (Out>0)
                OutBytes[1] = 0x00;
            else
                OutBytes[1] = 0x01;
            return OutBytes;
        }
    }

}













