package com.kpmc.accelrc;

import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.preference.PreferenceManager;

import com.kpmc.accelrc.application.AccelRCApplication;
import com.kpmc.accelrc.core.CoreModule;
import com.kpmc.accelrc.dagger.Accelerometer;
import com.kpmc.accelrc.dagger.ApplicationContext;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import static android.content.Context.SENSOR_SERVICE;
import static android.hardware.Sensor.TYPE_ACCELEROMETER;


/**
 * Created by matthijs on 29/08/15.
 */
@Module(includes = CoreModule.class)
public class AccelRCModule {

    private final AccelRCApplication application;

    public AccelRCModule(AccelRCApplication application) {
        this.application = application;
    }


    @Provides
    @Singleton
    @ApplicationContext
    Context provideApplicationContext() {
        return application;
    }

    @Provides
    @Singleton
    SensorManager provideSensorManager() {
        return (SensorManager) application.getSystemService(SENSOR_SERVICE);
    }

    @Provides
    @Singleton
    @Accelerometer
    Sensor provideAccelerometer(SensorManager sensorManager) {
        return sensorManager.getDefaultSensor(TYPE_ACCELEROMETER);
    }


    @Provides
    @Singleton
    SharedPreferences provideSharedPreferences(@ApplicationContext Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

}
