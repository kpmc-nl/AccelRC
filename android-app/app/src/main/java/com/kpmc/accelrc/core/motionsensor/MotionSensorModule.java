package com.kpmc.accelrc.core.motionsensor;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by matthijs on 29/08/15.
 */
@Module
public class MotionSensorModule {


    @Provides
    @Singleton
    MotionSensorPreferences provideMotionSensorPreferences(MotionSensorPreferencesImpl impl) {
        return impl;
    }

    @Provides
    @Singleton
    MotionSensor provideMotionSensor(MotionSensorImpl impl) {
        return impl;
    }

}
