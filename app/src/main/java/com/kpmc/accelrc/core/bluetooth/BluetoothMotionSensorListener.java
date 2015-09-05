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


            int verticalMicroSeconds = 1500 + (int) (Math.min(frontBack, fbEx) / fbEx * 1000);
            int horizontalMicroseconds = 1500 + (int) (Math.min(leftRight, lrEx) / lrEx * 1000);

            String btData = String.format("%d,%d", verticalMicroSeconds, horizontalMicroseconds);

            bluetoothSPP.send(btData, true);
        }

    }
}
