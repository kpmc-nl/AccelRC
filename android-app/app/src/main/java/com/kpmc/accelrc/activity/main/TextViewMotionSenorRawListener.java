package com.kpmc.accelrc.activity.main;

import android.widget.TextView;

import com.kpmc.accelrc.core.motionsensor.MotionSensor;

/**
 * Created by matthijs on 31/08/15.
 */
public class TextViewMotionSenorRawListener implements MotionSensor.RawListener {


    private final TextView xValue;
    private final TextView yValue;
    private final TextView zValue;

    public TextViewMotionSenorRawListener(TextView xValue, TextView yValue, TextView zValue) {
        this.xValue = xValue;
        this.yValue = yValue;
        this.zValue = zValue;
    }

    @Override
    public void onUpdate(MotionSensor sensor, float x, float y, float z) {


        xValue.setText(String.format("%.2f", x));
        yValue.setText(String.format("%.2f", y));
        zValue.setText(String.format("%.2f", z));
    }
}
