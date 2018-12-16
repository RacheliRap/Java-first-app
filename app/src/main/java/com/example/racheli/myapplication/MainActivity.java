package com.example.racheli.myapplication;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
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

public class MainActivity extends Activity implements View.OnClickListener
{
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
    private ProgressBar addProgressBar;

    /**
     * Find the Views in the layout
     *
     */
    private void findViews() {
        statusSpinner = (Spinner)findViewById( R.id.status_spinner );
        nameTextview = (EditText)findViewById( R.id.name_textview );
        locTextview = (EditText)findViewById( R.id.loc_textview );
        destTextview = (EditText)findViewById( R.id.dest_textview );
        timeSpinner = (Spinner)findViewById( R.id.time_spinner );
        emailTextview = (EditText)findViewById( R.id.email_textview );
        phoneTextview = (EditText)findViewById( R.id.phone_textview );
        ccTextview = (EditText)findViewById( R.id.cc_textview );
        orderButton = (Button)findViewById( R.id.order_button );
        etChooseTime = findViewById( R.id.etChooseTime );
        addProgressBar = findViewById(R.id.addProgressBar);


        etChooseTime.setOnClickListener(this);
        orderButton.setOnClickListener( this );
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        initTextChangeListener();
        }

    private void initSpinner() {
        timeSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

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

    private void validate() {
        boolean isAllValid = true;
        if (emailTextview.getText().length()>0){
            String email = emailTextview.getText().toString();
            String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
            java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
            java.util.regex.Matcher m = p.matcher(email);
            if (!m.matches()) {
//                            Toast.makeText(getBaseContext(), "invalid email address", Toast.LENGTH_LONG).show();
                //todo add validation
                emailTextview.setError("invalid email address");
                isAllValid = false;
            }
        }
        if (phoneTextview.getText().length()>0){
            //todo add validation
            phoneTextview.setError("invalid phone number");
            isAllValid = false;
        }
        if (ccTextview.getText().length()>0) {
            //todo add validation
            ccTextview.setError("invalid credit card number");
            isAllValid = false;
        }
        //check for location input
        if (locTextview.getText().length()>0) {
            //todo add validation
        }
        //check for definition input
        if (destTextview.getText().length()>0) {
            //todo add validation
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
        if ( view == orderButton ) {
            // Handle clicks for orderButton
            addRide();
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






