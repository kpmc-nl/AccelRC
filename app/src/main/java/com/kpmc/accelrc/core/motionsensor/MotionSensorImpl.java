package com.kpmc.accelrc.core.motionsensor;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.google.common.base.Function;
import com.google.common.collect.Sets;
import com.kpmc.accelrc.android.logging.LogHelper;
import com.kpmc.accelrc.android.logging.Logger;
import com.kpmc.accelrc.dagger.Accelerometer;
import com.kpmc.accelrc.util.VoidFunction;

import java.util.Set;

import javax.inject.Inject;

/**
 * Created by matthijs on 29/08/15.
 */
public class MotionSensorImpl implements MotionSensor, SensorEventListener {

    private final Logger log = LogHelper.getLogger(this);

    private final SensorManager sensorManager;
    private final Sensor accelerometer;
    private final MotionSensorPreferences motionSensorPreferences;

    private MotionSensorPreferences.MotionSensorBinding fbBinding;
    private MotionSensorPreferences.MotionSensorBinding rlBinding;

    private Set<Listener> listeners;
    private Set<RawListener> rawListeners;

    @Inject
    public MotionSensorImpl(SensorManager sensorManager,
                            @Accelerometer Sensor accelerometer,
                            MotionSensorPreferences motionSensorPreferences) {
        this.sensorManager = sensorManager;
        this.accelerometer = accelerometer;
        this.motionSensorPreferences = motionSensorPreferences;

        this.listeners = Sets.newHashSet();
        this.rawListeners = Sets.newHashSet();
    }

    @Override
    public void invalidatePreferences() {
        fbBinding = motionSensorPreferences.getFrontBackBinding();
        rlBinding = motionSensorPreferences.getRightLeftBinding();
    }

    @Override
    public void register(final Listener listener) {
        registerImpl(new VoidFunction() {
            @Override
            public void apply() {
                listeners.add(listener);
            }
        });
    }

    @Override
    public void unregister(final Listener listener) {
        unregisterImpl(new VoidFunction() {
            @Override
            public void apply() {
                listeners.remove(listener);
            }
        });
    }

    @Override
    public void register(final RawListener rawListener) {
        registerImpl(new VoidFunction() {
            @Override
            public void apply() {
                rawListeners.add(rawListener);
            }
        });
    }

    @Override
    public void unregister(final RawListener rawListener) {
        unregisterImpl(new VoidFunction() {
            @Override
            public void apply() {
                rawListeners.remove(rawListener);
            }
        });
    }

    private void registerImpl(VoidFunction funct) {
        synchronized (listeners) {
            if (listeners.isEmpty()) {
                invalidatePreferences();
                sensorManager.registerListener(this, accelerometer, 10* SensorManager.SENSOR_DELAY_GAME);
            }
            funct.apply();
            log.d("Added Listener.");
        }
    }

    private void unregisterImpl(VoidFunction funct) {
        synchronized (listeners) {
            funct.apply();
            if (listeners.isEmpty()) {
                sensorManager.unregisterListener(this);
            }
            log.d("Removed Listener.");
        }
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        /* According to the Android documentation */
        float z = event.values[0];
        float x = event.values[1];
        float y = event.values[2];

        float fbValue = calculate(fbBinding, x, y, z);
        float rlValue = calculate(rlBinding, x, y, z);

        for (RawListener rawListener : rawListeners) {
            rawListener.onUpdate(this, x, y, z);
        }
        for (Listener listener : listeners) {
            listener.onUpdate(this, fbValue, rlValue);
        }


    }


    @Override
    public float getFrontBackExtreme() {
        return fbBinding.extreme;
    }

    @Override
    public float getLeftRightExtreme() {
        return rlBinding.extreme;
    }

    private float calculate(MotionSensorPreferences.MotionSensorBinding binding, float x, float y, float z) {
        float result = 0;
        switch (binding.axis) {
            case X_AXIS:
                result = x;
                break;
            case Y_AXIS:
                result = y;
                break;
            case Z_AXIS:
                result = z;
                break;
            default:
                log.e("Unsupported axis found in binding.");
        }
        if (binding.reversed) {
            result *= -1f;
        }
        boolean negative = result < 0;
        result = Math.min(Math.abs(result), binding.extreme);
        return negative ? result * -1f : result;
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        log.w("onAccuracyChanged");
    }
}
