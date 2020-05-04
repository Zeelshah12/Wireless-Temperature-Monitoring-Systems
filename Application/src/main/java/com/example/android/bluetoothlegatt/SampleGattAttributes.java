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

import java.util.HashMap;

/**
 * This class includes a small subset of standard GATT attributes for demonstration purposes.
 */
public class SampleGattAttributes {
    private static HashMap<String, String> attributes = new HashMap();
    public static String HM_10 = "49535343-1e4d-4bd9-ba61-23c647249616";// write value of characteristics here
    public static String CLIENT_CHARACTERISTIC_CONFIG = "00002902-0000-1000-8000-00805f9b34fb"; //static value for all ble devices.


    public static String ble = "49535343-8841-43f4-a8d4-ecbe34729bb3"; //edited for writing from mobile to ble

    static {
        // Sample Services.
       // attributes.put("0000180d-0000-1000-8000-00805f9b34fb", "Heart Rate Service");
        attributes.put("49535343-fe7d-4ae5-8fa9-9fafd205e455", "Zeel service");
       attributes.put("0000180a-0000-1000-8000-00805f9b34fb", "Device INFO Service");
        // Sample Characteristics.
       attributes.put(HM_10, "Zeel characteristics");
        attributes.put(ble, "from mobile to ble");
        attributes.put("00002a29-0000-1000-8000-00805f9b34fb", "Manufacturer Name String");
      //  attributes.put("49535343-1e4d-4bd9-ba61-23c647249616", "Zeel characteristics");
    }

    public static String lookup(String uuid, String defaultName) {
        String name = attributes.get(uuid);
        return name == null ? defaultName : name;
    }
}
