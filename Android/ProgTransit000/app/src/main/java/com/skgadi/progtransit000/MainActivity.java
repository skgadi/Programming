package com.skgadi.progtransit000;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.hoho.android.usbserial.driver.CdcAcmSerialDriver;
import com.hoho.android.usbserial.driver.ProbeTable;
import com.hoho.android.usbserial.driver.UsbSerialDriver;
import com.hoho.android.usbserial.driver.UsbSerialPort;
import com.hoho.android.usbserial.driver.UsbSerialProber;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public TextView mTextMessage;
    protected UsbManager manager;
    ProbeTable customTable;
    UsbSerialProber prober;
    List<UsbSerialDriver> drivers;
    UsbSerialDriver driver;
    UsbDeviceConnection connection;
    public UsbSerialPort port;
    public byte SendBuffer[];

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
        ProgramButton = (Button) findViewById(R.id.programButton);
        ConnectButton = (ToggleButton) findViewById(R.id.toggleConnectButton);
        SendBuffer = new byte[6];

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

    public void PrepareSendBufferToWrite(int address, int data) {
        SendBuffer[0] = 0x02;
        SendBuffer[1] = (byte)(address>>16 & 0xff);
        SendBuffer[2] = (byte)(address>>8 & 0xff);
        SendBuffer[3] = (byte)(address & 0xff);
        SendBuffer[4] = (byte)(data>>8 & 0xff);
        SendBuffer[5] = (byte)(data & 0xff);
        ByteBuffer wrapped =ByteBuffer.wrap(SendBuffer);
        mTextMessage.append("\n"+Integer.toHexString(wrapped.getInt()));
        return;
    }

    public void SendAndVerify (View v) {
        int RecBuffLength=0;
        byte RecBuffer[] = new byte[4];
        int MAddress = 0x310000;
        byte Data = 10;
        PrepareSendBufferToWrite(MAddress, Data);
        for (int i=0; i<10; i++) {
            PrepareSendBufferToWrite(MAddress, Data);
            try {
                //port.purgeHwBuffers(true, true);
                port.write(SendBuffer, 10);
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(MainActivity.this, "Writing error",Toast.LENGTH_SHORT).show();
            }
            try {
                RecBuffLength = port.read(RecBuffer, 10);
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(MainActivity.this, "Receive error",Toast.LENGTH_SHORT).show();
            }
            mTextMessage.append("\nSent "+Data+ " to memory "+MAddress+" and Recevied "+RecBuffLength+" byes as "+ RecBuffer[0]+"  "+RecBuffer[1]+"  "+RecBuffer[2]+"  "+RecBuffer[3]);
            MAddress++;
            Data++;
        }
        return;
    }
}
