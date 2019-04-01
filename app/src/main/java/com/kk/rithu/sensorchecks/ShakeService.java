package com.kk.rithu.sensorchecks;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.widget.Toast;
import java.util.concurrent.ExecutorService;

public class ShakeService extends Service implements SensorEventListener
{
    public static float swRoll;
    public static float swPitch;
    public static float swAzimuth;
    public static SensorManager mSensorManager;
    public static Sensor accelerometer;
    public static Sensor magnetometer;
    private ExecutorService mExecutor = null;
    public static float[] mAccelerometer = null;
    public static float[] mGeomagnetic = null;
    private boolean mIsServiceStarted = false;
    private Context mContext = null;
    public static double azimuth=0;

    public ShakeService() {
        super();
    }

    public ShakeService(Context context) {
        super();
        if (context != null)
            mContext = context;
        else
            mContext = getBaseContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();

//        mContext = getBaseContext();
//        mSensorManager = (SensorManager)mContext.getSystemService(SENSOR_SERVICE);
//        accelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
//        magnetometer = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        azimuth=0;
        Toast.makeText(getBaseContext(), "Service onCreate", Toast.LENGTH_SHORT).show();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (isServiceStarted() == false) {
            mContext = getBaseContext();
            mSensorManager = (SensorManager)mContext.getSystemService(SENSOR_SERVICE);
            accelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            magnetometer = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

            mSensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
            mSensorManager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_GAME);
        }
        //set started to true
        mIsServiceStarted = true;
        Toast.makeText(mContext, "Service onStartCommand", Toast.LENGTH_SHORT).show();
        return Service.START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Toast.makeText(mContext, "Service onDestroy", Toast.LENGTH_LONG).show();
        mIsServiceStarted = false;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public boolean isServiceStarted() {
        return mIsServiceStarted;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        // onSensorChanged gets called for each sensor so we have to remember the values
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            mAccelerometer = event.values;
        }
        if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            mGeomagnetic = event.values;
        }

        if (mAccelerometer != null && mGeomagnetic != null) {
            float R[] = new float[9];
            float I[] = new float[9];
            boolean success = SensorManager.getRotationMatrix(R, I, mAccelerometer, mGeomagnetic);
            if (success) {
                float orientation[] = new float[3];
                SensorManager.getOrientation(R, orientation);
                // at this point, orientation contains the azimuth(direction), pitch and roll values.
                azimuth =  180*orientation[0] / Math.PI;
                double pitch =  180*orientation[1] / Math.PI;
                double roll =  180*orientation[2] / Math.PI;

              //  Toast.makeText(getApplicationContext(),azimuth+"--",Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}