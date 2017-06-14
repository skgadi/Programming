package com.skgadi.progtransit000;

import android.content.Context;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.hoho.android.usbserial.driver.UsbSerialDriver;
import com.hoho.android.usbserial.driver.UsbSerialPort;
import com.hoho.android.usbserial.driver.UsbSerialProber;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    protected UsbManager manager;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            try {
                ConnectUSB();
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "First Exception",Toast.LENGTH_SHORT).show();
            }
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


        //For communication
        // Find all available drivers from attached devices.
        try {
            ConnectUSB();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(MainActivity.this, "First Exception",Toast.LENGTH_SHORT).show();
        }
    }

    private void ConnectUSB () throws IOException {
        manager = (UsbManager) getSystemService(Context.USB_SERVICE);
        List<UsbSerialDriver> availableDrivers = UsbSerialProber.getDefaultProber().findAllDrivers(manager);
        if (availableDrivers.isEmpty()) {
            return;
        }
        // Open a connection to the first available driver.
        UsbSerialDriver driver = availableDrivers.get(0);
        UsbDeviceConnection connection = manager.openDevice(driver.getDevice());
        if (connection == null) {
            // You probably need to call UsbManager.requestPermission(driver.getDevice(), ..)
            return;
        }
        // Read some data! Most have just one port (port 0).
        UsbSerialPort port = driver.getPorts().get(0);
        try {
            port.open(connection);
            port.setParameters(9600, 8, UsbSerialPort.STOPBITS_1, UsbSerialPort.PARITY_NONE);
            byte a[] = {'a'};
            port.write(a, 1);
            byte buffer[] = new byte[16];
            int numBytesRead = port.read(buffer, 1);
            mTextMessage.append("Read " + buffer + " which is " + numBytesRead + " bytes.\n");
            //Log.d(TAG, "Read " + numBytesRead + " bytes.");
        } catch (IOException e) {
            Toast.makeText(MainActivity.this, "Exception",Toast.LENGTH_SHORT).show();
            // Deal with error.
        } finally {
            port.close();
            Toast.makeText(MainActivity.this, "PORT Closed",Toast.LENGTH_SHORT).show();
        }

    }

}
