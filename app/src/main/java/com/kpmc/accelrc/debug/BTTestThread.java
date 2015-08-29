package com.kpmc.accelrc.debug;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.util.UUID;


/**
 * Created by matthijs on 26/08/15.
 */
public class BTTestThread extends Thread {

    private final UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    private BluetoothSocket btSocket;
    private Activity context;

    public BTTestThread(BluetoothDevice device, Activity context) {
        this.context = context;
        try {
            btSocket = device.createInsecureRfcommSocketToServiceRecord(uuid);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void run() {

        int counter = 0;

        try {
            while (true) {
                ++counter;
                final String s = "count " + counter + "\n";
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
                    }
                });
                btSocket.getOutputStream().write(s.getBytes("UTF-8"));

                try{
                    Thread.sleep(1000);
                }catch(InterruptedException e){
                    Thread.currentThread().interrupt();
                    return;
                }

                if (Thread.currentThread().interrupted()) {
                    return;
                }
            }
        } catch (IOException e) {
            Log.e("BTTestThread", "IOException: " + e.getMessage());
        } finally {
            cancel();
        }
    }

    /**
     * Will cancel the listening socket, and cause the thread to finish
     */
    public void cancel() {
        if (null != btSocket) {
            try {
                btSocket.close();
            } catch (IOException e) {
            }
        }
    }

}


