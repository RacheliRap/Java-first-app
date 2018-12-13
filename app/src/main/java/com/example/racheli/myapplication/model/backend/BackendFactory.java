package com.example.racheli.myapplication.model.backend;

import com.example.racheli.myapplication.model.datasource.Firebase_DBManager;
import com.example.racheli.myapplication.model.entities.Ride;

public final class BackendFactory {

    static Backend instance = null;

    static public final Backend getInstance(){
        if(instance == null){
            instance = new Firebase_DBManager();
        }
        return instance;
    }
}
