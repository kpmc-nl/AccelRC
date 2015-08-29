package com.kpmc.accelrc;

import android.content.Context;
import android.hardware.SensorManager;

import com.kpmc.accelrc.application.AccelRCApplication;
import com.kpmc.accelrc.application.ApplicationContext;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import static android.content.Context.SENSOR_SERVICE;


/**
 * Created by matthijs on 29/08/15.
 */
@Module
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
}
