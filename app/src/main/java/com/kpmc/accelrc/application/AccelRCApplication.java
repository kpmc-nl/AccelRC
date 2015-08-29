package com.kpmc.accelrc.application;

import android.app.Application;

import com.kpmc.accelrc.AccelDebugActivity;
import com.kpmc.accelrc.AccelRCModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by matthijs on 29/08/15.
 */
public class AccelRCApplication extends Application {

    @Singleton
    @Component(modules = AccelRCModule.class)
    public interface ApplicationComponent {
        void inject(AccelDebugActivity accelDebugActivity);

    }

    private ApplicationComponent component;

    @Override
    public void onCreate() {
        super.onCreate();

        component = DaggerAccelRCApplication_ApplicationComponent
                .builder()
                .accelRCModule(new AccelRCModule(this))
                .build();
    }

    public ApplicationComponent component(){
        return component;
    }
}
