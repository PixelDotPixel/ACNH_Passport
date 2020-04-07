package com.acnhcompanion.application.Crafting;

import java.io.Serializable;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Recipes implements Serializable {
    @PrimaryKey
    @NonNull
    public String rName;

    public int IMGid;
    public String materialNames;
    public String materialCounts;
    public boolean isCrafted;
    public String notes;
    //public String category;

    public Recipes(String rName, int IMGid, String materialNames, String materialCounts/*, String category*/){
        this.rName = rName;
        this.IMGid = IMGid;
        this.materialNames = materialNames;
        this.materialCounts = materialCounts;
        //this.category = category;
    }
}
