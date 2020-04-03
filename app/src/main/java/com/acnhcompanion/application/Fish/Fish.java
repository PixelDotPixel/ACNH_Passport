package com.acnhcompanion.application.Fish;

import com.acnhcompanion.application.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Fish implements Serializable {
    @PrimaryKey
    @NonNull
    public int id;

    public String name;
    public String location;
    public String Size;
    public int bells;
    public String timeWindow;
    public String northernSeason;
    public String southernSeason;
    public int imgID;
    public boolean isCaught;
    public boolean isMuseum;
    public String notes;

    public Fish(int id, String name, String location, String Size, int bells, String timeWindow, String northernSeason, String southernSeason, int imgID){
        this.id = id;
        this.name = name;
        this.location = location;
        this.Size = Size;
        this. bells = bells;
        this.timeWindow = timeWindow;
        this.northernSeason = northernSeason;
        this.southernSeason = southernSeason;
        this.imgID = imgID;
    }
}


