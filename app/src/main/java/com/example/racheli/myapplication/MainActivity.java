package com.example.racheli.myapplication;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.racheli.myapplication.model.entities.Ride;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends Activity implements View.OnClickListener
{

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

    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2018-12-12 20:15:04 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
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
        //final EditText chooseTime = (EditText)findViewById(R.id.etChooseTime);

        etChooseTime.setOnClickListener(this);

        orderButton.setOnClickListener( this );
    }

    /**
     * Handle button click events<br />
     * <br />
     * Auto-created on 2018-12-12 20:15:04 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();

        };
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
        if ( view == orderButton ) {
            // Handle clicks for orderButton
            addRide();
        }

    }

    public void addRide(){
        Ride ride = getRide();


    }

    public Ride getRide(){
        Ride ride = new Ride();
        ride.setPassengerName(this.nameTextview.getText().toString());
        ride.setPassengerMail(this.emailTextview.getText().toString());
        ride.setPassengerNumber(this.phoneTextview.getText().toString());
        ride.setDestination(this.destTextview.getText().toString());
        ride.setOrigin(this.locTextview.getText().toString());

        String time = this.etChooseTime.getText().toString();
        time = time + ":00";
        Time timeValue = Time.valueOf(time);
        String timeOption = (String)this.timeSpinner.getSelectedItem();
        if(timeOption == "Departure time"){
            ride.setStartingTime(timeValue);
        }
        else
        {
           ride.setEndingTime(timeValue);
        }
        
        return ride;
    }

}



