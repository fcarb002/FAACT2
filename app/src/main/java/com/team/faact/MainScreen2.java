package com.team.faact;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.*;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;
import android.os.AsyncTask;
import java.io.*;
import java.lang.*;
import java.lang.Object;
import android.net.wifi.*;
import android.widget.Toast;


public class MainScreen2 extends AppCompatActivity  {
    WifiManager wifimanager;
    BluetoothConnection btc;
    int REQUEST_ENABLE_BT = 0;
    private static final int DISCOVER_DURATION = 300;

    // our request code (must be greater than zero)
    private static final int REQUEST_BLU = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen2);
        final String str = "";
        final TextView textField = findViewById(R.id.textField);
        //ImageButton wifiButton = findViewById(R.id.wifiButton);
        final EditText enterEmail = findViewById(R.id.enterEmail);
        Button sendButton = findViewById(R.id.sendButton);
        textField.setMovementMethod(new ScrollingMovementMethod());
        final ConnectivityManager conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo activeNetwork = conMgr.getActiveNetworkInfo();


        if (activeNetwork != null && activeNetwork.isConnected() )
        {

            try {
                // Create a URL for the desired page
                URL url = new URL("http://10.109.69.90/logfile.txt");

                // launch task
                new ReadTextTask().execute(url);
            }
            catch (MalformedURLException e) {
                // ** do something here **
            }

        }
        else
        {

        }



/*
//        try {
            String data = "";
            final StringBuffer myBuffer = new StringBuffer();
//            final URL url = new URL("http://humanstxt.org/humans.txt");
            InputStream istream = this.getResources().openRawResource(R.raw.logfile);
//            InputStream istream = url.openStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(istream));
            try {
                while ((data = br.readLine()) != null) {
                    myBuffer.append(data + "\n");
                }
                textField.setText(myBuffer);
                istream.close();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }

        } catch (IOException e) {
            e.printStackTrace();
        }

*/

        /*
        WifiManager wifiManager = (WifiManager) c.getSystemService(Context.WIFI_SERVICE);


        wifiButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (bluetoothAdapter == null) {
                    Toast.makeText(getApplicationContext(),"Bluetooth Not Supported",Toast.LENGTH_SHORT).show();
                }
                if (!bluetoothAdapter.isEnabled()) {
                    Intent discoveryIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                    discoveryIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION,
                            DISCOVER_DURATION );
                    startActivityForResult(discoveryIntent, REQUEST_BLU);
                    Toast.makeText(getApplicationContext(),"Turning Bluetooth ON...",Toast.LENGTH_SHORT).show();
                }
                if (bluetoothAdapter.isEnabled()) {
                    bluetoothAdapter.disable();
                    Toast.makeText(getApplicationContext(),"Turning Bluetooth OFF...",Toast.LENGTH_SHORT).show();
                }
            }
        });
        */

        sendButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent it = new Intent(Intent.ACTION_SEND);
                it.putExtra(Intent.EXTRA_EMAIL, new String[]{enterEmail.getText().toString()});
                it.putExtra(Intent.EXTRA_SUBJECT,"FAACT Sensor Data Log");
                it.putExtra(Intent.EXTRA_TEXT,textField.getText().toString());
                it.setType("message/rfc822");
                startActivity(Intent.createChooser(it,"Choose Mail App"));
            }
        });

        /*
        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();

        if (pairedDevices.size() > 0) {
            // There are paired devices. Get the name and address of each paired device.
            for (BluetoothDevice device : pairedDevices) {
                String deviceName = device.getName();
                String deviceHardwareAddress = device.getAddress(); // MAC address
                //textField.setText(deviceName);
            }

        }
        */
    }


    private class ReadTextTask extends AsyncTask<URL, Void, String> {
        String str = null;
        final StringBuffer myBuffer = new StringBuffer();
        @Override
        protected String doInBackground(URL... urls) {

            try {
                // Read all the text returned by the server

                InputStream istream = urls[0].openStream();
                BufferedReader in = new BufferedReader(new InputStreamReader(istream));

                try {
                    while ((str = in.readLine()) != null) {
                        myBuffer.append(str + "\n");
                    }
                    istream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                str = in.readLine();
                in.close();
            }
            catch (IOException e) {
                // ** do something here **
            }
            return str;
        }

        @Override
        protected void onPostExecute(String result) {
            TextView textField = (TextView) findViewById(R.id.textField);
            textField.setText(myBuffer);
        }
    }
}

