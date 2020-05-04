package com.example.android.bluetoothlegatt;

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
import android.os.Bundle;
import android.app.Activity;
import android.os.IBinder;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleExpandableListAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


public class SecondActivity extends Activity {
    private final static String TAG = DeviceControlActivity.class.getSimpleName();

    public static final String EXTRAS_DEVICE_NAME = "DEVICE_NAME";
    public static final String EXTRAS_DEVICE_ADDRESS = "DEVICE_ADDRESS";
    String i,j,k,f,g,h,a,b;
    private Spinner mySpinner,mySpinner1, mySpinner2,mySpinner3,mySpinner4,mySpinner5;
    private SpinAdapter adapter,adapter1,adapter2,adapter3,adapter4,adapter5;
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
    private EditText et1,et2,et3,et4,et5,et6,et7,et8;
String content;
String content1,content2,content3,content4,content5;
String low_threshold_channel1;
String high_threshold_channel1;
String no_alarm_channel1;

String display_interval,tx_interval;


 String final_output;
    String low_threshold_channel2;
    String high_threshold_channel2;
    String no_alarm_channel2;

    String low_threshold_channel3_temperature;
    String high_threshold_channel3_temperature;

    String low_threshold_channel3_humidity;
    String high_threshold_channel3_humidity;
    String no_alarm_channel3;


    String write= "*11#*17&00300#*18&03500#*21#*27&00300#*28&03500#*30#*42#*51&011819#*61&101010#*71&0015#*81&0030#*90#*00#$Y";

    private BluetoothGattCharacteristic bluetoothGattCharacteristicHM_10;
    private Button btnSubmit;
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

            //    String write= "*11#*17&00300#*18&03500#*21#*27&00300#*28&03500#*30#*42#*51&011819#*61&101010#*71&0015#*81&0030#*90#*00#$Y";
            //  a = String.valueOf(i)+"and" +String.valueOf(j);
            final String action = intent.getAction();

            if (BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED.equals(action)) {
                //   Show all the supported services and characteristics on the user interface.
                displayGattServices(mBluetoothLeService.getSupportedGattServices());
            }

        }
    };

    public static String rightpadzero(String str, int num){
        return String.format("%1$-"+num+"s",str).replace(' ','0');
    }
    public static String leftpadzero(String str, int num){

        return String.format("%1$"+num+"s",str).replace(' ','0');
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
       // getCurrentDate();
        //getCurrentTime();
        Calendar c= Calendar.getInstance();

        SimpleDateFormat df= new SimpleDateFormat("MMddyy");
        SimpleDateFormat df1= new SimpleDateFormat("HHmmss");
       final String formattedDate= "*51&"+df.format(c.getTime())+"#";
       final String formattedDate1= "*61&"+df1.format(c.getTime())+"#";
        System.out.println("Time is " + formattedDate1);


        System.out.println("DATE is" + formattedDate);

        //code for alarm spinner1
        final EditText et1 = (EditText) findViewById(R.id.edit1);
        final EditText et2 = (EditText) findViewById(R.id.edit2);



        final RadioGroup radioGroup = (RadioGroup) findViewById(R.id.grp);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.a){
                    et1.setVisibility(View.VISIBLE);
                    et2.setVisibility(View.VISIBLE);


                } else {
                    et1.setVisibility(View.INVISIBLE);
                    et2.setVisibility(View.INVISIBLE);

                }


            }
        });

        //code for alarm spinner2
        final EditText et3 = (EditText) findViewById(R.id.edit3);
        final EditText et4 = (EditText) findViewById(R.id.edit4);


        RadioGroup radioGroup1 = (RadioGroup) findViewById(R.id.grp1);
        radioGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.a1){
                    et3.setVisibility(View.VISIBLE);
                    et4.setVisibility(View.VISIBLE);

                } else {
                    et3.setVisibility(View.INVISIBLE);
                    et4.setVisibility(View.INVISIBLE);

                }
            }
        });



      //  System.out.println("ET1 is:"+et1);
        final Intent intent = getIntent();
        btnSubmit = (Button) findViewById(R.id.btnSubmit);

        User[] users = new User[7];
        User [] users1 =new User[7];
        User [] users2 = new User[4];
        User [] users3 = new User[4];
        User [] users4 = new User[4];
        User [] users5 = new User[5];

        users[0] = new User();
        users[0].setId("*10#");
        users[0].setName("No sensor");

        users[1] = new User();
        users[1].setId("*11#");
        users[1].setName("10k sensor");

        users[2] = new User();
        users[2].setId("*12#");
        users[2].setName("100k sensor");

        users[3] = new User();
        users[3].setId("*13#");
        users[3].setName("PT1000 sensor");

        users[4] = new User();
        users[4].setId("*14#");
        users[4].setName("4-20mA sensor");

        users[5] = new User();
        users[5].setId("*15#");
        users[5].setName("0-5V sensor");

        users[6] = new User();
        users[6].setId("*16#");
        users[6].setName("0-10V sensor");

        users1[0] = new User();
        users1[0].setId("*20#");
        users1[0].setName("No sensor");


        users1[1] = new User();
        users1[1].setId("*21#");
        users1[1].setName("10k sensor");

        users1[2] = new User();
        users1[2].setId("*22#");
        users1[2].setName("100k sensor");


        users1[3] = new User();
        users1[3].setId("*23#");
        users1[3].setName("PT1000 sensor");

        users1[4] = new User();
        users1[4].setId("*24#");
        users1[4].setName("4-20mA sensor");

        users1[5] = new User();
        users1[5].setId("*25#");
        users1[5].setName("0-5V sensor");

        users1[6] = new User();
        users1[6].setId("*26#");
        users1[6].setName("0-10V sensor");

        users2[0] = new User();
        users2[0].setId("*30#");
        users2[0].setName("No sensor");

        users2[1] = new User();
        users2[1].setId("*31#");
        users2[1].setName("Temperature");

        users2[2] = new User();
        users2[2].setId("*32#");
        users2[2].setName("Humidity");

        users2[3] = new User();
        users2[3].setId("*33#");
        users2[3].setName("Temp/Humidity");

        users3[0] = new User();
        users3[0].setId("*90#");
        users3[0].setName("900 MHz");

        users3[1] = new User();
        users3[1].setId("*91#");
        users3[1].setName("Wi-Fi");

        users3[2] = new User();
        users3[2].setId("*92#");
        users3[2].setName("BLE");

        users3[3] = new User();
        users3[3].setId("*93#");
        users3[3].setName("Bluvision");

        users4[0] = new User();
        users4[0].setId("0015");
        users4[0].setName("15 secs");

        users4[1] = new User();
        users4[1].setId("0030");
        users4[1].setName("30 secs");

        users4[2] = new User();
        users4[2].setId("0045");
        users4[2].setName("45 secs");

        users4[3] = new User();
        users4[3].setId("0060");
        users4[3].setName("60 secs");

        users5[0] = new User();
        users5[0].setId("0060");
        users5[0].setName("1 min");

        users5[1] = new User();
        users5[1].setId("0300");
        users5[1].setName("5 mins");

        users5[2] = new User();
        users5[2].setId("0600");
        users5[2].setName("10 mins");

        users5[3] = new User();
        users5[3].setId("0900");
        users5[3].setName("15 mins");

        users5[4] = new User();
        users5[4].setId("1200");
        users5[4].setName("20 mins");



        final EditText et5 = (EditText) findViewById(R.id.edit5);
        final EditText et6 = (EditText) findViewById(R.id.edit6);
        final EditText et7 = (EditText) findViewById(R.id.edit7);
        final EditText et8 = (EditText) findViewById(R.id.edit8);






        // Initialize the adapter sending the current context
        // Send the simple_spinner_item layout
        // And finally send the Users array (Your data)
        //for first spinner
        adapter = new SpinAdapter(SecondActivity.this, android.R.layout.simple_spinner_item, users);
        mySpinner = (Spinner) findViewById(R.id.miSpinner);
        mySpinner.setAdapter(adapter);

        //for second spinner
        adapter1 = new SpinAdapter(SecondActivity.this, android.R.layout.simple_spinner_item, users1);
        mySpinner1 = (Spinner) findViewById(R.id.miSpinner1);
        mySpinner1.setAdapter(adapter1); // Set the custom adapter to the spinner
        // You can create an anonymous listener to handle the event when is selected an spinner item

        //for third spinner
        adapter2= new SpinAdapter(SecondActivity.this, android.R.layout.simple_spinner_item, users2);
        mySpinner2 = (Spinner) findViewById(R.id.miSpinner2);
        mySpinner2.setAdapter(adapter2);

        adapter3= new SpinAdapter(SecondActivity.this, android.R.layout.simple_spinner_item, users3);
        mySpinner3 = (Spinner) findViewById(R.id.miSpinner3);
        mySpinner3.setAdapter(adapter3);


        adapter4 = new SpinAdapter(SecondActivity.this, android.R.layout.simple_spinner_item, users4);
        mySpinner4 = (Spinner) findViewById(R.id.miSpinner4);
        mySpinner4.setAdapter(adapter4);


        adapter5 = new SpinAdapter(SecondActivity.this, android.R.layout.simple_spinner_item, users5);
        mySpinner5 = (Spinner) findViewById(R.id.miSpinner5);
        mySpinner5.setAdapter(adapter5);


        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                // Here you get the current item (a User object) that is selected by its position
                User user = adapter.getItem(position);

                // Here you can do the action you want to...
                i = user.getId();
                //  Toast.makeText(MainActivity.this, "ID: " + user.getId() + "\nName: " + user.getName(),
                //        Toast.LENGTH_SHORT).show();
            }



            @Override
            public void onNothingSelected(AdapterView<?> adapter) {  }
        });


        mySpinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                // Here you get the current item (a User object) that is selected by its position
                User user1 = adapter1.getItem(position);
                j = user1.getId();
                // Here you can do the action you want to...
                // System.out.println("ID is:"+user1.getId());
                //Toast.makeText(MainActivity.this, "ID: " + user1.getId() + "\nName: " + user1.getName(),
                //      Toast.LENGTH_SHORT).show();
            }



            @Override
            public void onNothingSelected(AdapterView<?> adapter) {  }
        });

        mySpinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                // Here you get the current item (a User object) that is selected by its position
                User user2 = adapter2.getItem(position);
                // Here you can do the action you want to...
                k = user2.getId();

                //  Toast.makeText(MainActivity.this, "ID: " + user.getId() + "\nName: " + user.getName(),
                //        Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapter) {  }

        });

        mySpinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                // Here you get the current item (a User object) that is selected by its position
                User user3 = adapter3.getItem(position);
                // Here you can do the action you want to...
                f = user3.getId();
                //  Toast.makeText(MainActivity.this, "ID: " + user.getId() + "\nName: " + user.getName(),
                //        Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapter) {  }

        });


        mySpinner4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                // Here you get the current item (a User object) that is selected by its position
                User user4 = adapter4.getItem(position);
                // Here you can do the action you want to...
                g = user4.getId();
                //  Toast.makeText(MainActivity.this, "ID: " + user.getId() + "\nName: " + user.getName(),
                //        Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapter) {  }

        });

        mySpinner5.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                // Here you get the current item (a User object) that is selected by its position
                User user5 = adapter5.getItem(position);
                // Here you can do the action you want to...
                h = user5.getId();
                //  Toast.makeText(MainActivity.this, "ID: " + user.getId() + "\nName: " + user.getName(),
                //        Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapter) {  }

        });

        final RadioGroup radioGroup2 = (RadioGroup) findViewById(R.id.grp2);
        radioGroup2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.a2 && k=="*31#"){
                    et5.setVisibility(View.VISIBLE);
                    et6.setVisibility(View.VISIBLE);
                    et7.setVisibility(View.INVISIBLE);
                    et8.setVisibility(View.INVISIBLE);
                }

                if(checkedId == R.id.a2 && k=="*32#"){
                    et5.setVisibility(View.INVISIBLE);
                    et6.setVisibility(View.INVISIBLE);
                    et7.setVisibility(View.VISIBLE);
                    et8.setVisibility(View.VISIBLE);
                }

                if(checkedId == R.id.a2 && k=="*33#"){
                    et5.setVisibility(View.VISIBLE);
                    et6.setVisibility(View.VISIBLE);
                    et7.setVisibility(View.VISIBLE);
                    et8.setVisibility(View.VISIBLE);
                }

                if(checkedId == R.id.b2){
                    et5.setVisibility(View.INVISIBLE);
                    et6.setVisibility(View.INVISIBLE);
                    et7.setVisibility(View.INVISIBLE);
                    et8.setVisibility(View.INVISIBLE);
                }

                if((checkedId == R.id.a2 || checkedId ==R.id.b2)&& k=="*30#"){
                   content2="";
                }

            }
        });


        RadioGroup radioGroup3 = (RadioGroup) findViewById(R.id.grp3);
        radioGroup3.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.a3){
                    content3= "*41#";

                }
                if(checkedId == R.id.b3){
                    content3= "*42#";

                }
            }
        });

        RadioGroup radioGroup4 = (RadioGroup) findViewById(R.id.grp4);
        radioGroup4.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.a4){
                    content4= "*00#";

                }
                if(checkedId == R.id.b4){
                    content4= "*01#";

                }
            }
        });




        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                RadioButton simpleRadioButton = (RadioButton) findViewById(R.id.a); // initiate a radio button
                Boolean RadioButtonState = simpleRadioButton.isChecked();

                RadioButton simpleRadioButton1 = (RadioButton) findViewById(R.id.b); // initiate a radio button
                Boolean RadioButtonState1 = simpleRadioButton1.isChecked();

                RadioButton simpleRadioButton2 = (RadioButton) findViewById(R.id.a1); // initiate a radio button
                Boolean RadioButtonState2 = simpleRadioButton2.isChecked();

                RadioButton simpleRadioButton3 = (RadioButton) findViewById(R.id.b1); // initiate a radio button
                Boolean RadioButtonState3 = simpleRadioButton3.isChecked();


                RadioButton simpleRadioButton4 = (RadioButton) findViewById(R.id.a2); // initiate a radio button
                Boolean RadioButtonState4 = simpleRadioButton4.isChecked();

                RadioButton simpleRadioButton5 = (RadioButton) findViewById(R.id.b2); // initiate a radio button
                Boolean RadioButtonState5 = simpleRadioButton5.isChecked();

                if(RadioButtonState==true)
                {

                    low_threshold_channel1=et1.getText().toString();
                    int p,lowcount=0;
                    for(p=0;p<low_threshold_channel1.length();p++){
                        if(low_threshold_channel1.charAt(p)!='.'){
                            lowcount=lowcount+1;
                        }
                        else{
                            break;
                        }
                    }
                    if(lowcount<3){
                        int spec=low_threshold_channel1.length()-lowcount;
                        spec=3+spec;
                        low_threshold_channel1= leftpadzero(low_threshold_channel1,spec);


                    }
                    low_threshold_channel1=low_threshold_channel1.replace('.','0');
                    if(low_threshold_channel1.length()<5){
                        low_threshold_channel1=rightpadzero(low_threshold_channel1,5);
                    }

                    StringBuilder sb= new StringBuilder(low_threshold_channel1);
                    if(sb.charAt(1)=='-' && sb.charAt(0)=='0')
                    {
                        sb.setCharAt(0,'-');
                        sb.setCharAt(1,'0');
                    }

                    System.out.println("low_threshold_sb"+sb);

                    high_threshold_channel1=et2.getText().toString();
                    int q,highcount=0;
                    for(q=0;q<high_threshold_channel1.length();q++){
                        if(high_threshold_channel1.charAt(q)!='.'){
                            highcount=highcount+1;
                        }
                        else{
                            break;
                        }
                    }
                    if(highcount<3){
                        int spec1=high_threshold_channel1.length()-highcount;
                        spec1=3+spec1;
                        high_threshold_channel1=leftpadzero(high_threshold_channel1,spec1);
                    }
                    high_threshold_channel1=high_threshold_channel1.replace('.','0');
                    if(high_threshold_channel1.length()<5){
                        high_threshold_channel1=rightpadzero(high_threshold_channel1,5);
                    }

                    StringBuilder sb1= new StringBuilder(high_threshold_channel1);
                    if(sb1.charAt(1)=='-' && sb1.charAt(0)=='0')
                    {
                        sb1.setCharAt(0,'-');
                        sb1.setCharAt(1,'0');
                    }

                    System.out.println("high_threshold_sb1"+sb1);
                    content="*17&"+sb+"#"+"*18&"+sb1+"#";
                }

                if(RadioButtonState1==true)
                {
                   no_alarm_channel1="*19#";
                    content=no_alarm_channel1;
                }

                if(RadioButtonState2==true)
                {
                    low_threshold_channel2=et3.getText().toString();

                    //new
                    int p,lowcount=0;
                    for(p=0;p<low_threshold_channel2.length();p++){
                        if(low_threshold_channel2.charAt(p)!='.'){
                            lowcount=lowcount+1;
                        }
                        else{
                            break;
                        }
                    }
                    if(lowcount<3){
                        int spec=low_threshold_channel2.length()-lowcount;
                        spec=3+spec;
                        low_threshold_channel2= leftpadzero(low_threshold_channel2,spec);


                    }
                    low_threshold_channel2=low_threshold_channel2.replace('.','0');
                    if(low_threshold_channel2.length()<5){
                        low_threshold_channel2=rightpadzero(low_threshold_channel2,5);
                    }

                    StringBuilder sb= new StringBuilder(low_threshold_channel2);
                    if(sb.charAt(1)=='-' && sb.charAt(0)=='0')
                    {
                        sb.setCharAt(0,'-');
                        sb.setCharAt(1,'0');
                    }

                    System.out.println("low_threshold_sb_channel2"+sb);

                    high_threshold_channel2=et4.getText().toString();
                    int q,highcount=0;
                    for(q=0;q<high_threshold_channel2.length();q++){
                        if(high_threshold_channel2.charAt(q)!='.'){
                            highcount=highcount+1;
                        }
                        else{
                            break;
                        }
                    }
                    if(highcount<3){
                        int spec1=high_threshold_channel2.length()-highcount;
                        spec1=3+spec1;
                        high_threshold_channel2=leftpadzero(high_threshold_channel2,spec1);
                    }
                    high_threshold_channel2=high_threshold_channel2.replace('.','0');
                    if(high_threshold_channel2.length()<5){
                        high_threshold_channel2=rightpadzero(high_threshold_channel2,5);
                    }

                    StringBuilder sb1= new StringBuilder(high_threshold_channel2);
                    if(sb1.charAt(1)=='-' && sb1.charAt(0)=='0')
                    {
                        sb1.setCharAt(0,'-');
                        sb1.setCharAt(1,'0');
                    }

                    System.out.println("high_threshold_sb1_channel2"+sb1);
                    content1="*27&"+sb+"#"+"*28&"+sb1+"#";
                }
                if(RadioButtonState3==true)
                {
                    no_alarm_channel2="*29#";
                    content1=no_alarm_channel2;
                }
                if(RadioButtonState4==true && k=="*31#") {
                    low_threshold_channel3_temperature = et5.getText().toString();

                    //new
                    int p, lowcount = 0;
                    for (p = 0; p < low_threshold_channel3_temperature.length(); p++) {
                        if (low_threshold_channel3_temperature.charAt(p) != '.') {
                            lowcount = lowcount + 1;
                        } else {
                            break;
                        }
                    }
                    if (lowcount < 3) {
                        int spec = low_threshold_channel3_temperature.length() - lowcount;
                        spec = 3 + spec;
                        low_threshold_channel3_temperature = leftpadzero(low_threshold_channel3_temperature, spec);


                    }
                    low_threshold_channel3_temperature = low_threshold_channel3_temperature.replace('.', '0');
                    if (low_threshold_channel3_temperature.length() < 5) {
                        low_threshold_channel3_temperature = rightpadzero(low_threshold_channel3_temperature, 5);
                    }

                    StringBuilder sb = new StringBuilder(low_threshold_channel3_temperature);
                    if (sb.charAt(1) == '-' && sb.charAt(0) == '0') {
                        sb.setCharAt(0, '-');
                        sb.setCharAt(1, '0');
                    }

                    System.out.println("low_threshold_sb_channel3_temperature" + sb);

                    high_threshold_channel3_temperature = et6.getText().toString();
                    int q, highcount = 0;
                    for (q = 0; q < high_threshold_channel3_temperature.length(); q++) {
                        if (high_threshold_channel3_temperature.charAt(q) != '.') {
                            highcount = highcount + 1;
                        } else {
                            break;
                        }
                    }
                    if (highcount < 3) {
                        int spec1 = high_threshold_channel3_temperature.length() - highcount;
                        spec1 = 3 + spec1;
                        high_threshold_channel3_temperature = leftpadzero(high_threshold_channel3_temperature, spec1);
                    }
                    high_threshold_channel3_temperature = high_threshold_channel3_temperature.replace('.', '0');
                    if (high_threshold_channel3_temperature.length() < 5) {
                        high_threshold_channel3_temperature = rightpadzero(high_threshold_channel3_temperature, 5);
                    }

                    StringBuilder sb1 = new StringBuilder(high_threshold_channel3_temperature);
                    if (sb1.charAt(1) == '-' && sb1.charAt(0) == '0') {
                        sb1.setCharAt(0, '-');
                        sb1.setCharAt(1, '0');
                    }

                    System.out.println("high_threshold_sb1_channel3_temperature" + sb1);
                    content2 = "*34&" + sb + "#" + "*35&" + sb1 + "#";
                }

                if(RadioButtonState4==true && k=="*32#")
                {

                    //new
                    low_threshold_channel3_humidity = et7.getText().toString();

                    //new
                    int p, lowcount = 0;
                    for (p = 0; p < low_threshold_channel3_humidity.length(); p++) {
                        if (low_threshold_channel3_humidity.charAt(p) != '.') {
                            lowcount = lowcount + 1;
                        } else {
                            break;
                        }
                    }
                    if (lowcount < 3) {
                        int spec = low_threshold_channel3_humidity.length() - lowcount;
                        spec = 3 + spec;
                        low_threshold_channel3_humidity = leftpadzero(low_threshold_channel3_humidity, spec);


                    }
                    low_threshold_channel3_humidity = low_threshold_channel3_humidity.replace('.', '0');
                    if (low_threshold_channel3_humidity.length() < 5) {
                        low_threshold_channel3_humidity = rightpadzero(low_threshold_channel3_humidity, 5);
                    }



                    high_threshold_channel3_humidity = et8.getText().toString();
                    int q, highcount = 0;
                    for (q = 0; q < high_threshold_channel3_humidity.length(); q++) {
                        if (high_threshold_channel3_humidity.charAt(q) != '.') {
                            highcount = highcount + 1;
                        } else {
                            break;
                        }
                    }
                    if (highcount < 3) {
                        int spec1 = high_threshold_channel3_humidity.length() - highcount;
                        spec1 = 3 + spec1;
                        high_threshold_channel3_humidity = leftpadzero(high_threshold_channel3_humidity, spec1);
                    }
                    high_threshold_channel3_humidity = high_threshold_channel3_humidity.replace('.', '0');
                    if (high_threshold_channel3_humidity.length() < 5) {
                        high_threshold_channel3_humidity = rightpadzero(high_threshold_channel3_humidity, 5);
                    }


                    content2="*36&"+low_threshold_channel3_humidity+"#"+"*37&"+ high_threshold_channel3_humidity+"#";
                    System.out.println("Low_humid"+low_threshold_channel3_humidity);
                    System.out.println("High_humid"+high_threshold_channel3_humidity);
                }
                if(RadioButtonState4==true&& k=="*33#")
                {

                    low_threshold_channel3_temperature = et5.getText().toString();

                    //new
                    int p, lowcount = 0;
                    for (p = 0; p < low_threshold_channel3_temperature.length(); p++) {
                        if (low_threshold_channel3_temperature.charAt(p) != '.') {
                            lowcount = lowcount + 1;
                        } else {
                            break;
                        }
                    }
                    if (lowcount < 3) {
                        int spec = low_threshold_channel3_temperature.length() - lowcount;
                        spec = 3 + spec;
                        low_threshold_channel3_temperature = leftpadzero(low_threshold_channel3_temperature, spec);


                    }
                    low_threshold_channel3_temperature = low_threshold_channel3_temperature.replace('.', '0');
                    if (low_threshold_channel3_temperature.length() < 5) {
                        low_threshold_channel3_temperature = rightpadzero(low_threshold_channel3_temperature, 5);
                    }

                    StringBuilder sb = new StringBuilder(low_threshold_channel3_temperature);
                    if (sb.charAt(1) == '-' && sb.charAt(0) == '0') {
                        sb.setCharAt(0, '-');
                        sb.setCharAt(1, '0');
                    }

                    System.out.println("low_threshold_sb_channel3_temperature" + sb);

                    high_threshold_channel3_temperature = et6.getText().toString();
                    int q, highcount = 0;
                    for (q = 0; q < high_threshold_channel3_temperature.length(); q++) {
                        if (high_threshold_channel3_temperature.charAt(q) != '.') {
                            highcount = highcount + 1;
                        } else {
                            break;
                        }
                    }
                    if (highcount < 3) {
                        int spec1 = high_threshold_channel3_temperature.length() - highcount;
                        spec1 = 3 + spec1;
                        high_threshold_channel3_temperature = leftpadzero(high_threshold_channel3_temperature, spec1);
                    }
                    high_threshold_channel3_temperature = high_threshold_channel3_temperature.replace('.', '0');
                    if (high_threshold_channel3_temperature.length() < 5) {
                        high_threshold_channel3_temperature = rightpadzero(high_threshold_channel3_temperature, 5);
                    }

                    StringBuilder sb1 = new StringBuilder(high_threshold_channel3_temperature);
                    if (sb1.charAt(1) == '-' && sb1.charAt(0) == '0') {
                        sb1.setCharAt(0, '-');
                        sb1.setCharAt(1, '0');
                    }

                    System.out.println("high_threshold_sb1_channel3_temperature" + sb1);
                    low_threshold_channel3_humidity = et7.getText().toString();

                    //new
                    int p1, lowcount1 = 0;
                    for (p1 = 0; p1 < low_threshold_channel3_humidity.length(); p1++) {
                        if (low_threshold_channel3_humidity.charAt(p1) != '.') {
                            lowcount1 = lowcount1 + 1;
                        } else {
                            break;
                        }
                    }
                    if (lowcount1 < 3) {
                        int spec = low_threshold_channel3_humidity.length() - lowcount1;
                        spec = 3 + spec;
                        low_threshold_channel3_humidity = leftpadzero(low_threshold_channel3_humidity, spec);


                    }
                    low_threshold_channel3_humidity = low_threshold_channel3_humidity.replace('.', '0');
                    if (low_threshold_channel3_humidity.length() < 5) {
                        low_threshold_channel3_humidity = rightpadzero(low_threshold_channel3_humidity, 5);
                    }



                    high_threshold_channel3_humidity = et8.getText().toString();
                    int q1, highcount1 = 0;
                    for (q1 = 0; q1 < high_threshold_channel3_humidity.length(); q1++) {
                        if (high_threshold_channel3_humidity.charAt(q1) != '.') {
                            highcount1 = highcount1 + 1;
                        } else {
                            break;
                        }
                    }
                    if (highcount1 < 3) {
                        int spec1 = high_threshold_channel3_humidity.length() - highcount1;
                        spec1 = 3 + spec1;
                        high_threshold_channel3_humidity = leftpadzero(high_threshold_channel3_humidity, spec1);
                    }
                    high_threshold_channel3_humidity = high_threshold_channel3_humidity.replace('.', '0');
                    if (high_threshold_channel3_humidity.length() < 5) {
                        high_threshold_channel3_humidity = rightpadzero(high_threshold_channel3_humidity, 5);
                    }
                    content2="*34&"+sb+"#"+"*35&"+ sb1+"#"+"*36&"+low_threshold_channel3_humidity+"#"+"*37&"+ high_threshold_channel3_humidity+"#";
                    System.out.println("Low_humid"+low_threshold_channel3_humidity);
                    System.out.println("High_humid"+high_threshold_channel3_humidity);
                    System.out.println("Low_temp"+sb);
                    System.out.println("High_temp"+sb1);
                }
                if(RadioButtonState5==true)
                {

                    no_alarm_channel3="*39#";
                    content2=no_alarm_channel3;
                }

                display_interval="*71&"+ g +"#";
                tx_interval="*81&"+ h +"#";


                System.out.println("No_alarm_channel1"+no_alarm_channel1);
                System.out.println("No_alarm_channel2"+no_alarm_channel2);
                System.out.println("Content is"+content);
                System.out.println("Content1 is"+ content1);
                System.out.println("Content2 is"+ content2);
                System.out.println("Content3 is"+ content3);
                System.out.println("Content4 is"+ content4);
                System.out.println("Transmitter type is"+ f);



                if(content2==null)
                {
                     b=i+content+j+content1+k+content3+formattedDate+formattedDate1+display_interval+tx_interval+f+content4+"$";
                    System.out.println("Content 2 if null"+b);

                }
                else
                {
                     b=i+content+j+content1+k+content2+content3+formattedDate+formattedDate1+display_interval+tx_interval+f+content4+"$";
                }

                int check_sum=0;
                int temp=256;
                while(check_sum<b.length())
                {
                    temp= temp + (b.charAt(check_sum)-48);
                    check_sum++;
                }
                int final_checksum=temp+48;
                char total=(char)final_checksum;
                System.out.println("checksum is"+total);

                final_output=b+total;

                System.out.println("Output is:"+final_output);

                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("final_output",final_output);
                    setResult(RESULT_OK,returnIntent);
                    finish();



               /* if(bluetoothGattCharacteristicHM_10 != null){
                    // bluetoothGattCharacteristicHM_10.setValue(x1);
                    bluetoothGattCharacteristicHM_10.setValue(final_output);
                    mBluetoothLeService.writeCharacteristic(bluetoothGattCharacteristicHM_10);
                    mBluetoothLeService.setCharacteristicNotification(bluetoothGattCharacteristicHM_10,true);
                }*/
                // Toast.makeText(MyAndroidAppActiv.this,
                //       "OnClickListener : " +
                //             "\nSpinner 1 : " + String.valueOf(spinner1.getSelectedItem()) +
                //           "\nSpinner 2 : " + String.valueOf(spinner2.getSelectedItem()),
                //Toast.LENGTH_SHORT).show();
            }

        });

        Intent gattServiceIntent = new Intent(this, BluetoothLeService.class);
        bindService(gattServiceIntent, mServiceConnection, BIND_AUTO_CREATE);
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

    private void updateConnectionState(final int resourceId) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mConnectionState.setText(resourceId);
            }
        });
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


                // uuid = gattCharacteristics.getUuid().toString();
                if (uuid.equalsIgnoreCase(SampleGattAttributes.HM_10)) {
                    final int charaProp = gattCharacteristic.getProperties();
                    //if ((charaProp | BluetoothGattCharacteristic.PROPERTY_READ) > 0) {
                    // If there is an active notification on a characteristic, clear
                    // it first so it doesn't update the data field on the user interface.
                       /* if (mNotifyCharacteristic != null) {
                            mBluetoothLeService.setCharacteristicNotification(
                                    mNotifyCharacteristic, false);
                            mNotifyCharacteristic = null;
                        }*/
                    // mBluetoothLeService.readCharacteristic(gattCharacteristic);
                    //}
                    if ((charaProp | BluetoothGattCharacteristic.PROPERTY_NOTIFY) > 0) {
                        mNotifyCharacteristic = gattCharacteristic;
                        mBluetoothLeService.setCharacteristicNotification(
                                gattCharacteristic, true);
                        mBluetoothLeService.readCharacteristic(gattCharacteristic);
                    }
                }

             //  if (uuid.equals(SampleGattAttributes.ble)) {
               //     bluetoothGattCharacteristicHM_10 = gattService.getCharacteristic(mBluetoothLeService.UUID_HEART_RATE_MEASUREMENT1);

                //}
                //Check if it is "HM_10"
                //  if(uuid.equals(SampleGattAttributes.HM_10)){
                //    bluetoothGattCharacteristicHM_10 = gattService.getCharacteristic(UUID_HM_10);

                //}
            }
            mGattCharacteristics.add(charas);
            gattCharacteristicData.add(gattCharacteristicGroupData);
        }


    }


    private static IntentFilter makeGattUpdateIntentFilter() {
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_CONNECTED);
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_DISCONNECTED);
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED);
        intentFilter.addAction(BluetoothLeService.ACTION_DATA_AVAILABLE);
        return intentFilter;
    }

    //old
    /*protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
    }*/

}
