package com.example.racheli.myapplication;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
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


import java.util.List;
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
    //Widgets definitions
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

    private ProgressBar addProgressBar;

    /**
     * Find the Views in the layout
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
        locationButton = (ImageButton) findViewById(R.id.imageButtonLocation);

        //final EditText chooseTime = (EditText)findViewById(R.id.etChooseTime);
        addProgressBar = findViewById(R.id.addProgressBar);


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
        initTextChangeListener();
    }

    /**
     * check for input correct using addTextChangedListener class.
     */
    public void initTextChangeListener() {
        View.OnFocusChangeListener onFocusChangeListener = new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    validate();
                }
            }
        };
        destTextview.setOnFocusChangeListener(onFocusChangeListener);
        locTextview.setOnFocusChangeListener(onFocusChangeListener);
        ccTextview.setOnFocusChangeListener(onFocusChangeListener);
        phoneTextview.setOnFocusChangeListener(onFocusChangeListener);
        emailTextview.setOnFocusChangeListener(onFocusChangeListener);
    }

    /**
     * function checks if all the inputs are correct
     */
    private void validate() {
        boolean isAllValid = true;
        //checking correctness of email address
        if (emailTextview.getText().length()>0){
            String email = emailTextview.getText().toString();
            String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
            java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
            java.util.regex.Matcher m = p.matcher(email);
            if (!m.matches()) {
                emailTextview.setError("invalid email address");
                isAllValid = false;
            }
        }
        //checking correctness of phone number
        if (phoneTextview.getText().length()>0){
            if(phoneTextview.getText().toString().length() != 10) {
                phoneTextview.setError("invalid phone number");
                isAllValid = false;
            }
        }
        //checking correctness of creditcard format
        if (ccTextview.getText().length()>0) {
            if(ccTextview.getText().toString().length() != 16) {
                ccTextview.setError("invalid credit card number");
                isAllValid = false;
            }
        }
        //check for location input
        if (locTextview.getText().length()>0) {
            try {
                Geocoder gc = new Geocoder(this);
                if (gc.isPresent()) {
                    List<Address> list = gc.getFromLocationName(locTextview.getText().toString(), 1);
                    Address address = list.get(0);
                    double lat = address.getLatitude();
                    double lng = address.getLongitude();
                    Location locationA = new Location("A");
                    locationA.setLatitude(lat);
                    locationA.setLongitude(lng);
                }
            } catch (Exception e)
            {
                locTextview.setError("invalid Address.");
                isAllValid = false;
            }
        }

        //check for definition input
        if (destTextview.getText().length()>0) {
            try {
                //converts string to locATION
                Geocoder gc = new Geocoder(this);
                if (gc.isPresent()) {
                    List<Address> list = gc.getFromLocationName(locTextview.getText().toString(), 1);
                    Address address = list.get(0);
                    double lat = address.getLatitude();
                    double lng = address.getLongitude();
                    Location locationA = new Location("A");
                    locationA.setLatitude(lat);
                    locationA.setLongitude(lng);
                }
            } catch (Exception e)
            {
                destTextview.setError("invalid Address.");
                isAllValid = false;
            }
        }
        //enables adRide Button only if all the textboxes are filled in
        if(locTextview.getText().length() == 0 || destTextview.getText().length() == 0
                || ccTextview.getText().length() == 0 || phoneTextview.getText().length() == 0 )
        {
            isAllValid = false;
        }
        orderButton.setEnabled(isAllValid);
    }

    @Override
    /**
     * onClick method. Define what will happened at each button/ TextView press
     */
    public void onClick(View view) {
        // create timePickerDialog in respond to click the text view.

        if (view == etChooseTime) {
            //create timePickerDialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                //set timePicker
                public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                    etChooseTime.setText((hourOfDay + ":" + minutes).toString());
                }
            }, 0, 0, true);
            timePickerDialog.show(); // make timepicker to show on ui
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


    /**
     * Create the ride and try to send it to Firebase_DBManager for insert to DB
     */
    public void addRide(){

        Ride ride = getRide();
        orderButton.setEnabled(false);//prevent the user press again, until the data is successfully insert into DB

        try{
            //String jsonObj = quickParse(ride);
            Backend instance = BackendFactory.getInstance();
            instance.addRide(ride, new Action<String>() {
                @Override
                public void onSuccess(String obj) {
                    Toast.makeText(getBaseContext(), "Succeeded" + obj, Toast.LENGTH_LONG).show();
                   resetView();
                }

                @Override
                public void onFailure(Exception exception) {
                    Toast.makeText(getBaseContext(), "Error \n" + exception.getMessage(), Toast.LENGTH_LONG).show();
                   resetView();
                }

                @Override
                public void onProgress(String status, double percent) {
                    if (percent != 100)
                        orderButton.setEnabled(false);
                    addProgressBar.setProgress((int) percent);
                }
            });
        } catch (Exception e) {
            Toast.makeText(getBaseContext(), "Error ", Toast.LENGTH_LONG).show();
            //resetView();
        };
    }

    /**
     * connect to each Wodget on activity_main, and get the data the user typed, in order to initalize Ride
     * @return ride with relevant data
     */
    public Ride getRide(){
        Ride ride = new Ride();
        ride.setPassengerName(this.nameTextview.getText().toString());
        ride.setPassengerMail(this.emailTextview.getText().toString());
        ride.setPhoneNumber(this.phoneTextview.getText().toString());
        ride.setDestination(this.destTextview.getText().toString());
        ride.setOrigin(this.locTextview.getText().toString());
        ride.setCreditCard(this.ccTextview.getText().toString());
        String time = (this.etChooseTime.getText().toString());
        //time = time + ":00";
        //Time timeValue = Time.valueOf(time);
       // String timeOption = (String)this.timeSpinner.getSelectedItem();
       if(time == "Arrival time"){
           ride.setEndingTime(time);
        }
        else
        {
            ride.setStartingTime(time);
        }

        return ride;
    }
    private void resetView() {
        new Handler().postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        addProgressBar.setProgress(0);
                        orderButton.setEnabled(true);
                    }
                },
                1500);
        ccTextview.setText("");
        nameTextview.setText("");
        locTextview.setText("");
        destTextview.setText("");
        emailTextview.setText("");
        phoneTextview.setText("");
        ccTextview.setText("");
        etChooseTime.setText("");
    }


}






