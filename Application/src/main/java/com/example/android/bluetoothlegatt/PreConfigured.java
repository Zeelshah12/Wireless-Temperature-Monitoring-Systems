package com.example.android.bluetoothlegatt;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.android.bluetoothlegatt.BluetoothLeService.s1;
public class PreConfigured extends Activity {
    private Button btnSubmit1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_configured);

        System.out.println("s1 in preconfigured is:"+s1);
        if(s1.length()==108)
        {
            System.out.println("String here: "+s1);
            ArrayList<String> portfolioCount = new ArrayList<String>();
            // String mainText = new String();
            final Pattern p = Pattern.compile("\\*.+?#");
            final Matcher m = p.matcher(s1);

            while(m.find()){

                portfolioCount.add(m.group());
            }
            String [] arr =new String [portfolioCount.size()];
            arr=portfolioCount.toArray(arr);

            System.out.println("array elements in preconfigured are:");
            for (int i = 0; i < arr.length; i++) {
                System.out.println(i + "is" + arr[i]);
            }

           TextView channel_1 = (TextView) findViewById(R.id.channel_1);

            TextView channel_2 = (TextView) findViewById(R.id.channel_2);
            TextView channel_3 = (TextView) findViewById(R.id.channel_3);
            TextView trans = (TextView) findViewById(R.id.trans);
            TextView trans1 = (TextView) findViewById(R.id.trans1);
            TextView display = (TextView) findViewById(R.id.display);
            TextView tx= (TextView) findViewById(R.id.tx);
            TextView temp = (TextView) findViewById(R.id.temp);

            TextView low_thres1 = (TextView) findViewById(R.id.low_thres1);
            TextView high_thres1 = (TextView) findViewById(R.id.high_thres1);
            TextView low_thres2 = (TextView) findViewById(R.id.low_thres2);
            TextView high_thres2= (TextView) findViewById(R.id.high_thres2);

           // channel_1.setText("100k sensor");

         char a=arr[0].charAt(2);
         switch(a){
             case '0' :
                 channel_1.setText("No sensor");
                 break;
             case '1' :
                 channel_1.setText("10k sensor");
                 break;
             case '2' :
                 channel_1.setText("100k sensor");
                 break;
             case '3' :
                 channel_1.setText("PT1000 sensor");
                 break;
             case '4' :
                 channel_1.setText("4-20mA sensor");
                 break;
             case '5' :
                 channel_1.setText("0-5V sensor");
                 break;
             case '6' :
                 channel_1.setText("0-10V sensor");
                 break;
             default:
                 channel_1.setText("No sensor");
         }
         char b=arr[3].charAt(2);
            switch(b){
                case '0' :
                    channel_2.setText("No sensor");
                    break;
                case '1' :
                    channel_2.setText("10k sensor");
                    break;
                case '2' :
                    channel_2.setText("100k sensor");
                    break;
                case '3' :
                    channel_2.setText("PT1000 sensor");
                    break;
                case '4' :
                    channel_2.setText("4-20mA sensor");
                    break;
                case '5' :
                    channel_2.setText("0-5V sensor");
                    break;
                case '6' :
                    channel_2.setText("0-10V sensor");
                    break;
                default:
                    channel_2.setText("No sensor");
            }

            char c=arr[6].charAt(2);
            switch(c){
                case '0' :
                    channel_3.setText("No sensor");
                    break;
                case '1' :
                    channel_3.setText("Temperature");
                    break;
                case '2' :
                    channel_3.setText("Humidity");
                    break;
                case '3' :
                    channel_3.setText("Temperature/Humidity");
                    break;
                default:
                    channel_3.setText("No sensor");
            }
          char d=arr[12].charAt(2);
            switch(d){
                case '0' :
                    trans.setText("900 MHz");
                    break;
                case '1' :
                    trans.setText("Wifi");
                    break;
                case '2' :
                    trans.setText("BLE");
                    break;
                case '3' :
                    trans.setText("Bluvision");
                    break;
                default:
                    trans.setText("900 Mhz");
            }

            char e=arr[13].charAt(2);
            switch(e){
                case '0' :
                    trans1.setText("Not Required");
                    break;
                case '1' :
                    trans1.setText("Required");
                    break;
                default:
                    trans1.setText("Not Required");
            }

            char f=arr[7].charAt(2);
            switch(f){
                case '0' :
                    temp.setText("No Change");
                    break;
                case '1' :
                    temp.setText("Celsius");
                    break;
                case '2' :
                    temp.setText("Fahrenheit");
                    break;
                default:
                    temp.setText("Fahrenheit");
            }
            char g=arr[10].charAt(6);
            switch(g){
                case '1' :
                    display.setText("15 secs");
                    break;
                case '3' :
                    display.setText("30 secs");
                    break;
                case '4' :
                    display.setText("45 secs");
                    break;
                case '6' :
                    display.setText("60 secs");
                    break;

                default:
                    display.setText("15 secs");
            }
            char h=arr[11].charAt(5);
            switch(h){
                case '0' :
                    tx.setText("1 min");
                    break;
                case '2' :
                    tx.setText("20 mins");
                    break;
                case '3' :
                    tx.setText("5 mins");
                    break;
                case '6' :
                    tx.setText("10 mins");
                    break;
                case '9' :
                    tx.setText("15 mins");
                    break;
                default:
                    tx.setText("10 mins");
            }

            String str="";
            char a1=arr[1].charAt(4);
            if(a1  =='-'){
                str=str+"-";
            }

            else if(a1!='0')
            {
                str=str+a1;
            }

            char b1=arr[1].charAt(5);
            if(a1!='-'&&b1!='0'){
                str=str+b1;
            }
            else if(a1=='-'&&b1!='0'){
                str=str+b1;
            }

            else if(a1!='0' &&b1=='0'){
                str=str+b1;
            }

            char c1=arr[1].charAt(6);
            str=str+c1;

            char d1=arr[1].charAt(8);
            str=str+'.'+d1;
            low_thres1.setText(str);

            System.out.println("str is" +str);

            //channel1 high thres

            String str2="";
            char a2=arr[2].charAt(4);
            if(a2  =='-'){
                str2=str2+"-";
            }

            else if(a2!='0')
            {
                str2=str2+a2;
            }

            char b2=arr[2].charAt(5);
            if(a2!='-'&&b2!='0'){
                str2=str2+b2;
            }
            else if(a2=='-'&&b2!='0'){
                str2=str2+b2;
            }

            else if(a2!='0' &&b2=='0'){
                str2=str2+b2;
            }

            char c2=arr[2].charAt(6);
            str2=str2+c2;

            char d2=arr[2].charAt(8);
            str2=str2+'.'+d2;
            high_thres1.setText(str2);

            System.out.println("str 2is" +str2);

            //channel2 low thres
            String str4="";
            char a4=arr[4].charAt(4);
            if(a4  =='-'){
                str4=str4+"-";
            }

            else if(a4!='0')
            {
                str4=str4+a4;
            }

            char b4=arr[4].charAt(5);
            if(a4!='-'&&b4!='0'){
                str4=str4+b4;
            }
            else if(a4=='-'&&b4!='0'){
                str4=str4+b4;
            }

            else if(a4!='0' &&b4=='0'){
                str4=str4+b4;
            }

            char c4=arr[4].charAt(6);
            str4=str4+c4;

            char d4=arr[4].charAt(8);
            str4=str4+'.'+d4;
            low_thres2.setText(str4);

            System.out.println("str4 is" +str4);

            //channel2 high thres

            String str5="";
            char a5=arr[5].charAt(4);
            if(a5  =='-'){
                str5=str5+"-";
            }

            else if(a5!='0')
            {
                str5=str5+a5;
            }

            char b5=arr[5].charAt(5);
            if(a5!='-'&&b5!='0'){
                str5=str5+b5;
            }
            else if(a5=='-'&&b5!='0'){
                str5=str5+b5;
            }

            else if(a5!='0' &&b5=='0'){
                str5=str5+b5;
            }

            char c5=arr[5].charAt(6);
            str5=str5+c5;

            char d5=arr[5].charAt(8);
            str5=str5+'.'+d5;
            high_thres2.setText(str5);

            System.out.println("str5 is" +str5);
            btnSubmit1 = (Button) findViewById(R.id.btnSubmit1);
            btnSubmit1.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("s1",s1);
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


        }

    }
}