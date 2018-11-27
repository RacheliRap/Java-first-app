package com.example.racheli.myapplication.model.entities;

import java.sql.Time;

public class Ride implements java.io.Serializable  {

    private static final long serialVersionUID = 1L;

    private String origin;
    private String destination;
    private Time startingTime;
    private Time endingTime;
    private String passengerName;
    private String passengerNumber;
    private String passengerMail;

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

    public Time getStartingTime() {
        return startingTime;
    }

    public void setStartingTime(Time startingTime) {
        this.startingTime = startingTime;
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

    public String getPassengerNumber() {
        return passengerNumber;
    }

    public void setPassengerNumber(String passengerNumber) {
        this.passengerNumber = passengerNumber;
    }

    public String getPassengerMail() {
        return passengerMail;
    }

    public void setPassengerMail(String passengerMail) {
        this.passengerMail = passengerMail;
    }

    public Ride(String origin, String destination, Time startingTime, Time endingTime, String passengerName, String passengerNumber, String passengerMail) {
        this.origin = origin;
        this.destination = destination;
        this.startingTime = startingTime;
        this.endingTime = endingTime;
        this.passengerName = passengerName;
        this.passengerNumber = passengerNumber;
        this.passengerMail = passengerMail;
    }

    public Ride(){}
}
