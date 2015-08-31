package com.kpmc.accelrc.core.motionsensor;

import android.app.Activity;

/**
 * Created by matthijs on 29/08/15.
 */
public interface MotionSensorPreferences {

    class MotionSensorBinding {
        public MotionSensorAxis axis;
        public int extreme;
        public boolean reversed;
    }

    /**
     * Should be called in the first activity to launch!
     *
     * @param source
     */
    void bind(Activity source);

    MotionSensorBinding getFrontBackBinding();

    MotionSensorBinding getRightLeftBinding();

}
