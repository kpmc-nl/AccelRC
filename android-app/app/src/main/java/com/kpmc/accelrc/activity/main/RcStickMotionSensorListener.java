package com.kpmc.accelrc.activity.main;

import com.kpmc.accelrc.android.logging.LogHelper;
import com.kpmc.accelrc.android.logging.Logger;
import com.kpmc.accelrc.core.motionsensor.MotionSensor;
import com.kpmc.accelrc.view.RCStick;

import javax.inject.Inject;

/**
 * Created by matthijs on 31/08/15.
 */
public class RcStickMotionSensorListener implements MotionSensor.Listener {

    private final Logger log = LogHelper.getLogger(this);

    private final RCStick rcStick;

    @Inject
    public RcStickMotionSensorListener(RCStick rcStick) {
        this.rcStick = rcStick;
    }

    @Override
    public void onUpdate(MotionSensor sensor, float frontBack, float leftRight) {
        float fbEx = sensor.getFrontBackExtreme();
        float lrEx = sensor.getLeftRightExtreme();


        int verticalValue = (int) (Math.min(frontBack, fbEx) / fbEx * rcStick.vResolution);
        int horizontalValue = (int) (Math.min(leftRight, lrEx) / lrEx * rcStick.hResolution);

        rcStick.update(verticalValue, horizontalValue);
    }
}
