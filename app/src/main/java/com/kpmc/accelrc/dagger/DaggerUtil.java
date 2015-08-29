package com.kpmc.accelrc.dagger;

import android.app.Activity;

import com.kpmc.accelrc.AccelRCModule;
import com.kpmc.accelrc.application.AccelRCApplication;

/**
 * Created by matthijs on 29/08/15.
 */
public class DaggerUtil {

    public static AccelRCApplication.ApplicationComponent component(Activity activity){
        return ((AccelRCApplication) activity.getApplication()).component();
    }

}
