package com.kpmc.accelrc.activity.debug;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.kpmc.accelrc.R;
import com.kpmc.accelrc.dagger.Accelerometer;
import com.kpmc.accelrc.dagger.DaggerUtil;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AccelDebugActivity extends Activity implements SensorEventListener {

    @Inject
    @Accelerometer
    protected Sensor accelSensor;

    @Inject
    protected SensorManager sensorManager;

    @Bind(R.id.azimuthBar)
    protected ProgressBar azimuthBar;
    @Bind(R.id.pitchBar)
    protected ProgressBar pitchBar;
    @Bind(R.id.rollBar)
    protected ProgressBar rollBar;

    @Bind(R.id.azimuthInfo)
    protected TextView azimuthTV;
    @Bind(R.id.pitchInfo)
    protected TextView pitchTV;
    @Bind(R.id.rollInfo)
    protected TextView rollTV;

    private float maxAzimuth, maxPitch, maxRoll;

 //   private BluetoothAdapter bluetoothAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accel_debug);
        ButterKnife.bind(this);
        DaggerUtil.component(this).inject(this);


//        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
//        if (null == bluetoothAdapter) {
//            Toast.makeText(this, "No bluetooth adapter.", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        if (!bluetoothAdapter.isEnabled()) {
//            Toast.makeText(this, "Bluetooth not enabled.", Toast.LENGTH_SHORT).show();
//        }
//
//        for (BluetoothDevice dev : bluetoothAdapter.getBondedDevices()) {
//            if("HC-06".equals(dev.getName())){
//                Toast.makeText(this, "Found the right device!", Toast.LENGTH_SHORT).show();;
//                //new BTTestThread(dev, this).start();
//            }
//        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        if (null == accelSensor) {
            Log.e("AccelDebugActivity", "Got null for sensor type TYPE_ACCELEROMETER");
            Toast.makeText(this, "NULL FOR ACCELEROMETER", Toast.LENGTH_SHORT).show();
        } else {
            // For now, take the default sampling rate for games. Should be sufficient for RC use.
            sensorManager.registerListener(this, accelSensor, SensorManager.SENSOR_DELAY_GAME);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

      /*
       * According to the Android documentation:
       *
       * event.values[0]: azimuth, rotation around the Z axis.
       * event.values[1]: pitch, rotation around the X axis.
       * event.values[2]: roll, rotation around the Y axis.
       */

        float valueAzimuth = event.values[0];
        float valuePitch = event.values[1];
        float valueRoll = event.values[2];

        maxAzimuth = Math.max(Math.abs(valueAzimuth), maxAzimuth);
        maxPitch = Math.max(Math.abs(valuePitch), maxPitch);
        maxRoll = Math.max(Math.abs(valueAzimuth), maxRoll);


        azimuthTV.setText(String.format("%.3f / %.3f", valueAzimuth, maxAzimuth));
        pitchTV.setText(String.format("%.3f / %.3f", valuePitch, maxPitch));
        rollTV.setText(String.format("%.3f / %.3f", valueRoll, maxRoll));

        azimuthBar.setProgress(50 + (int) (50 * valueAzimuth / maxAzimuth));
        pitchBar.setProgress(50 + (int) (50 * valuePitch / maxPitch));
        rollBar.setProgress(50 + (int) (50 * valueRoll / maxRoll));


    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        Log.d("AccelDebugActivity", "Accuracy changed: " + sensor.getName());
    }
}
