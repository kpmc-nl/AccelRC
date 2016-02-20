package com.kpmc.accelrc.core.motionsensor;

/**
 * Created by matthijs on 29/08/15.
 */
public interface MotionSensor {


    interface Listener {
        void onUpdate(MotionSensor sensor, float frontBack, float leftRight);
    }

    interface RawListener {
        void onUpdate(MotionSensor sensor, float x, float y, float z);
    }

    void invalidatePreferences();

    void register(Listener listener);

    void register(RawListener rawListener);

    void unregister(Listener listener);

    void unregister(RawListener rawListener);

    float getFrontBackExtreme();

    float getLeftRightExtreme();


}
