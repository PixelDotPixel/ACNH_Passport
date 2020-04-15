package com.acnhcompanion.application.Fossils;

import java.io.Serializable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Fossil",primaryKeys = {"parentStructure","name"})
public class Fossil implements Serializable {
    @NonNull
    public String name;
    @NonNull
    public String parentStructure;

    public String scientificName;
    public String period;
    public int totalSections;
    public int donatedSections;
    public int bells;
    public int imgID;
    public boolean isMuseum;
    public String notes;

    public Fossil(String parentStructure, String name, int imgID, int bells, int totalSections){
        this.name = name;
        this.parentStructure = parentStructure;
        this.scientificName = "";
        this.period = "";
        this.totalSections = totalSections;
        this.donatedSections = 0;
        this.bells = bells;
        this.imgID = imgID;
        this.isMuseum = false;
        this.notes = "";
    }
}