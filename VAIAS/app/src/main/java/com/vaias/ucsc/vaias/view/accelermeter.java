package com.vaias.ucsc.vaias.view;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.vaias.ucsc.vaias.R;

public class accelermeter extends AppCompatActivity implements SensorEventListener{

    Sensor accelerometer;
    SensorManager sm;
    TextView acceleration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accelermeter);
        acceleration=(TextView)findViewById(R.id.accText);

        sm=(SensorManager)getSystemService(SENSOR_SERVICE);
        accelerometer=sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
         sm.registerListener(this,accelerometer,SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        acceleration.setText("X : "+event.values[0]+"\nY : "+event.values[1] +"\nZ : "+event.values[2] );

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
