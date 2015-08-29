package com.kpmc.accelrc.application;

import android.app.Activity;

import com.kpmc.accelrc.AccelRCModule;

/**
 * Created by matthijs on 29/08/15.
 */
public class DaggerUtil {

    public static AccelRCApplication.ApplicationComponent component(Activity activity){
        return ((AccelRCApplication) activity.getApplication()).component();
    }

}
