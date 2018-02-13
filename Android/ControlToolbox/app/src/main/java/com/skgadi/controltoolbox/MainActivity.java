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
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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

import java.io.File;
import java.io.IOException;
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
    SCREENS PresentScreen = SCREENS.MAIN_SCREEN
            ;
    SCREENS PreviousScreen;
    private boolean CloseApp;
    protected String[] ScreensList;
    MenuItem SettingsButton;
    MenuItem SimulateButton;


    //--- Database Related
    SQLiteDatabase GSK_Database;
    String DatabaseName = "gsk_settings.db";
    //--- Settings Related
    Integer[] SettingsDefault = {
            100, 100, 10
    };
    Integer[][] SettingsLimits = {
            {10, 1000}, {10, 10000}, {1, 100}
    };
    Integer[] PreviousSettings = {
            0, 0, 0
    };
    String[] SettingsDBColumns = {"SamplingTime", "ChartHistoryLength", "ChartWindowLength"};
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

    SimulateAlgorithm SimHandle;



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
        ConnectToDatabase();
        //--- Generate Settings window
        GenerateSettingsView ();
        //--- Var vals
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
        SettingsSeekBars = new IndicatorSeekBar[3];
        for (int i=SettingsDefault.length-1; i>=0; i--) {
            TempTextView = new TextView(getApplicationContext());
            TempTextView.setText(getResources().getStringArray(R.array.SETTINGS_WINDOW)[i]);
            TempTextView.setTypeface(null, Typeface.BOLD);
            SettingsSeekBars[i] = new IndicatorSeekBar.Builder(getApplicationContext())
                    .setMin(SettingsLimits[i][0])
                    .setMax(SettingsLimits[i][1])
                    .thumbProgressStay(true)
                    .build();
            ((LinearLayout) findViewById(R.id.Settings)).addView(SettingsSeekBars[i],0);
            ((LinearLayout) findViewById(R.id.Settings)).addView(TempTextView,0);
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
                        SimHandle = new SimulateAlgorithm();
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
    private class SimulateAlgorithm extends AsyncTask <SimulateParams, ProgressParams, Integer> {
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
                    RecData[i] = Float.parseFloat(RecStrs[i]);
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
                    RecData[i] = ConvertBytesToInt(Rec.getBytes()[i+2],Rec.getBytes()[i + 1]);
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
            int PresentItem=0;
            byte[] ReadBuff = new byte[20];
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
                        PresentItem++;
                    }
                } while ((((System.currentTimeMillis() - StartTime))%10) != 0);
                if (RecedData)
                    Log.i("Timing", String.valueOf(PValues[0]));

                try {
                    port.write("00".getBytes(),10);
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
            GraphSeries0.appendData(new DataPoint(Params[0].Values[0], Params[0].Values[1]), true, 1000);
            GraphSeries1.appendData(new DataPoint(Params[0].Values[2], Params[0].Values[3]), true, 1000);
            GraphSeries2.appendData(new DataPoint(Params[0].Values[4], Params[0].Values[5]), true, 1000);
            if (IsProgressFirstIteration) {
                IsProgressFirstIteration=false;
                graph.addSeries(GraphSeries0);
                graph.addSeries(GraphSeries1);
                graph.addSeries(GraphSeries2);
                graph.getViewport().setScalable(true);
                graph.getViewport().setScalableY(true);
                graph.getViewport().setScrollable(true);
                graph.getViewport().setScrollableY(true);
                graph.getViewport().setMinX(0);
                graph.getViewport().setMaxX(10);
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
        private Integer ConvertBytesToInt (byte LSB, byte MSB) {
            Integer val=0;
            if ((MSB & 0x80) == 0x80)
                val = -1;
            val = val | LSB ;
            val = val | ((int) MSB<<8) ;
            return val;
        }
    }

}













