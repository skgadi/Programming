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

import com.hoho.android.usbserial.driver.CdcAcmSerialDriver;
import com.hoho.android.usbserial.driver.ProbeTable;
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
                    mTextMessage.append("Home");
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.append("Dashboard");
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.append("Notifications");
                    return true;
                case R.id.navigation_about:
                    mTextMessage.append("About");
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
        /*try {
            ConnectUSB();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(MainActivity.this, "First Exception",Toast.LENGTH_SHORT).show();
        }*/
    }

    private void ConnectUSB () throws IOException {
        mTextMessage.append("step1");
        manager = (UsbManager) getSystemService(Context.USB_SERVICE);

        ProbeTable customTable = new ProbeTable();
        customTable.addProduct(1240, 10, CdcAcmSerialDriver.class);
        customTable.addProduct(0x1234, 0x0002, CdcAcmSerialDriver.class);

        UsbSerialProber prober = new UsbSerialProber(customTable);
        List<UsbSerialDriver> drivers = prober.findAllDrivers(manager);

        mTextMessage.append("step2");
        mTextMessage.append(drivers.toString());

        /*List<UsbSerialDriver> availableDrivers = UsbSerialProber.getDefaultProber().findAllDrivers(manager);
        mTextMessage.append(availableDrivers.toString());
        if (availableDrivers.isEmpty()) {
            return;
        }
        mTextMessage.append("step4");
        // Open a connection to the first available driver.
        UsbSerialDriver driver = availableDrivers.get(0);*/
        UsbSerialDriver driver = drivers.get(0);


        //sKGadi Edit
        /*for (int i=0; i<availableDrivers.size(); i++) {
            mTextMessage.append(i+availableDrivers.get(i).toString()+"\n");
        }*/
        //SKGadi Edit finish
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
            byte buffer[] = new byte[2];
            int numBytesRead = port.read(buffer, 10);
            mTextMessage.append("\nRead " + buffer[0] + " which is " + numBytesRead + " bytes.\n");
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
