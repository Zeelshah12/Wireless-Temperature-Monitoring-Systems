package com.example.android.bluetoothlegatt;

/**
 * Created by Dell on 06/02/2019.
 */

public class User {

    private String _id;
    private String _name;

    public User() {
        this._id = "";
        this._name = "";
    }

    public void setId(String id) {
        this._id = id;
    }

    public String getId() {
        return this._id;
    }

    public void setName(String name) {
        this._name = name;
    }

    public String getName() {
        return this._name;
    }
}
