package com.kpmc.accelrc.activity.main;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.kpmc.accelrc.R;
import com.kpmc.accelrc.activity.debug.AccelDebugActivity;
import com.kpmc.accelrc.activity.preferences.PreferenceActivity;
import com.kpmc.accelrc.android.logging.LogHelper;
import com.kpmc.accelrc.android.logging.Logger;
import com.kpmc.accelrc.core.bluetooth.BluetoothMotionSensorListener;
import com.kpmc.accelrc.core.motionsensor.MotionSensor;
import com.kpmc.accelrc.core.motionsensor.MotionSensorPreferences;
import com.kpmc.accelrc.dagger.DaggerUtil;
import com.kpmc.accelrc.view.RCStick;

import javax.inject.Inject;

import app.akexorcist.bluetotohspp.library.BluetoothSPP;
import app.akexorcist.bluetotohspp.library.BluetoothState;
import app.akexorcist.bluetotohspp.library.DeviceList;
import butterknife.Bind;
import butterknife.ButterKnife;

import static app.akexorcist.bluetotohspp.library.BluetoothState.DEVICE_OTHER;
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

    @Bind(R.id.btStatus)
    protected TextView btStatus;

    private RcStickMotionSensorListener rcStickMotionSensorListener;
    private TextViewMotionSenorRawListener textViewMotionSenorRawListener;
    private BluetoothMotionSensorListener btMotionSensorListener;

    private BluetoothSPP bt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        DaggerUtil.component(this).inject(this);
        motionSensorPreferences.bind(this);
        log.d("onCreate");

        bt = new BluetoothSPP(this);
        bt.setBluetoothConnectionListener(new BluetoothStatusTextConnectionListener(btStatus, bt));

        rcStickMotionSensorListener = new RcStickMotionSensorListener(rcStick);
        textViewMotionSenorRawListener = new TextViewMotionSenorRawListener(xValue, yValue, zValue);
        btMotionSensorListener = new BluetoothMotionSensorListener(bt);

    }

    @Override
    protected void onDestroy() {
        bt.stopService();
        super.onDestroy();
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (!bt.isBluetoothEnabled()) {
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(intent, BluetoothState.REQUEST_ENABLE_BT);
        } else {
            if (!bt.isServiceAvailable()) {
                bt.setupService();
                bt.startService(DEVICE_OTHER);
            }
        }

    }

    @Override
    protected void onResume() {
        motionSensor.register(rcStickMotionSensorListener);
        motionSensor.register(textViewMotionSenorRawListener);
        motionSensor.register(btMotionSensorListener);
        super.onResume();
    }


    @Override
    protected void onPause() {
        motionSensor.unregister(rcStickMotionSensorListener);
        motionSensor.unregister(textViewMotionSenorRawListener);
        motionSensor.unregister(btMotionSensorListener);
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

        switch (id) {
            case R.id.action_settings:
                log.d("Settings selected.");
                Intent prefIntent = new Intent(getApplicationContext(), PreferenceActivity.class);
                startActivityForResult(prefIntent, REQUEST_PREFERENCES);
                return true;
            case R.id.action_connect:
                log.d("Connect selected.");
                if (bt.getServiceState() == BluetoothState.STATE_CONNECTED) {
                    bt.disconnect();
                }
                Intent connectBtIntent = new Intent(getApplicationContext(), DeviceList.class);
                startActivityForResult(connectBtIntent, BluetoothState.REQUEST_CONNECT_DEVICE);
                return true;

            case R.id.accel_debug:
                log.d("AccelDebug selected.");
                Intent accelDebugIntent = new Intent(getApplicationContext(), AccelDebugActivity.class);
                startActivity(accelDebugIntent);
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

            case BluetoothState.REQUEST_CONNECT_DEVICE:
                if (resultCode == RESULT_OK) {
                    bt.connect(data);
                    return;
                }
                break;
            case BluetoothState.REQUEST_ENABLE_BT:
                if (resultCode == RESULT_OK) {
                    bt.setupService();
                    bt.startService(BluetoothState.DEVICE_OTHER);
                }
                break;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
