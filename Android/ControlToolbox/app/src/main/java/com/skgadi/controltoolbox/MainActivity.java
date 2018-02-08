package com.skgadi.controltoolbox;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbManager;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


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
    ON
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

    public void DrawSine(View v) throws InterruptedException {
        chart = (LineChart) findViewById(R.id.pid_chart0);
        List<Entry> entries = new ArrayList<Entry>();
        LineDataSet dataSet = new LineDataSet(entries, "Label");
        LineData lineData = new LineData(dataSet);


        for (int i=0; i<(10*360); i++) {
            lineData.addEntry( new Entry(i/10.0f, (float)(Math.sin(i*Math.PI/1800))), 0);
            //chart.getData().addEntry(new Entry(i/10.0f, (float)(Math.sin(i*Math.PI/1800))), 0);
        }
        chart.setData(lineData);
        chart.invalidate();
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
                    ChangeStateToNotSimulating();
                } else {
                    if (SimulationState == SIMULATION_STATUS.OFF) {
                        ChangeStateToSimulating();
                        SimulateParams SParams = new SimulateParams(port, PresentScreen);
                        new SimulateAlgorithm().execute(SParams);
                        Toast.makeText(MainActivity.this,
                                "started simulating",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        if (SimulationState == SIMULATION_STATUS.DISABLED) {
                            Toast.makeText(MainActivity.this,
                                    getResources().getStringArray(R.array.TOASTS)[8],
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
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
        LineData lineData0, lineData1, lineData2;
        @Override
        protected Integer doInBackground(SimulateParams... Params) {
            float[] PValues = new float[2];
            List<Entry> entries = new ArrayList<Entry>();
            LineDataSet dataSet = new LineDataSet(entries, "Label");
            lineData0 = new LineData(dataSet);
            ProgressParams PParams = new ProgressParams(PValues);
            port = Params[0].Port;
            byte SendBuffer[] = new byte[3];
            SendBuffer[0] = '1';
            SendBuffer[0] = '\r';
            SendBuffer[0] = '\n';
            byte RecBuffer[] = new byte[25];
            for (int i=0;i<100; i++) {
                try {
                    port.write(SendBuffer,10);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    port.read(RecBuffer, 100);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                PValues[0] = i;
                PValues[1] = 2* ((float) Math.pow(i, 2));
                lineData0.addEntry( new Entry(PValues[0], PValues[1]), 0);
                publishProgress(PParams);
            }
            return null;
        }

        protected void onProgressUpdate(ProgressParams... Params) {
            chart = (LineChart) findViewById(R.id.pid_chart0);
            LineData lineData;
            //if (Params[0].Values[0]==0) {
                List<Entry> entries = new ArrayList<Entry>();
                LineDataSet dataSet = new LineDataSet(entries, "Label");
                lineData = new LineData(dataSet);
            //} else {
               // lineData = chart.getLineData();
            //}

            chart.setData(lineData0);
            chart.invalidate();

        }

        protected void onPostExecute(Integer result) {

        }
    }

}













