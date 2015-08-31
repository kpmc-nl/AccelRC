package com.kpmc.accelrc.activity.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.kpmc.accelrc.R;
import com.kpmc.accelrc.android.logging.LogHelper;
import com.kpmc.accelrc.android.logging.Logger;
import com.kpmc.accelrc.core.motionsensor.MotionSensor;
import com.kpmc.accelrc.core.motionsensor.MotionSensorPreferences;
import com.kpmc.accelrc.dagger.DaggerUtil;
import com.kpmc.accelrc.activity.preferences.PreferenceActivity;
import com.kpmc.accelrc.view.RCStick;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.kpmc.accelrc.activity.IntentCodes.REQUEST_PREFERENCES;

/**
 * Created by matthijs on 29/08/15.
 */
public class MainActivity extends AppCompatActivity {

    private final Logger log = LogHelper.getLogger(this);

    @Inject
    protected MotionSensor motionSensor;

    @Inject
    protected MotionSensorPreferences motionSensorPreferences;

    @Bind(R.id.rcStick)
    protected RCStick rcStick;

    @Bind(R.id.xValue)
    protected TextView xValue;
    @Bind(R.id.yValue)
    protected TextView yValue;
    @Bind(R.id.zValue)
    protected TextView zValue;


    private RcStickMotionSensorListener rcStickMotionSensorListener;
    private TextViewMotionSenorRawListener textViewMotionSenorRawListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        DaggerUtil.component(this).inject(this);
        motionSensorPreferences.bind(this);
        log.d("onCreate");


        rcStickMotionSensorListener = new RcStickMotionSensorListener(rcStick);
        textViewMotionSenorRawListener = new TextViewMotionSenorRawListener(xValue, yValue, zValue);
    }


    @Override
    protected void onResume() {
        motionSensor.register(rcStickMotionSensorListener);
        motionSensor.register(textViewMotionSenorRawListener);
        super.onResume();
    }


    @Override
    protected void onPause() {
        motionSensor.unregister(rcStickMotionSensorListener);
        motionSensor.unregister(textViewMotionSenorRawListener);
        super.onPause();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        log.d("Inflated menu.");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            log.d("Settings selected.");
            Intent prefIntent = new Intent(getApplicationContext(), PreferenceActivity.class);
            startActivityForResult(prefIntent, REQUEST_PREFERENCES);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_PREFERENCES:
                motionSensor.invalidatePreferences();
                return;
        }


        super.onActivityResult(requestCode, resultCode, data);
    }
}
