package com.example.racheli.myapplication.model.datasource;

import android.support.annotation.NonNull;

import com.example.racheli.myapplication.model.backend.Backend;
import com.example.racheli.myapplication.model.entities.Ride;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class Firebase_DBManager implements Backend{


    //public interface Action<T> {
      //  void onSuccess(T obj);
//
  //      void onFailure(Exception exception);

    //    void onProgress(String status, double percent);
    //}

    public interface NotifyDataChange<T> {
        void OnDataChanged(T obj);

        void onFailure(Exception exception);
    }

    static DatabaseReference RideRef;


    static {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        RideRef = database.getReference("students");
    }
    @Override
    public void addRide(final Ride ride, final Action<Long> action) throws Exception
    {
        //String key = Ride.getID().toString();
        RideRef.push().setValue(ride).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                action.onSuccess(ride.getID());
                action.onProgress("upload ride data", 100);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                action.onFailure(e);
                action.onProgress("error upload ride data", 100);

            }
        });
    }


}