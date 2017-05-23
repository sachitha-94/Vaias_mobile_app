package android.ucsc.vaias.Fragments;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.hardware.SensorEvent;
import android.location.Location;
import android.location.LocationManager;
import android.ucsc.vaias.Activity.HomeActivity;
import android.ucsc.vaias.Helper.ActionEmergencyContactHelper;
import android.ucsc.vaias.Helper.AltertDialogHelper;
import android.ucsc.vaias.Intent.SmsDeliever;
import android.ucsc.vaias.Listener.GpsListener;
import android.ucsc.vaias.R;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

//Import for device sensors
import android.hardware.Sensor;
import android.widget.Toast;

import com.google.android.gms.location.LocationListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


/**
 * Created by Annick on 03/02/2017.
 */

public class FirstFragment extends Fragment implements LocationListener {

    private boolean isCrack = false;
    private double lat;
    private double lon;
    //Just textview
    TextView percentage;
    TextView speedTextView;
    ImageView egg;
    Toast totoast;



    // Listener
    private GpsListener gpsListener;

    // Permission
    private boolean permissionsEnabled;

    private boolean sendSMS;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Check permission
        ActivatePermissions();


    }

    public static FirstFragment newInstance(int instance) {
        Bundle args = new Bundle();
        args.putInt("argsInstance", instance);
        FirstFragment firstFragment = new FirstFragment();
        firstFragment.setArguments(args);
        return firstFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            return inflater.inflate(R.layout.fragment_first, container, false);
        } else {
            return inflater.inflate(R.layout.fragment_first, container, false);
        }
    }

    public void onStart() {
        super.onStart();

        percentage = (TextView) getActivity().findViewById(R.id.percentageID);
        egg = (ImageView) getActivity().findViewById(R.id.eggimgID);
        speedTextView = (TextView) getActivity().findViewById(R.id.speedTextView);
        egg.setImageResource(R.drawable.egg);
        totoast = Toast.makeText(getContext(), "", Toast.LENGTH_SHORT);
        isCrack = false;
        //seekbar = (SeekBar) getActivity().findViewById(R.id.detectparamID);
        //seekbar.setMax(100);
        //seekbar.setProgress(5);


        LocationManager locationManager = (LocationManager) this.getActivity().getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this.getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this.getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new android.location.LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                if (location==null){
                    percentage.setText("--");
                }else {
                    float speed = location.getSpeed();
                    percentage.setText(String.valueOf( Math.round(speed*3.6)));
                    lat=location.getLatitude();
                    lon=location.getLongitude();
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

            }
        });
        this.onLocationChanged(null);

        /** Using GPS */
        gpsListener = new GpsListener(getContext(), new GpsListener.GpsCallBack() {
            @Override
            public void onSpeedRecieved(Float vitesse) {
                //Log.d("vitesse", "vitesse: " + vitesse);
            }

            @Override
            public void onSensorEventRecieved(SensorEvent event) {
                if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
                    //Log.d("sensor", "Accelerometer :\nx:" + event.values[0] + "\ny:" +event.values[1] + "\nz:" + event.values[2]);
                }else if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE){
                   // Log.d("sensor", "Gyrometer :\nx:" + event.values[0] + "\ny:" +event.values[1] + "\nz:" + event.values[2]);
                }
            }

            @Override
            public void onChocEventRecieved(Float AccidentProba) {

                if(!isCrack) {
                    //percentage.setText((Math.floor(AccidentProba * 10) / 10)  +"%");

                    Random rand = new Random();
                    int n = rand.nextInt(3) - 1;
                    egg.setRotation(n * AccidentProba * 5);

                    /** User got an accident */
                    if (AccidentProba > 10) {
                        isCrack = true;
                        egg.setImageResource(R.drawable.newcrack);

                        try {
                            Vibrator v = (Vibrator) getContext().getSystemService(getContext().VIBRATOR_SERVICE);
                            v.vibrate(500);
                        }catch (Exception e){
                            Log.d("error", "Error");
                        }

                        try {

                            /** Creating an alertdialog to prevent the user that he has 60 secondss to cancel the operation */
                            AltertDialogHelper altertDialogHelper = new AltertDialogHelper(getContext(), new AltertDialogHelper.AltertDialogCallback() {
                                @Override
                                public void onTimerFinish(boolean sendSMS) {
                                    /** In case where the user got an accident*/
                                    if (sendSMS == true) {
                                        ArrayList<String> phoneNumber = new ArrayList<>();
                                        ActionEmergencyContactHelper actionEmergencyContactHelper = new ActionEmergencyContactHelper(getActivity().getApplicationContext());
                                        actionEmergencyContactHelper.insertEmergencyContact("dad","0718739936");
                                        HashMap<String, String> emergencyContacts = actionEmergencyContactHelper.getAllEmergencyContacts();

                                        for (Map.Entry<String, String> e : emergencyContacts.entrySet()) {
                                            phoneNumber.add(e.getValue());
                                            System.out.println(e.getValue());
                                        }

                                        String smsBody = "I just had an accident https://www.google.com/maps/search/?api=1&query="+lat+","+lon;
                                        SmsDeliever smsDeliever = new SmsDeliever(getContext(), phoneNumber, smsBody);
                                        smsDeliever.SendingMessage();
                                    }
                                    /** Dans l'autre cas on reset l'oeuf*/
                                    else {
                                        isCrack = false;
                                        egg.setImageResource(R.drawable.egg);

                                    }
                                }
                            });
                            altertDialogHelper.shouldISendMessage();



                        }catch (Exception e){
                            Log.d("error", "Error");
                        }
                    }
                }

            }

            @Override
            public void onWarningEventRecieved(Float AccidentProba) {

                if(!isCrack) {
                   // percentage.setText(Math.floor(AccidentProba * 10) / 10 + "%");

                    //Show toast
                    totoast.setText("No GPS signal");
                    totoast.show();

                    Random rand = new Random();
                    int n = rand.nextInt(3) - 1;
                    egg.setRotation(90);


                    /*if (AccidentProba > 10) {
                        isCrack = true;
                        egg.setImageResource(R.drawable.newcrack);

                        Vibrator v = (Vibrator) getContext().getSystemService(getContext().VIBRATOR_SERVICE);
                        v.vibrate(500);
                    }*/

                }

            }

        });
    }


    @Override
    public void onPause() {
        totoast.cancel();
        //mSensorManager.unregisterListener(gpsListener, accelerometer);
        //mSensorManager.unregisterListener(gpsListener, gyroscope);
        super.onPause();
    }

    @Override
    public void onResume() {
        totoast.cancel();
        //mSensorManager.registerListener(gpsListener, accelerometer, SensorManager.SENSOR_DELAY_UI);
        //mSensorManager.registerListener(gpsListener, gyroscope, SensorManager.SENSOR_DELAY_UI);
        super.onResume();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 10:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    permissionsEnabled = true;
                } else {
                    Log.d("test ", "Position refused");
                    AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                    alert.setMessage("Activating position permissions is mandatory");
                    alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            ActivatePermissions();
                        }
                    });
                    AlertDialog dialog = alert.create();
                    dialog.show();
                }

        }
    }

    private void ActivatePermissions() {
        if (permissionsEnabled != true) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.INTERNET,
                    Manifest.permission.ACCESS_NETWORK_STATE,
                    Manifest.permission.READ_CONTACTS,
                    Manifest.permission.SEND_SMS
            }, 10);
        }
    }

    @Override
    public void onLocationChanged(Location location) {
       /* if (location==null){
            speedTextView.setText("-- Km/h");
        }else {
            float speed = location.getSpeed();
            speedTextView.setText(Math.round(speed * 3.6) + " Km/h");
        }*/
    }




}
