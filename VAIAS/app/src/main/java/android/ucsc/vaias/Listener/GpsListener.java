package android.ucsc.vaias.Listener;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;



public class GpsListener extends AppCompatActivity implements android.location.LocationListener, SensorEventListener {

    private Context contexte;
    GpsCallBack gpsCallBack;

    //Sensor manager
    private SensorManager mSensorManager;
    private Sensor accelerometer;
    //private Sensor gyroscope;
    float gravity[] = {0,0,(float)9.81};

    // Location
    private LocationManager locationManager;
    private int timeLastLocationData;
    private float speed[]= {0,0,0};
    private int currentSpeed = 0;

    public GpsListener(Context context, GpsCallBack gpsCallBack) {
        contexte = context;

        //Register
        mSensorManager = (SensorManager) contexte.getSystemService(Context.SENSOR_SERVICE);
        accelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        //gyroscope = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        // Lance le service de localisation
        timeLastLocationData = (int)(System.currentTimeMillis()/1000);
        locationManager = (LocationManager) contexte.getSystemService(LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(contexte, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(contexte, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        mSensorManager.registerListener(this, accelerometer, 500000);
        //mSensorManager.registerListener(this, gyroscope, 100000);

        this.gpsCallBack = gpsCallBack;
    }


    public void calculChoc(float pwr) {

        //if choc < 1 m/s^2 do nothing
        if (pwr < 2) {return;}

        float AccidentProba = 0;
        AccidentProba = pwr/2;

        //On cherche une baisse de vitesse et la vitesse moyenne dans les 20 derniere mesure
        float chocDif = 0;
        float vitmoy = 0;
        for(int i = 0; i < 2; i++){
            vitmoy += speed[i];
            float tmpChocDif = speed[i+1] - speed[i];
            if (tmpChocDif < 0) {
                if (tmpChocDif < chocDif) {
                    chocDif = tmpChocDif;
                }
            }
        }
        vitmoy = vitmoy/20;

        //Log.d("tttr",""+chocDif);
        AccidentProba = ((Math.abs(chocDif)/2)+1) * AccidentProba;

        //If the last location mesure is less than 30sec old
        if (timeLastLocationData > ((int)(System.currentTimeMillis()/1000) - 30) ){
            //All is good go to the right callback
            gpsCallBack.onChocEventRecieved(AccidentProba);
        }else{
            //Show a not relevent message
            gpsCallBack.onWarningEventRecieved(AccidentProba);
        }

    }


    @Override
    public void onSensorChanged(SensorEvent event) {

        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){

            // alpha is calculated as t / (t + dT)
               // with t, the low-pass filter's time-constant
               // and dT, the event delivery rate
               float alpha = (float)0.8;
               gravity[0] = alpha * gravity[0] + (1 - alpha) * event.values[0];
               gravity[1] = alpha * gravity[1] + (1 - alpha) * event.values[1];
               gravity[2] = alpha * gravity[2] + (1 - alpha) * event.values[2];
               event.values[0] = event.values[0] - gravity[0];
               event.values[1] = event.values[1] - gravity[1];
               event.values[2] = event.values[2] - gravity[2];

               //If accelerometer data
               calculChoc(Math.abs(event.values[0]) + Math.abs(event.values[1]) + Math.abs(event.values[2]));
        }else if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE){
            //If gyro data
        }

        /** Sending Event */
        gpsCallBack.onSensorEventRecieved(event);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        // Do nothing here.
    }

    @Override
    public void onLocationChanged(Location location) {
        timeLastLocationData = (int)(System.currentTimeMillis()/1000);
        speed[currentSpeed] = location.getSpeed() * (float) 3.6;
        gpsCallBack.onSpeedRecieved(speed[currentSpeed]);

        if (currentSpeed < 2) {
            currentSpeed++;
        }else{
            currentSpeed = 0;
        }
    }








    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {
        AlertDialog.Builder builder = new AlertDialog.Builder(contexte);
        builder.setMessage("You need to activate your gps localisation to use this application. Please turn on the access to your position in Settings.")
                .setTitle("Unable to get your position")
                .setCancelable(false)
                .setPositiveButton("Settings",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                contexte.startActivity(intent);
                            }
                        }
                )
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                ((Activity) contexte).finish();
                            }
                        }
                );
        AlertDialog alert = builder.create();
        alert.show();
    }

    public interface GpsCallBack {
        void onSpeedRecieved(Float vitesse);
        void onSensorEventRecieved (SensorEvent event);
        void onChocEventRecieved(Float AccidentProba);
        void onWarningEventRecieved(Float AccidentProba);
    }
}
