package com.skgadi.progtransit000;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
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
import java.nio.ByteBuffer;
import java.util.List;
import java.util.concurrent.Phaser;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    protected UsbManager manager;
    ProbeTable customTable;
    UsbSerialProber prober;
    List<UsbSerialDriver> drivers;
    UsbSerialDriver driver;
    UsbDeviceConnection connection;
    private UsbSerialPort port;

    private ProgressBar ProgramProgressBar;

    ToggleButton ConnectButton;
    Button ProgramButton;
    private enum DEVICE_STATE {
        DISCONNECTED,
        CONNECTED
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Toast.makeText(getApplicationContext(), "First Exception",Toast.LENGTH_SHORT).show();
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
                case R.id.navigation_about:
                    mTextMessage.setText(R.string.title_about);
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //Added by Suresh Gadi
        //Pulling variables from xml
        ProgramButton = (Button) findViewById(R.id.programButton);
        ConnectButton = (ToggleButton) findViewById(R.id.toggleConnectButton);
        ProgramProgressBar = (ProgressBar) findViewById(R.id.ProgramProgressBar);


        customTable = new ProbeTable();
        customTable.addProduct(0x4D8, 0x000A, CdcAcmSerialDriver.class);
        prober = new UsbSerialProber(customTable);
        ConnectButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    int status = ConnectUSB ();
                    if (status == 2)
                        SetStateAs(DEVICE_STATE.CONNECTED);
                    else
                        SetStateAs(DEVICE_STATE.DISCONNECTED);
                } else {
                    try {
                        SetStateAs(DEVICE_STATE.DISCONNECTED);
                        port.close();
                        Toast.makeText(MainActivity.this, R.string.success_device_disconnected,Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(MainActivity.this, R.string.error_device_disconnect,Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
    private void SetStateAs (DEVICE_STATE state) {
        switch (state) {
            case CONNECTED:
                ConnectButton.setChecked(true);
                ProgramButton.setVisibility(View.VISIBLE);
                return;
            case DISCONNECTED:
                ConnectButton.setChecked(false);
                ProgramButton.setVisibility(View.INVISIBLE);
                //ProgramButton.setVisibility(View.VISIBLE);
                return;
        }
    }

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


    public void SendAndVerify (View v) {
        new ProgramDeviceTask().execute(port);
        return;
    }
    private void setProgressPercent(Integer Percent) {
        ProgramProgressBar.setProgress(Percent);
    }
    private class ProgramDeviceTask extends AsyncTask<UsbSerialPort, Integer, Integer> {
        private boolean isSuccessful = true;
        private int lastErrorLocation=0;
        private int noOfErrors = 0;
        private int noOfSuccess = 0;
        private byte SendBuffer[] = new byte[6];

        private void PrepareSendBufferToWrite(int address, int data) {
            SendBuffer[0] = 0x32;
            SendBuffer[1] = (byte) ((address & 0xff0000)>>16);
            SendBuffer[2] = (byte) ((address & 0x00ff00)>>8);
            SendBuffer[3] = (byte) ((address & 0x0000ff));
            SendBuffer[4] = (byte) ((data & 0xff00)>>8);
            SendBuffer[5] = (byte) ((data & 0x00ff));
            return;
        }

        private void PrepareStartCommand(){
            SendBuffer[0] = 0x30;
            return;
        }

        private void PrepareEndCommand(){
            SendBuffer[0] = 0x31;
            return;
        }

        protected Integer doInBackground(UsbSerialPort... ports) {
            UsbSerialPort port;
            port = ports[0];
            int RecBuffLength=0;
            byte RecBuffer[] = new byte[4];
            int MAddress = 0x310000;
            byte Data = 0x00;
            int EE_Settings[]={0x7A, 0xFE, 1, 4, 1, 10, 20, 0xF6, 0xFF, 60, 40, 4, 8, 192, 8, 42, 5, 1, 128, 81, 1, 0, 5, 0xC5, 0x80, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x20, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 7, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 160, 0, 1, 0, 5, 1, 127, -1, -1, -1, -1, 1, 127, -1, -1, -1, -1, 1, 127, -1, -1, -1, -1, 1, 127, -1, -1, -1, -1, 1, 127, -1, -1, -1, -1, 1, 127, -1, -1, -1, -1, 1, 127, -1, -1, -1, -1, 1, 127, -1, -1, -1, -1, 1, 127, -1, -1, -1, -1, 1, 127, -1, -1, -1, -1, 1, 127, -1, -1, -1, -1, 1, 127, -1, -1, -1, -1, 1, 127, -1, -1, -1, -1, 1, 127, -1, -1, -1, -1, 1, 127, -1, -1, -1, -1, 1, 127, -1, -1, -1, -1, 1, 127, -1, -1, -1, -1, 1, 127, -1, -1, -1, -1, 1, 127, -1, -1, -1, -1, 1, 127, -1, -1, -1, -1, 1, 127, -1, -1, -1, -1, 1, 127, -1, -1, -1, -1, 1, 127, -1, -1, -1, -1, 1, 127, 82, 0x20, 0x90, 0x01, 0x00, 3, 0x22, 0x90, 0x00, 0x00, 6, 0x0C, 0x90, 0x00, 0x00, 2, 0x04, 0x90, 0x08, 0x00, 2, 0x14, 0x90, 0x00, 0x00, 21, 0x24, 0x28, 0x00, 0x00, 2, 0x24, 0x10, 0x00, 0x20, 2, 0x24, 0x50, 0x00, 0x00, 1, 0x00, 0x00, 0x14, 0x20, 0, 0x00, 0x00, 0x00, 0x00, 0, 0x00, 0x00, 0x00, 0x00, 0, 0x00, 0x00, 0x00, 0x00, 0, 0x00, 0x00, 0x00, 0x00, 0, 0x00, 0x00, 0x00, 0x00, 0, 0x00, 0x00, 0x00, 0x00, 0, 0x00, 0x00, 0x00, 0x00, 0, 0x00, 0x00, 0x00, 0x00, 0, 0x00, 0x00, 0x00, 0x00, 0, 0x00, 0x00, 0x00, 0x00, 0, 0x00, 0x00, 0x00, 0x00, 0, 0x00, 0x00, 0x00, 0x00, 0, 0x00, 0x00, 0x00, 0x00, 0, 0x00, 0x00, 0x00, 0x00, 0, 0x00, 0x00, 0x00, 0x00, 0, 0x00, 0x00, 0x00, 0x00, 0, 0x00, 0x00, 0x00, 0x00, 0, 0x00, 0x00, 0x00, 0x00, 0, 0x00, 0x00, 0x00, 0x00, 0, 0x00, 0x00, 0x00, 0x00, 0, 0x00, 0x00, 0x00, 0x00, 0, 0x00, 0x00, 0x00, 0x00, 0, 0x00, 0x00, 0x00, 0x00, 0, 0x00, 0x00, 0x00, 0x00, 0, 0x00, 0x00, 0x00, 0x00, 0, 0x00, 0x00, 0x00, 0x00, 0, 0x00, 0x00, 0x00, 0x00, 0, 0x00, 0x00, 0x00, 0x00, 0, 0x00, 0x00, 0x00, 0x00, 0, 0x00, 0x00, 0x00, 0x00, 0, 0x00, 0x00, 0x00, 0x00, 0, 0x00, 0x00, 0x00, 0x00, 0, 0x00, 0x00, 0x00, 0x00, 0, 0x00, 0x00, 0x00, 0x00, 0, 0x00, 0x00, 0x00, 0x00, 0, 0x00, 0x00, 0x00, 0x00, 0, 0x00, 0x00, 0x00, 0x00, 0, 0x00, 0x00, 0x00, 0x00, 0, 0x00, 0x00, 0x00, 0x00, 0, 0x00, 0x00, 0x00, 0x00, 0, 0x00, 0x00, 0x00, 0x00, 0, 0x00, 0x00, 0x00, 0x00, 0, 0x00, 0x00, 0x00, 0x00, 0, 0x00, 0x00, 0x00, 0x00, 0, 0x00, 0x00, 0x00, 0x00, 0, 0x00, 0x00, 0x00, 0x00, 0, 0x00, 0x00, 0x00, 0x00, 0, 0x00, 0x00, 0x00, 0x00, 0, 0x00, 0x00, 0x00, 0x00, 0, 0x00, 0x00, 0x00, 0x00, 0, 0x00, 0x00, 0x00, 0x00, 0, 0x00, 0x00, 0x00, 0x00, 0, 0x00, 0x00, 0x00, 0x00, 0, 0x00, 0x00, 0x00, 0x00, 0, 0x00, 0x00, 0x00, 0x00, 0, 0x00, 0x00, 0x00, 0x00, 0, 0x00, 0x00, 0x00, 0x00, 0, 0x00, 0x00, 0x00, 0x00, 0, 0x00, 0x00, 0x00, 0x00, 0, 0x00, 0x00, 0x00, 0x00, 0, 0x00, 0x00, 0x00, 0x00, 0, 0x00, 0x00, 0x00, 0x00, 0, 0x00, 0x00, 0x00, 0x00, 0, 0x00, 0x00, 0x00, 0x00, 0, 0x00, 0x00, 0x00, 0x00, 0, 0x00, 0x00, 0x00, 0x00, 0, 0x00, 0x00, 0x00, 0x00, 0, 0x00, 0x00, 0x00, 0x00, 0, 0x00, 0x00, 0x00, 0x00, 0, 0x00, 0x00, 0x00, 0x00, 0, 0x00, 0x00, 0x00, 0x00, 0, 0x00, 0x00, 0x00, 0x00, 0, 0x00, 0x00, 0x00, 0x00, 0, 0x00, 0x00, 0x00, 0x00, 0, 0x00, 0x00, 0x00, 0x00, 0, 0x00, 0x00, 0x00, 0x00, 0, 0x00, 0x00, 0x00, 0x00, 0, 0x00, 0x00, 0x00, 0x00, 0, 0x00, 0x00, 0x00, 0x00, 0, 0x00, 0x00, 0x00, 0x00, 0, 0x00, 0x00, 0x00, 0x00, 0, 0x00, 0x00, 0x00, 0x00, 0, 0x00, 0x00, 0x00, 0x00, 0, 0x00, 0x00, 0x00, 0x00, 0, 0x00, 0x00, 0x00, 0x00, 0, 0x00, 0x00, 0x00, 0x00, 0, 0x00, 0x00, 0x00, 0x00, 0, 0x00, 0x00, 0x00, 0x00, 0, 0x00, 0x00, 0x00, 0x00, 0, 0x00, 0x00, 0x00, 0x00, 0, 0x00, 0x00, 0x00, 0x00, 0, 0x00, 0x00, 0x00, 0x00, 0, 0x00, 0x00, 0x00, 0x00, 0, 0x00, 0x00, 0x00, 0x00, 0, 0x00, 0x00, 0x00, 0x00, 0, 0x00, 0x00, 0x00, 0x00, 0, 0x00, 0x00, 0x00, 0x00, 0, 0x00, 0x00, 0x00, 0x00, 0, 0x00, 0x00, 0x00, 0x00, 0, 0x00, 0x00, 0x00, 0x00, 0, 0x00, 0x00, 0x00, 0x00, 0, 0x00, 0x00, 0x00, 0x00, 0, 0x00, 0x00, 0x00, 0x00, 0, 0x00, 0x00, 0x00, 0x00, 0, 0x00, 0x00, 0x00, 0x00, 0, 0x00, 0x00, 0x00, 0x00, 0, 0x00, 0x00, 0x00, 0x00, 0, 0x00, 0x00, 0x00, 0x00, 0, 0x00, 0x00, 0x00, 0x00, 0, 0x00, 0x00, 0x00, 0x00, 0, 0x00, 0x00, 0x00, 0x00, 0, 0x00, 0x00, 0x00, 0x00, 0, 0x00, 0x00, 0x00, 0x00, 0, 0x00, 0x00, 0x00, 0x00, 0, 0x00, 0x00, 0x00, 0x00, 0, 0x00, 0x00, 0x00, 0x00, 0, 0x00, 0x00, 0x00, 0x00, 0, 0x00, 0x00, 0x00, 0x00, 0, 0x00, 0x00, 0x00, 0x00, 0, 0x00, 0x00, 0x00, 0x00, 0, 0x00, 0x00, 0x00, 0x00, 0, 0x00, 0x00, 0x00, 0x00, 0, 0x00, 0x00, 0x00, 0x00, 0, 0x00, 0x00, 0x00, 0x00, 0, 0x00, 0x00, 0x00, 0x00, 0, 0x00, 0x00, 0x00, 0x00, 0, 0x00, 0x00, 0x00, 0x00, 0, 0x00, 0x00, 0x00, 0x00, 0, 0x00, 0x00, 0x00, 0x00, 0, 0x00, 0x00, 0x00, 0x00, 0, 0x00, 0x00, 0x00, 0x00, 0, 0x00, 0x00, 0x00, 0x00, 0, 0x00, 0x00, 0x00, 0x00, 0, 0x00, 0x00, 0x00, 0x00, 0, 0x00, 0x00, 0x00, 0x00, 0, 0x00, 0x00, 0x00, 0x00, 0, 0x00, 0x00, 0x00, 0x00, 0, 0x00, 0x00, 0x00, 0x00, 0, 0x00, 0x00, 0x00, 0x00, 0, 0x00, 0x00, 0x00, 0x00, 0, 0x00, 0x00, 0x00, 0x00, 0, 0x00, 0x00, 0x00, 0x00, 0, 0x00, 0x00, 0x00, 0x00, 0, 0x00, 0x00, 0x00, 0x00, 0, 0x00, 0x00, 0x00, 0x00, 0, 0x00, 0x00, 0x00, 0x00, 0, 0x00, 0x00, 0x00, 0x00, 0, 0x00, 0x00, 0x00, 0x00, 0, 0x00, 0x00, 0x00, 0x00, 0, 0x00, 0x00, 0x00, 0x00, 0, 0x00, 0x00, 0x00, 0x00, };
            for (int i=0; i<5; i++) {
                PrepareEndCommand();
                try {
                    port.purgeHwBuffers(true, true);
                    port.write(SendBuffer, 10);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                PrepareStartCommand();
                try {
                    port.purgeHwBuffers(true, true);
                    port.write(SendBuffer, 10);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            /*try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
            for (int i=0; i<1024; i++) {
                /*try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }*/
                PrepareSendBufferToWrite(MAddress++, (byte) EE_Settings[i]);
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
                    RecBuffLength = port.read(RecBuffer, 100);
                    if (RecBuffer[0]==0x30) {
                        isSuccessful = false;
                        lastErrorLocation = i;
                        noOfErrors++;
                    } else if (RecBuffer[0]==0x31)
                        noOfSuccess++;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                publishProgress(((i+1)*100)/1024);
            }
            try {
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
            }
            return 1;
        }

        protected void onProgressUpdate(Integer... progress) {
            setProgressPercent(progress[0]);
        }

        protected void onPostExecute(Integer result) {
            if (isSuccessful)
                mTextMessage.append("\nDone");
            else
                mTextMessage.append("\nErrors:"+noOfErrors+"\nSuccess:"+noOfSuccess+"\nLast error at:"+ lastErrorLocation);
            mTextMessage.append("\nDone "+"\n"+ Integer.toHexString(SendBuffer[0])+"\n"+ Integer.toHexString(SendBuffer[1])+"\n"+
                    Integer.toHexString(SendBuffer[2])+"\n"+ Integer.toHexString(SendBuffer[3])
                    +"\n"+ Integer.toHexString(SendBuffer[4])+"\n"+ Integer.toHexString(SendBuffer[5]));
        }
    }

    // File handling Code

    /* Checks if external storage is available for read and write */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /* Checks if external storage is available to at least read */
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

    public File PrepareDirectoryForStoringConfigFiles() {
        // Get the directory for the app's private pictures directory.
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOWNLOADS), "ProgTransitConfigFiles");
        if (!file.mkdirs()) {
            mTextMessage.append("\nDirectory not created");
        } else {
            mTextMessage.append("\nDone");
        }
        return file;
    }

    public void ListFiles (View v) {
        if (isExternalStorageWritable()) {
            File file = PrepareDirectoryForStoringConfigFiles();
            if (file.exists())
                mTextMessage.append("\nDone");
            else
                mTextMessage.append("\nOpssss...");
        } else
            mTextMessage.append("\nNo :( ......");
    }
}
