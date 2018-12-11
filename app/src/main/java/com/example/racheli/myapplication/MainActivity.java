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

import java.util.Calendar;
import java.util.Date;

public class MainActivity extends Activity //implements View.OnClickListener
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final EditText chooseTime = findViewById(R.id.etChooseTime);
        chooseTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TimePickerDialog timePickerDialog = new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                        chooseTime.setText(hourOfDay + ":" + minutes);
                    }
                }, 0, 0, true);
                timePickerDialog.show();
            }
        });
    }


   /* private Spinner statusSpinner;
    private EditText nameTextview;
    private EditText locTextview;
    private EditText destTextview;
    private Spinner timeSpinner;
    private Button timeButton;
    private EditText emailTextview;
    private EditText phoneTextview;
    private EditText ccTextview;
    private Button orderButton;
    private Button btnDate;
    private CustomDatePicker datePicker;


    private void findViews()
    {
        statusSpinner = (Spinner)findViewById( R.id.status_spinner );
        nameTextview = (EditText)findViewById( R.id.name_textview );
        locTextview = (EditText)findViewById( R.id.loc_textview );
        destTextview = (EditText)findViewById( R.id.dest_textview );
        timeSpinner = (Spinner)findViewById( R.id.time_spinner );
        timeButton = (Button)findViewById( R.id.time_button );
        emailTextview = (EditText)findViewById( R.id.email_textview );
        phoneTextview = (EditText)findViewById( R.id.phone_textview );
        ccTextview = (EditText)findViewById( R.id.cc_textview ); //cc - for credit card
        orderButton = (Button)findViewById( R.id.order_button );
        btnDate = (Button)findViewById( R.id.btnDate );
        datePicker = (CustomDatePicker)findViewById( R.id.datePicker );

        timeButton.setOnClickListener( this );
        orderButton.setOnClickListener( this );
        btnDate.setOnClickListener( this );
    }

    @Override
    public void onClick(View v) {
        if ( v == timeButton ) {
            // Handle clicks for timeButton
        } else if ( v == orderButton ) {
            // Handle clicks for orderButton
        } else if ( v == btnDate ) {
            // Handle clicks for btnDate
        }
    }*/

}



