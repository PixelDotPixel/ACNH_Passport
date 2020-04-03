package com.acnhcompanion.application.Bugs;

import java.io.Serializable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Bug implements Serializable  {
    @PrimaryKey
    @NonNull
    public int id;

    public String name;
    public String location;
    public int bells;
    public String timeWindow;
    public String northernSeason;
    public String southernSeason;
    public int imgID;
    public boolean isMuseum;
    public boolean isCaught;
    public String notes;

    public Bug(int id, String name, String location, int bells, String timeWindow, String northernSeason, String southernSeason, int imgID){
        this.id = id;
        this.name = name;
        this.location = location;
        this. bells = bells;
        this.timeWindow = timeWindow;
        this.northernSeason = northernSeason;
        this.southernSeason = southernSeason;
        this.imgID = imgID;
    }
}
