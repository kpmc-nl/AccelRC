package com.kpmc.accelrc.activity.main;

import android.widget.TextView;

import app.akexorcist.bluetotohspp.library.BluetoothSPP;

/**
 * Created by matthijs on 05/09/15.
 */
public class BluetoothStatusTextConnectionListener implements BluetoothSPP.BluetoothConnectionListener {

    private final TextView statusTextView;

    public BluetoothStatusTextConnectionListener(TextView statusTextView, BluetoothSPP bluetoothSPP) {
        this.statusTextView = statusTextView;

        if(!bluetoothSPP.isBluetoothAvailable()){
            statusTextView.setText("Not available");
        }else{
            statusTextView.setText("Disconnected");
        }



    }

    @Override
    public void onDeviceConnected(String name, String address) {
        statusTextView.setText("Connected to: " + name);
    }

    @Override
    public void onDeviceDisconnected() {
        statusTextView.setText("Disconnected");
    }

    @Override
    public void onDeviceConnectionFailed() {
        statusTextView.setText("Connection failed");
    }
}
