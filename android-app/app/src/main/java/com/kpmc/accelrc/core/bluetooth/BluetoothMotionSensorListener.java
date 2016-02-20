package com.kpmc.accelrc.core.bluetooth;

import com.kpmc.accelrc.core.motionsensor.MotionSensor;

import app.akexorcist.bluetotohspp.library.BluetoothSPP;
import app.akexorcist.bluetotohspp.library.BluetoothState;

import static app.akexorcist.bluetotohspp.library.BluetoothState.STATE_CONNECTED;

/**
 * Created by matthijs on 05/09/15.
 */
public class BluetoothMotionSensorListener implements MotionSensor.Listener {

    private final BluetoothSPP bluetoothSPP;


    long lastUpdate = 0;

    Integer[] avgs = new Integer[4];


    public BluetoothMotionSensorListener(BluetoothSPP bluetoothSPP) {
        this.bluetoothSPP = bluetoothSPP;
    }


    @Override
    public void onUpdate(MotionSensor sensor, float frontBack, float leftRight) {
        if (
                bluetoothSPP.isBluetoothEnabled() &&
                        bluetoothSPP.isBluetoothAvailable() &&
                        bluetoothSPP.isServiceAvailable() &&
                        bluetoothSPP.getServiceState() == STATE_CONNECTED) {

            float fbEx = sensor.getFrontBackExtreme();
            float lrEx = sensor.getLeftRightExtreme();

            int[] vals = new int[4];

            vals[0] = 1500 + (int) (Math.min(frontBack, fbEx) / fbEx * 1000);
            vals[1] = 1500 + (int) (Math.min(leftRight, lrEx) / lrEx * 1000);
            vals[2] = 1500; //TODO
            vals[3] = 1500; //TODO


            long t = System.currentTimeMillis();

            for (int i = 0; i < 4; i++) {
                if (null == avgs[i]) {
                    avgs[i] = vals[i];
                } else {
                    avgs[i] = (avgs[i] / 2) + vals[i] / 2;
                }
            }

            if (t - 25 < lastUpdate) {
                return;
            }
            lastUpdate = t;


            String btData = String.format("%d,%d,%d,%d\n", avgs[0], avgs[1], avgs[2], avgs[3]);

            bluetoothSPP.send(btData, false);
        }

    }
}
