package model.BackEnd;

import com.example.racheli.myapplication.model.entities.Ride;

import java.util.ArrayList;

public interface backend
{
     void addTravel(Ride travel) throws Exception;
     void deleteTravel(Ride travel) throws Exception;
     void updateTravel(Ride travel) throws Exception;

     ArrayList<Ride> getTravelList() throws Exception ;
}
