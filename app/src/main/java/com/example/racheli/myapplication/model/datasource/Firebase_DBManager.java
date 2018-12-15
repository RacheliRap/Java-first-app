package com.example.racheli.myapplication.model.datasource;

import android.support.annotation.NonNull;

import com.example.racheli.myapplication.model.backend.Backend;
import com.example.racheli.myapplication.model.entities.Ride;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    static DatabaseReference rideRef;


    static {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        rideRef = database.getReference("rides");
    }

    /**
     * Try to insert Ride with the data from the user to firebase
     * @param ride - the ride initialized with user's data.
     * @param action - implementation of Action interface, which defines what will happen in the insertion of the data
     * @throws Exception
     */
    @Override
    public void addRide(final Ride ride, final Action<String> action) throws Exception
    {
        final Map<String,Object> dataMap = new HashMap<String, Object>();
        dataMap.put("Ride1" , ride.toMap());
        rideRef.push().setValue(ride).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                action.onSuccess("insert ride");
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