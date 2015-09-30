package com.kpmc.accelrc.core.motionsensor;

import android.app.Activity;
import android.content.SharedPreferences;

import com.kpmc.accelrc.R;

import javax.inject.Inject;

import butterknife.BindString;
import butterknife.ButterKnife;

import static com.kpmc.accelrc.core.motionsensor.MotionSensorAxis.X_AXIS;
import static com.kpmc.accelrc.core.motionsensor.MotionSensorAxis.Y_AXIS;
import static com.kpmc.accelrc.core.motionsensor.MotionSensorAxis.Z_AXIS;

/**
 * Created by matthijs on 29/08/15.
 */
public class MotionSensorPreferencesImpl implements MotionSensorPreferences {

    private final SharedPreferences sharedPreferences;

    @BindString(R.string.xAxis)
    protected String xAxis;

    @BindString(R.string.yAxis)
    protected String yAxis;

    @BindString(R.string.zAxis)
    protected String zAxis;

    @BindString(R.string.pref_fb_bind)
    protected String prefFbBind;

    @BindString(R.string.pref_rl_bind)
    protected String prefRlBind;

    @BindString(R.string.pref_fb_extreme)
    protected String prefFbExtreme;

    @BindString(R.string.pref_rl_extreme)
    protected String prefRlExtreme;

    @BindString(R.string.pref_fb_reverse)
    protected String prefFbReverse;

    @BindString(R.string.pref_rl_reverse)
    protected String prefRlReverse;

    @BindString(R.string.pref_lowpass_alpha)
    protected String prefLowPassAlpha;


    @Inject
    public MotionSensorPreferencesImpl(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    @Override
    public void bind(Activity source) {
        ButterKnife.bind(this, source);
    }


    @Override
    public MotionSensorBinding getFrontBackBinding() {
        MotionSensorBinding binding = new MotionSensorBinding();
        binding.axis = getFBAxis();
        binding.extreme = getFBExtreme();
        binding.reversed = isFBReversed();
        return binding;
    }

    @Override
    public MotionSensorBinding getRightLeftBinding() {
        MotionSensorBinding binding = new MotionSensorBinding();
        binding.axis = getRLAxis();
        binding.extreme = getRLExtreme();
        binding.reversed = isRLReversed();
        return binding;
    }

    @Override
    public float getLowPassAlpha() {
        int alphaSliderVal = sharedPreferences.getInt(prefLowPassAlpha, 20);

        return ((float) alphaSliderVal) / 100f;
    }

    private MotionSensorAxis getFBAxis() {
        String value = sharedPreferences.getString(prefFbBind, xAxis);
        return axisFromPrefString(value);
    }

    private MotionSensorAxis getRLAxis() {
        String value = sharedPreferences.getString(prefRlBind, yAxis);
        return axisFromPrefString(value);
    }


    //TODO: Remove magic numbers
    private int getFBExtreme() {
        return sharedPreferences.getInt(prefFbExtreme, 16);
    }

    //TODO: Remove magic numbers
    private int getRLExtreme() {
        return sharedPreferences.getInt(prefRlExtreme, 16);
    }

    private boolean isFBReversed() {
        return sharedPreferences.getBoolean(prefFbReverse, false);
    }

    private boolean isRLReversed() {
        return sharedPreferences.getBoolean(prefRlReverse, false);
    }


    private MotionSensorAxis axisFromPrefString(String prefString) {
        if (xAxis.equals(prefString)) {
            return X_AXIS;
        }
        if (yAxis.equals(prefString)) {
            return Y_AXIS;
        }
        if (zAxis.equals(prefString)) {
            return Z_AXIS;
        }
        return null;
    }
}
