package com.kpmc.accelrc;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
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

import com.kpmc.accelrc.application.DaggerUtil;

import javax.inject.Inject;

import static android.hardware.Sensor.TYPE_ACCELEROMETER;

public class AccelDebugActivity extends Activity implements SensorEventListener {

    @Inject protected SensorManager sensorManager;
    private Sensor accelSensor;


    private ProgressBar azimuthBar, pitchBar, rollBar;
    private TextView maxAzimuthTV, maxPitchTV, maxRollTV;

    private float maxAzimuth, maxPitch, maxRoll;


    private BluetoothAdapter bluetoothAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerUtil.component(this).inject(this);

        setContentView(R.layout.activity_main);

        azimuthBar = (ProgressBar) findViewById(R.id.azimuthBar);
        pitchBar = (ProgressBar) findViewById(R.id.pitchBar);
        rollBar = (ProgressBar) findViewById(R.id.rollBar);

        maxAzimuthTV = (TextView) findViewById(R.id.maxAzimuth);
        maxPitchTV = (TextView) findViewById(R.id.maxPitch);
        maxRollTV = (TextView) findViewById(R.id.maxRoll);

       // sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelSensor = sensorManager.getDefaultSensor(TYPE_ACCELEROMETER);


        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (null == bluetoothAdapter) {
            Toast.makeText(this, "No bluetooth adapter.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!bluetoothAdapter.isEnabled()) {
            Toast.makeText(this, "Bluetooth not enabled.", Toast.LENGTH_SHORT).show();
        }

        for (BluetoothDevice dev : bluetoothAdapter.getBondedDevices()) {
            if("HC-06".equals(dev.getName())){
                Toast.makeText(this, "Found the right device!", Toast.LENGTH_SHORT).show();;
                new BTTestThread(dev, this).start();
            }
        }


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




        maxAzimuthTV.setText(String.format("%.3f / %.3f", valueAzimuth, maxAzimuth));
        maxPitchTV.setText(String.format("%.3f / %.3f", valuePitch, maxPitch));
        maxRollTV.setText(String.format("%.3f / %.3f", valueRoll, maxRoll));

        azimuthBar.setProgress(50 + (int) (50 * valueAzimuth / maxAzimuth));
        pitchBar.setProgress(50 + (int) (50 * valuePitch / maxPitch));
        rollBar.setProgress(50 + (int) (50 * valueRoll / maxRoll));


    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        Log.d("AccelDebugActivity", "Accuracy changed: " + sensor.getName());
    }
}
