package com.skgadi.controltoolbox;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
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
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.hoho.android.usbserial.driver.CdcAcmSerialDriver;
import com.hoho.android.usbserial.driver.ProbeTable;
import com.hoho.android.usbserial.driver.UsbSerialDriver;
import com.hoho.android.usbserial.driver.UsbSerialPort;
import com.hoho.android.usbserial.driver.UsbSerialProber;
import com.hoho.android.usbserial.util.SerialInputOutputManager;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


enum SCREENS {
    MAIN_SCREEN,
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
    SCREENS PresentScreen;
    private boolean CloseApp;
    protected String[] ScreensList;
    MenuItem ConnectButton;
    MenuItem SimulateButton;


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
            } else {
                SetScreenTo(SCREENS.MAIN_SCREEN);
            }
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //--- Var vals
        Screens = new LinearLayout[SCREENS.values().length];
        Screens[0] = (LinearLayout) findViewById(R.id.Main);
        Screens[1] = (LinearLayout) findViewById(R.id.PID);
        Screens[2] = (LinearLayout) findViewById(R.id.IDENTIFICATION0);
        Screens[3] = (LinearLayout) findViewById(R.id.IDENTIFICATION1);
        Screens[4] = (LinearLayout) findViewById(R.id.IDENTIFICATION2);
        DefaultLayoutParams =  new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.FILL_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        SimulationState = SIMULATION_STATUS.DISABLED;
        //--- Add buttons
        ScreensList = getResources().getStringArray(R.array.SCREENS_LIST);
        Button ButtonForMainScreen;
        for (int i=1; i<ScreensList.length; i++) {
            ButtonForMainScreen = new Button(this);
            ButtonForMainScreen.setText(ScreensList[i]);
            ButtonForMainScreen.setLayoutParams(DefaultLayoutParams);
            ButtonForMainScreen.setOnClickListener(new OnMainWindowButton(i));
            Screens[0].addView(ButtonForMainScreen);
        }

        //--- USB related initialization
        /*customTable = new ProbeTable();
        //customTable.addProduct(0x4D8, 0x000A, CdcAcmSerialDriver.class);
        customTable.addProduct(0x2341, 0x43, CdcAcmSerialDriver.class);
        prober = new UsbSerialProber(customTable);*/ // Not required for arduino
    }

    private void SetScreenTo (SCREENS Screen) {
        for (int i=0; i<SCREENS.values().length; i++)
            Screens[i].setVisibility(View.GONE);
        PresentScreen = Screen;
        Screens[PresentScreen.ordinal()].setVisibility(View.VISIBLE);
        SetProperSimulateButtonStatus();
        /*switch (Screen){
            case MAIN_SCREEN:
                break;
            case PID:
                break;
            case IDENTIFICATION0:
                break;
            case IDENTIFICATION1:
                break;
            case IDENTIFICATION2:
                break;
            default:
                break;
        }
        setTitle(getResources().getString(R.string.app_name)
                + ": "
                +ScreensList[PresentScreen]);
        Toast.makeText(getApplicationContext(), ScreensList[PresentScreen], Toast.LENGTH_SHORT).show();
        */
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
        ConnectButton = menu.getItem(0);
        SimulateButton = menu.getItem(1);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.connect:
                if (DeviceConnected)
                    DisconnectUSB();
                else
                    ConnectUSB();
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
                    ChangeStateToSimulating();
                    SimulateParams SParams = new SimulateParams(port, PresentScreen);
                    SimHandle = new SimulateAlgorithm();
                    SimHandle.execute(SParams);
                    Toast.makeText(MainActivity.this,
                            "started simulating",
                            Toast.LENGTH_SHORT).show();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    void SetProperSimulateButtonStatus () {
        if (!DeviceConnected || PresentScreen == SCREENS.MAIN_SCREEN) {
            ChangeStateToSimulateDisabled();
        } else {
            if (SimulationState == SIMULATION_STATUS.ON) {
                ChangeStateToSimulating();
            } else {
                ChangeStateToNotSimulating();
            }
        }
    }
    void StopSimulation () {

    }
    void ChangeStateToConnected () {
        DeviceConnected = true;
        ConnectButton.setIcon(R.drawable.icon_disconnect);
        SetProperSimulateButtonStatus ();
    }
    void ChangeStateToDisconnected () {
        DeviceConnected = false;
        ConnectButton.setIcon(R.drawable.icon_connect);
        SetProperSimulateButtonStatus();
    }
    void ChangeStateToSimulateDisabled () {
        SimulationState = SIMULATION_STATUS.DISABLED;
        SimulateButton.setIcon(R.drawable.icon_simulate_disabled);
    }
    void ChangeStateToSimulating () {
        SimulationState = SIMULATION_STATUS.ON;
        SimulateButton.setIcon(R.drawable.icon_simulate_stop);
    }
    void ChangeStateToNotSimulating () {
        SimulationState = SIMULATION_STATUS.OFF;
        SimulateButton.setIcon(R.drawable.icon_simulate_start);
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
            ChangeStateToDisconnected();
            return 0;
        }
        if (drivers.isEmpty()) {
            Toast.makeText(MainActivity.this,
                    getResources().getStringArray(R.array.TOASTS)[1],
                    Toast.LENGTH_SHORT).show();
            ChangeStateToDisconnected();
            return 0;
        }
        driver = drivers.get(0);
        connection = manager.openDevice(driver.getDevice());
        if (connection == null) {
            // You probably need to call UsbManager.requestPermission(driver.getDevice(), ..)
            String ACTION_USB_PERMISSION = "com.android.example.USB_PERMISSION";
            PendingIntent mPermissionIntent =
                    PendingIntent.getBroadcast(MainActivity.this,
                            0,
                            new Intent(ACTION_USB_PERMISSION),
                            0);
            manager.requestPermission(driver.getDevice(), mPermissionIntent);
            Toast.makeText(MainActivity.this,
                    getResources().getStringArray(R.array.TOASTS)[2],
                    Toast.LENGTH_SHORT).show();
            ChangeStateToDisconnected();
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
            ChangeStateToConnected();
            return 2;
        } catch (IOException e) {
            Toast.makeText(MainActivity.this,
                    getResources().getStringArray(R.array.TOASTS)[4],
                    Toast.LENGTH_SHORT).show();
            e.printStackTrace();
            ChangeStateToDisconnected();
            return 0;
        }
    }
    void DisconnectUSB () {
        try {
            port.close();
            Toast.makeText(MainActivity.this,
                    getResources().getStringArray(R.array.TOASTS)[5],
                    Toast.LENGTH_SHORT).show();
            ChangeStateToDisconnected();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(MainActivity.this,
                    getResources().getStringArray(R.array.TOASTS)[6],
                    Toast.LENGTH_SHORT).show();
            ChangeStateToDisconnected();
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
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>();

        boolean IsProgressFirstIteration=true;
        DataPoint[] DataPoints;
        float[] RecData = new float[3];
        int readCount=0;
        boolean isValidRead=false;
        int readSize = 0;


        private ExecutorService mExecutor = Executors.newSingleThreadExecutor();
        private SerialInputOutputManager mSerialIoManager;
        private final SerialInputOutputManager.Listener mListener =
                new SerialInputOutputManager.Listener() {

                    @Override
                    public void onRunError(Exception e) {
                        //Log.d(TAG, "Runner stopped.");
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
        private void DataRecUpdate (byte[] data) {
            String Rec = new String(data);
            Log.i("USBRec", Rec);
            String[] RecStrs = Rec.split(";");
            if (RecStrs.length == 3) {
                isValidRead = true;
                for (int i = 0; i < RecStrs.length; i++) {
                    if (RecStrs.length > i)
                        RecData[i] = Float.parseFloat(RecStrs[i]);
                }
            }
            readCount++;
        }

        @Override
        protected Integer doInBackground(SimulateParams... Params) {
            float[] PValues = new float[2];
            //series = new LineGraphSeries<DataPoint>();
            DataPoints = new DataPoint[1000];

            ProgressParams PParams = new ProgressParams(PValues);
            port = Params[0].Port;

            //mSerialIoManager = new SerialInputOutputManager(port, mListener);
            //mExecutor.submit(mSerialIoManager);

            try {
                port.purgeHwBuffers(true, true);
            } catch (Exception e) {
                e.printStackTrace();
            }
            long StartTime = System.currentTimeMillis();
            int PresentItem=0;
            byte[] ReadBuff = new byte[20];
            while(!this.isCancelled()) {
                try {
                    port.write("00".getBytes(),1);
                    port.read(ReadBuff, 1);
                    DataRecUpdate(ReadBuff);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                boolean RecedData = false;
                do {
                    if (isValidRead) {
                        isValidRead  = false;
                        PValues[0]  = (System.currentTimeMillis()-StartTime)/1000.0f;
                        PValues[1] = RecData[0];
                        publishProgress(PParams);
                        PresentItem++;
                    }
                } while ((((System.currentTimeMillis()-StartTime))%10) != 0);
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(ProgressParams... Params) {
            GraphView graph = (GraphView) findViewById(R.id.pid_chart00);
            series.appendData(new DataPoint(Params[0].Values[0],Params[0].Values[1]),true, 10000);
            if (IsProgressFirstIteration) {
                IsProgressFirstIteration=false;
                graph.addSeries(series);
                graph.getViewport().setScalable(true);
                graph.getViewport().setScalableY(true);
                graph.getViewport().setScrollable(true);
                graph.getViewport().setScrollableY(true);
                graph.getViewport().setMinX(0);
                graph.getViewport().setMaxX(5);
            }
        }

        @Override
        protected void onPostExecute(Integer result) {
            //mSerialIoManager.stop();
            //mExecutor.shutdown();
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
            //mSerialIoManager.stop();
            //mExecutor.shutdown();
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
        private Integer ConvertBytesToInt (byte LSB, byte MSB) {
            Integer val=0;
            val = val | LSB ;
            val = val | ((int) MSB<<8) ;
            if ((MSB & 0x80) > 0) {
                for (int i=0; i< (Integer.BYTES-2); i++) {
                    val = val | (0xff<<8*(i+1));
                }
            }
            return val;
        }
    }

}













