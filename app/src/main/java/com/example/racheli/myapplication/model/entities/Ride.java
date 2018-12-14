package com.example.racheli.myapplication.model.entities;

import com.google.firebase.database.Exclude;

import java.sql.Time;
import java.util.HashMap;
import java.util.Map;

public class Ride implements java.io.Serializable{
    enum  Status{
        AVAILABLE, ON, DONE
    }

    //private static final long serialVersionUID = 1L;
    //@Exclude
    //private Long ID;
    //private Status status;
    private String origin;
    private String destination;
    //private Time startingTime;
    private Time endingTime;
    private String passengerName;
    private String passengerMail;
    private String phoneNumber;
    private String creditCard;

    public Ride(String origin, String destination, Time endingTime, String passengerName, String passengerMail, String phoneNumber, String creditCard) {
        this.origin = origin;
        this.destination = destination;
        this.endingTime = endingTime;
        this.passengerName = passengerName;
        this.passengerMail = passengerMail;
        this.phoneNumber = phoneNumber;
        this.creditCard = creditCard;
    }


   /* @Exclude
    public Long getID() {
        return ID;
    }
    @Exclude
    public void setID(Long ID) {
        this.ID = ID;
    }*/

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Time getEndingTime() {
        return endingTime;
    }

    public void setEndingTime(Time endingTime) {
        this.endingTime = endingTime;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }

    public String getPassengerMail() {
        return passengerMail;
    }

    public void setPassengerMail(String passengerMail) {
        this.passengerMail = passengerMail;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(String creditCard) {
        this.creditCard = creditCard;
    }

    public Ride(){}
    @Exclude
    public Map<String, Object> toMap(){
        HashMap<String, Object> results = new HashMap<>();
        results.put("Name", getPassengerName());
        results.put("Origin:", getOrigin());
        results.put("Destination:", getDestination());
        results.put("Time", getEndingTime());
        results.put("Phone number", getPhoneNumber());
        results.put("email", getPassengerMail());
        results.put("Credit card", getCreditCard());
        return results;
    }
}
