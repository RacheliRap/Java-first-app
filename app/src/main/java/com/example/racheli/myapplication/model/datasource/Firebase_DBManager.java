package com.example.racheli.myapplication.model.datasource;

import com.example.racheli.myapplication.model.backend.Backend;
import com.example.racheli.myapplication.model.entities.Ride;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class Firebase_DBManager implements Backend {


    @Override
    public void addRide(Ride ride) throws Exception {

    }

    public interface Action<T> {
        void onSuccess(T obj);

        void onFailure(Exception exception);

        void onProgress(String status, double percent);
    }

    public interface NotifyDataChange<T> {
        void OnDataChanged(T obj);

        void onFailure(Exception exception);
    }

    static DatabaseReference StudentsRef;


    static {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        StudentsRef = database.getReference("students");
    }


}