/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.bluetoothlegatt;
import android.app.Activity;
import android.app.Service;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.CountDownTimer;
import android.view.View.OnClickListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static com.example.android.bluetoothlegatt.BluetoothLeService.s1;


/**
 * For a given BLE device, this Activity provides the user interface to connect, display data,
 * and display GATT services and characteristics supported by the device.  The Activity
 * communicates with {@code BluetoothLeService}, which in turn interacts with the
 * Bluetooth LE API.
 */
public class DeviceControlActivity extends Activity  {

    private final static String TAG = DeviceControlActivity.class.getSimpleName();

    public static final String EXTRAS_DEVICE_NAME = "DEVICE_NAME";
    public static final String EXTRAS_DEVICE_ADDRESS = "DEVICE_ADDRESS";

    private TextView mConnectionState;
    private TextView mDataField;
    private String mDeviceName;
    private String mDeviceAddress;
    private ExpandableListView mGattServicesList;
    private BluetoothGattService mBluetoothGattService;
    private BluetoothGatt mBluetoothGatt;
    private BluetoothLeService mBluetoothLeService;
    private Service service;
    private ArrayList<ArrayList<BluetoothGattCharacteristic>> mGattCharacteristics =
            new ArrayList<ArrayList<BluetoothGattCharacteristic>>();
    private boolean mConnected = false;
    private BluetoothGattCharacteristic mNotifyCharacteristic;

    private final String LIST_NAME = "NAME";
    private final String LIST_UUID = "UUID";




    private static  Button button_sbm;
    TextView tvResult;

    private static  Button button_sbm1;
    // Custom Spinner adapter (ArrayAdapter<User>)
    // You can define as a private to use it in the all class
    // This is the object that is going to do the "magic"


    private BluetoothGattCharacteristic bluetoothGattCharacteristicHM_10;

    // Code to manage Service lifecycle.
    private final ServiceConnection mServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            mBluetoothLeService = ((BluetoothLeService.LocalBinder) service).getService();
            if (!mBluetoothLeService.initialize()) {
                Log.e(TAG, "Unable to initialize Bluetooth");
                finish();
            }
            // Automatically connects to the device upon successful start-up initialization.
            mBluetoothLeService.connect(mDeviceAddress);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mBluetoothLeService = null;
        }
    };

    // Handles various events fired by the Service.
    // ACTION_GATT_CONNECTED: connected to a GATT server.
    // ACTION_GATT_DISCONNECTED: disconnected from a GATT server.
    // ACTION_GATT_SERVICES_DISCOVERED: discovered GATT services.
    // ACTION_DATA_AVAILABLE: received data from the device.  This can be a result of read
    //                        or notification operations.
    private final BroadcastReceiver mGattUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
        String write= "*11#*17&00300#*18&03500#*21#*27&00300#*28&03500#*30#*42#*51&011819#*61&101010#*71&0015#*81&0030#*90#*00#$Y";
          //  a = String.valueOf(i)+"and" +String.valueOf(j);
            final String action = intent.getAction();

            if (BluetoothLeService.ACTION_GATT_CONNECTED.equals(action)) {
                mConnected = true;
                updateConnectionState(R.string.connected);
                invalidateOptionsMenu();
            } else if (BluetoothLeService.ACTION_GATT_DISCONNECTED.equals(action)) {
                mConnected = false;
                updateConnectionState(R.string.disconnected);
                invalidateOptionsMenu();
                clearUI();
            } else if (BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED.equals(action)) {
              //   Show all the supported services and characteristics on the user interface.
                displayGattServices(mBluetoothLeService.getSupportedGattServices());
            }
            else if (BluetoothLeService.ACTION_DATA_AVAILABLE.equals(action)) {
                displayData(intent.getStringExtra(BluetoothLeService.EXTRA_DATA));

            }
          /*  if(bluetoothGattCharacteristicHM_10 != null){
                   // bluetoothGattCharacteristicHM_10.setValue(x1);
                    bluetoothGattCharacteristicHM_10.setValue(write);
                    mBluetoothLeService.writeCharacteristic(bluetoothGattCharacteristicHM_10);
                    mBluetoothLeService.setCharacteristicNotification(bluetoothGattCharacteristicHM_10,true);
                }*/
        }
    };

    // If a given GATT characteristic is selected, check for supported features.  This sample
    // demonstrates 'Read' and 'Notify' features.  See
    // http://d.android.com/reference/android/bluetooth/BluetoothGatt.html for the complete02-09 16:08:13.501 559-574/com.example.android.bluetoothlegatt D/ScanRecord: parseFromBytes

    // list of supported characteristic features.
   /* private final ExpandableListView.OnChildClickListener servicesListClickListner =
            new ExpandableListView.OnChildClickListener() {
                @Override
                public boolean onChildClick(ExpandableListView parent, View v, int groupPosition,
                                            int childPosition, long id) {
                    if (mGattCharacteristics != null) {
                        final BluetoothGattCharacteristic characteristic =
                                mGattCharacteristics.get(groupPosition).get(childPosition);
                        final int charaProp = characteristic.getProperties();
                        if ((charaProp | BluetoothGattCharacteristic.PROPERTY_READ) > 0) {
                            // If there is an active notification on a characteristic, cl ear
                            // it first so it doesn't update the data field on the user interface.
                            if (mNotifyCharacteristic != null) {
                                mBluetoothLeService.setCharacteristicNotification(
                                        mNotifyCharacteristic, false);
                                mNotifyCharacteristic = null;
                            }
                            mBluetoothLeService.readCharacteristic(characteristic);
                        }
                        if ((charaProp | BluetoothGattCharacteristic.PROPERTY_NOTIFY) > 0) {
                            mNotifyCharacteristic = characteristic;
                            mBluetoothLeService.setCharacteristicNotification(
                                    characteristic, true);
                        }
                        return true;
                    }
                    return false;
                }
    }; */

    private void clearUI() {
       //mGattServicesList.setAdapter((SimpleExpandableListAdapter) null);
        mDataField.setText(R.string.no_data);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gatt_services_characteristics);
        tvResult = (TextView) findViewById(R.id.tvResult);
       // button_sbm1 = (Button) findViewById(R.id.button1);

        OnClickButtonListener();

       // OnClickButtonListener1();

        final Intent intent = getIntent();
        mDeviceName = intent.getStringExtra(EXTRAS_DEVICE_NAME);
        mDeviceAddress = intent.getStringExtra(EXTRAS_DEVICE_ADDRESS);



        // Sets up UI references.
        ((TextView) findViewById(R.id.device_address)).setText(mDeviceAddress);
       // mGattServicesList = (ExpandableListView) findViewById(R.id.gatt_services_list);
      //  mGattServicesList.setOnChildClickListener(servicesListClickListner);
        mConnectionState = (TextView) findViewById(R.id.connection_state);
        mDataField = (TextView) findViewById(R.id.data_value);
        getActionBar().setTitle(mDeviceName);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        Intent gattServiceIntent = new Intent(this, BluetoothLeService.class);
        bindService(gattServiceIntent, mServiceConnection, BIND_AUTO_CREATE);
    }

    public void OnClickButtonListener() {
        button_sbm = (Button)findViewById(R.id.button);
        button_sbm.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        //Intent intent = new Intent(this,SecondActivity.class);
                        Intent intent = new Intent("com.example.android.bluetoothlegatt.SecondActivity");
                       // startActivity(intent);
                        startActivityForResult(intent, 1);
                    }
                }
        );

    }



    public void buttonClick(View view){
        Intent myintent = new Intent(DeviceControlActivity.this, PreConfigured.class);
       // startActivity(myintent);
        startActivityForResult(myintent, 2);
    }





   /*public void OnClickButtonListener1() {
        button_sbm1 = (Button)findViewById(R.id.button1);
        button_sbm1.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        //Intent intent = new Intent(this,SecondActivity.class);
                        Intent intent = new Intent("com.example.android.bluetoothlegatt.PreConfigured");
                        // startActivity(intent);
                        startActivity(intent);
                    }
                }
        );

    }*/


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

      if(requestCode==1) {
          if (resultCode == RESULT_OK) {
              tvResult.append(data.getStringExtra("final_output"));
              //neww
              writeDataToBle(data.getStringExtra("final_output"));

              //old
              System.out.println("Final_output IN NEW IS" + data.getStringExtra("final_output"));
              //  final String  final_output = (data.getStringExtra("final_output"));
          /*if(bluetoothGattCharacteristicHM_10 != null){
                // bluetoothGattCharacteristicHM_10.setValue(x1);
                bluetoothGattCharacteristicHM_10.setValue(data.getStringExtra("final_output"));
                mBluetoothLeService.writeCharacteristic(bluetoothGattCharacteristicHM_10);
                mBluetoothLeService.setCharacteristicNotification(bluetoothGattCharacteristicHM_10,true);
            }*/
          }
      }

        if(requestCode==2) {
            if (resultCode == RESULT_OK) {
                tvResult.append(data.getStringExtra("s1"));
                //neww
                writeDataToBle(data.getStringExtra("s1"));

                //old
                System.out.println("S1 in new is" + data.getStringExtra("s1"));
                //  final String  final_output = (data.getStringExtra("final_output"));
          /*if(bluetoothGattCharacteristicHM_10 != null){
                // bluetoothGattCharacteristicHM_10.setValue(x1);
                bluetoothGattCharacteristicHM_10.setValue(data.getStringExtra("final_output"));
                mBluetoothLeService.writeCharacteristic(bluetoothGattCharacteristicHM_10);
                mBluetoothLeService.setCharacteristicNotification(bluetoothGattCharacteristicHM_10,true);
            }*/
            }
        }




    }



    int count = 0;
    String stringData = "";


    //String s7 = data.getStringExtra(data.getStringExtra("final_output"));


    public void writeDataToBle( final String final_output1) {


        if( final_output1.length() < 106) {
            Toast.makeText(this,"Not Valid Data", Toast.LENGTH_SHORT).show();
            return;
        }
        new CountDownTimer(30000, 1000) {

            public void onTick(long millisUntilFinished) {
                if(count == 0) {
                    mBluetoothLeService.writeCharacteristic( mNotifyCharacteristic,
                            final_output1.subSequence(0, 19).toString().trim());
                    stringData = final_output1.subSequence(0, 19).toString().trim();
                    Log.e("BLE Write Data", stringData);
                }
                else if(count == 1) {
                    mBluetoothLeService.writeCharacteristic(
                            mNotifyCharacteristic,
                            final_output1.subSequence(19, 39).toString().trim()
                    );
                    stringData += final_output1.subSequence(19, 39).toString().trim();
                    Log.e("Sent Data", stringData);
                }
                else if(count == 2) {
                    mBluetoothLeService.writeCharacteristic(
                            mNotifyCharacteristic,
                            final_output1.subSequence(39, 59).toString().trim()
                    );
                    stringData += final_output1.subSequence(39, 59).toString().trim();
                    Log.e("Sent Data", stringData);
                }
                else if(count == 3) {
                    mBluetoothLeService.writeCharacteristic(
                            mNotifyCharacteristic,
                            final_output1.subSequence(59, 79).toString().trim()
                    );
                    stringData += final_output1.subSequence(59, 79).toString().trim();
                    Log.e("Sent Data", stringData);
                }
                else if(count == 4) {
                    mBluetoothLeService.writeCharacteristic(
                            mNotifyCharacteristic,
                            final_output1.subSequence(79, 99).toString().trim()
                    );
                    stringData += final_output1.subSequence(79, 99).toString();
                    Log.e("Sent Data", stringData);
                }
                else if(count == 5) {
                    mBluetoothLeService.writeCharacteristic(
                            mNotifyCharacteristic,
                            final_output1.subSequence(99, 106).toString().trim()
                    );
                    stringData += final_output1.subSequence(99, 106).toString().trim();
                    Log.e("Sent Data", stringData);
                }
                count =count+ 1;
                //here you can have your logic to set text to edittext
            }

            public void onFinish(){}

        }.start();
    }


    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(mGattUpdateReceiver, makeGattUpdateIntentFilter());
        if (mBluetoothLeService != null) {
            final boolean result = mBluetoothLeService.connect(mDeviceAddress);
            Log.d(TAG, "Connect request result=" + result);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mGattUpdateReceiver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mServiceConnection);
        mBluetoothLeService = null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.gatt_services, menu);
        if (mConnected) {
            menu.findItem(R.id.menu_connect).setVisible(false);
            menu.findItem(R.id.menu_disconnect).setVisible(true);
        } else {
            menu.findItem(R.id.menu_connect).setVisible(true);
            menu.findItem(R.id.menu_disconnect).setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.menu_connect:
                mBluetoothLeService.connect(mDeviceAddress);
                return true;
            case R.id.menu_disconnect:
                mBluetoothLeService.disconnect();
                return true;
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void updateConnectionState(final int resourceId) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mConnectionState.setText(resourceId);
            }
        });
    }


    private void displayData(String data) {
        if (data != null)
        {
            Log.e("BLE DATA", data);
            mDataField.append(data);


        }
    }

    // Demonstrates how to iterate through the supported GATT Services/Characteristics.
    // In this sample, we populate the data structure that is bound to the ExpandableListView
    // on the UI.
    private void displayGattServices(List<BluetoothGattService> gattServices) {

       // UUID UUID_HM_10 =
         //      UUID.fromString(SampleGattAttributes.HM_10);
        if (gattServices == null) return;
      String uuid = null;
        String unknownServiceString = getResources().getString(R.string.unknown_service);
        String unknownCharaString = getResources().getString(R.string.unknown_characteristic);
        ArrayList<HashMap<String, String>> gattServiceData = new ArrayList<HashMap<String, String>>();
        ArrayList<ArrayList<HashMap<String, String>>> gattCharacteristicData
                = new ArrayList<ArrayList<HashMap<String, String>>>();
        mGattCharacteristics = new ArrayList<ArrayList<BluetoothGattCharacteristic>>();

        // Loops through available GATT Services.
        for (BluetoothGattService gattService : gattServices) {
            HashMap<String, String> currentServiceData = new HashMap<String, String>();
            uuid = gattService.getUuid().toString();
            currentServiceData.put(
                    LIST_NAME, SampleGattAttributes.lookup(uuid, unknownServiceString));
            currentServiceData.put(LIST_UUID, uuid);
            gattServiceData.add(currentServiceData);

            ArrayList<HashMap<String, String>> gattCharacteristicGroupData =
                    new ArrayList<HashMap<String, String>>();
            List<BluetoothGattCharacteristic> gattCharacteristics =
                    gattService.getCharacteristics();
            ArrayList<BluetoothGattCharacteristic> charas =
                    new ArrayList<BluetoothGattCharacteristic>();

            // Loops through available Characteristics.
            for (BluetoothGattCharacteristic gattCharacteristic : gattCharacteristics) {
                charas.add(gattCharacteristic);
                HashMap<String, String> currentCharaData = new HashMap<String, String>();
                uuid = gattCharacteristic.getUuid().toString();
                currentCharaData.put(
                        LIST_NAME, SampleGattAttributes.lookup(uuid, unknownCharaString));
                currentCharaData.put(LIST_UUID, uuid);
                gattCharacteristicGroupData.add(currentCharaData);


                //uuid = gattCharacteristics.getUuid().toString();
                if(uuid.equalsIgnoreCase(SampleGattAttributes.HM_10)) {
                    final int charaProp = gattCharacteristic.getProperties();
                  // if ((charaProp | BluetoothGattCharacteristic.PROPERTY_READ) > 0) {
                        // If there is an active notification on a characteristic, clear
                        // it first so it doesn't update the data field on the user interface.
                       /* if (mNotifyCharacteristic != null) {
                            mBluetoothLeService.setCharacteristicNotification(
                                    mNotifyCharacteristic, false);
                            mNotifyCharacteristic = null;
                        }*/
                        //mBluetoothLeService.readCharacteristic(gattCharacteristic);
                    //}
                    if ((charaProp | BluetoothGattCharacteristic.PROPERTY_NOTIFY) > 0) {
                        mNotifyCharacteristic = gattCharacteristic;
                        mBluetoothLeService.setCharacteristicNotification(
                                gattCharacteristic, true);
                        mBluetoothLeService.readCharacteristic(gattCharacteristic);

                    }
                }

             if(uuid.equals(SampleGattAttributes.ble)){
                   bluetoothGattCharacteristicHM_10 = gattService.getCharacteristic(mBluetoothLeService.UUID_HEART_RATE_MEASUREMENT1);

               }
              //Check if it is "HM_10"
              //  if(uuid.equals(SampleGattAttributes.HM_10)){
                //    bluetoothGattCharacteristicHM_10 = gattService.getCharacteristic(UUID_HM_10);

                //}
            }
            mGattCharacteristics.add(charas);
            gattCharacteristicData.add(gattCharacteristicGroupData);
        }

        SimpleExpandableListAdapter gattServiceAdapter = new SimpleExpandableListAdapter(
                this,
                gattServiceData,
               android.R.layout.simple_expandable_list_item_2,
                new String[] {LIST_NAME, LIST_UUID},
                new int[] { android.R.id.text1, android.R.id.text2 },
                gattCharacteristicData,
                android.R.layout.simple_expandable_list_item_2,
                new String[] {LIST_NAME, LIST_UUID},
                new int[] { android.R.id.text1, android.R.id.text2 }
        );
       // mGattServicesList.setAdapter(gattServiceAdapter);
    }

    private static IntentFilter makeGattUpdateIntentFilter() {
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_CONNECTED);
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_DISCONNECTED);
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED);
        intentFilter.addAction(BluetoothLeService.ACTION_DATA_AVAILABLE);
        return intentFilter;
    }
}
