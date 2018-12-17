package com.example.racheli.myapplication;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.Editable;

import com.example.racheli.myapplication.model.backend.Backend;
import com.example.racheli.myapplication.model.backend.BackendFactory;
import com.example.racheli.myapplication.model.datasource.Action;
import com.example.racheli.myapplication.model.datasource.Firebase_DBManager;
import com.example.racheli.myapplication.model.entities.Ride;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;


public class MainActivity extends Activity implements View.OnClickListener/*, GooglePlayServicesClient.ConnectionCallbacks,
        GooglePlayServicesClient.OnConnectionFailedListener*/ {

    private Spinner statusSpinner;
    private EditText nameTextview;
    private EditText locTextview;
    private EditText destTextview;
    private Spinner timeSpinner;
    private EditText emailTextview;
    private EditText phoneTextview;
    private EditText ccTextview;
    private Button orderButton;
    private EditText etChooseTime;
    private ImageButton locationButton;
    private FusedLocationProviderClient mFusedLocationClient;


    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2018-12-12 20:15:04 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    private void findViews() {
        statusSpinner = (Spinner) findViewById(R.id.status_spinner);
        nameTextview = (EditText) findViewById(R.id.name_textview);
        locTextview = (EditText) findViewById(R.id.loc_textview);
        destTextview = (EditText) findViewById(R.id.dest_textview);
        timeSpinner = (Spinner) findViewById(R.id.time_spinner);
        emailTextview = (EditText) findViewById(R.id.email_textview);
        phoneTextview = (EditText) findViewById(R.id.phone_textview);
        ccTextview = (EditText) findViewById(R.id.cc_textview);
        orderButton = (Button) findViewById(R.id.order_button);
        etChooseTime = findViewById(R.id.etChooseTime);
        locationButton = (ImageButton) findViewById(R.id.imageButton2);

        //final EditText chooseTime = (EditText)findViewById(R.id.etChooseTime);

        etChooseTime.setOnClickListener(this);
        orderButton.setOnClickListener(this);
        locationButton.setOnClickListener(this);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) //what is this?
    {

    }

    @Override
    public void onClick(View view) {
        if (view == etChooseTime) {
            TimePickerDialog timePickerDialog = new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                    etChooseTime.setText((hourOfDay + ":" + minutes).toString());
                }
            }, 0, 0, true);
            timePickerDialog.show();
        }
        if (view == locationButton) {
            mFusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    // Got last known location. In some rare situations this can be null.
                    if (location != null) {
                        getLocation(location);

                    }
                }

            });
        }

        if ( view == orderButton ) {
            // Handle clicks for orderButton
            addRide();
        }
    }

    public void getLocation(Location location){
        Geocoder geocoder;
        List<Address> addresses;
        try {
            geocoder = new Geocoder(this, Locale.getDefault());

            addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

            String address = addresses.get(0).getAddressLine(0);
            String city = addresses.get(0).getLocality();
            String country = addresses.get(0).getCountryName();
            locTextview.setText(address + ", " + city);
        }
        catch (Exception e){

        }
    }


    public void addRide(){

        Ride ride = getRide();

        try{
            //String jsonObj = quickParse(ride);
            Backend instance = BackendFactory.getInstance();
            instance.addRide(ride, new Action<Long>() {
                @Override
                public void onSuccess(Long obj) {
                    Toast.makeText(getBaseContext(), "Succeeded" + obj, Toast.LENGTH_LONG).show();
                   // resetView();
                }

                @Override
                public void onFailure(Exception exception) {
                    Toast.makeText(getBaseContext(), "Error \n" + exception.getMessage(), Toast.LENGTH_LONG).show();
                   // resetView();
                }

                @Override
                public void onProgress(String status, double percent) {
                    if (percent != 100)
                        orderButton.setEnabled(false);
                    //addStudentProgressBar.setProgress((int) percent);
                }
            });
        } catch (Exception e) {
            Toast.makeText(getBaseContext(), "Error ", Toast.LENGTH_LONG).show();
            //resetView();
        };
    }

    public Ride getRide(){
        Ride ride = new Ride();
        ride.setPassengerName(this.nameTextview.getText().toString());
        ride.setPassengerMail(this.emailTextview.getText().toString());
        ride.setPhoneNumber(this.phoneTextview.getText().toString());
        ride.setDestination(this.destTextview.getText().toString());
        ride.setOrigin(this.locTextview.getText().toString());
        ride.setCreditCard(this.ccTextview.getText().toString());
        String time = this.etChooseTime.getText().toString();
        time = time + ":00";
        Time timeValue = Time.valueOf(time);
       // String timeOption = (String)this.timeSpinner.getSelectedItem();
       /* if(timeOption == "Departure time"){
            ride.setStartingTime(timeValue);
        }
        else
        {*/
           ride.setEndingTime(timeValue);
        //}

        return ride;
    }
    //the function converts object to Json format
   /* public static String quickParse(Ride ride) throws IllegalArgumentException, IllegalAccessException, JSONException {
        JSONObject jsonObj = new JSONObject();
        try {
            jsonObj.put("Name", ride.getPassengerName());
            jsonObj.put("Origin:", ride.getOrigin());
            jsonObj.put("Destination:", ride.getDestination());
            jsonObj.put("Time", ride.getEndingTime());
            jsonObj.put("Phone number", ride.getPhoneNumber());
            jsonObj.put("email", ride.getPassengerMail());
            jsonObj.put("Credit card", ride.getCreditCard());

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return jsonObj.toString();
    }*/

}






