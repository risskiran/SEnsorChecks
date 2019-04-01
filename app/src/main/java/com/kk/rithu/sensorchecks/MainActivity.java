package com.kk.rithu.sensorchecks;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    Handler hd=new Handler();

    TextView tv;
    public static float swRoll;
    public static float swPitch;
    public static float swAzimuth;

    public static SensorManager mSensorManager;
    public static Sensor accelerometer;
    public static Sensor magnetometer;

    public static float[] mAccelerometer = null;
    public static float[] mGeomagnetic = null;

    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
//        // onSensorChanged gets called for each sensor so we have to remember the values
//        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
//            mAccelerometer = event.values;
//        }
//
//        if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
//            mGeomagnetic = event.values;
//        }
//
//        if (mAccelerometer != null && mGeomagnetic != null) {
//            float R[] = new float[9];
//            float I[] = new float[9];
//            boolean success = SensorManager.getRotationMatrix(R, I, mAccelerometer, mGeomagnetic);
//
//            if (success) {
//                float orientation[] = new float[3];
//                SensorManager.getOrientation(R, orientation);
//                // at this point, orientation contains the azimuth(direction), pitch and roll values.
//                double azimuth = 180 * orientation[0] / Math.PI;
//                double pitch = 180 * orientation[1] / Math.PI;
//                double roll = 180 * orientation[2] / Math.PI;
//
//                tv.setText(azimuth+"");
//            }
//        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv=(TextView)findViewById(R.id.tv);
//        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
//        accelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
//        magnetometer = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        Intent i=new Intent(getApplicationContext(),ShakeService.class);
        startService(i);

        hd.post(r);
    }

    Runnable r=new Runnable() {
        @Override
        public void run() {
            tv.setText(ShakeService.azimuth+"");
            hd.postDelayed(r,2000);
        }
    };

    @Override
    protected void onResume() {
        super.onResume();

//        mSensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
//        mSensorManager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause() {
        super.onPause();
//        mSensorManager.unregisterListener(this, accelerometer);
//        mSensorManager.unregisterListener(this, magnetometer);
    }
}
