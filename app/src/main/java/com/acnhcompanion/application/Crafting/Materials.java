package com.acnhcompanion.application.Crafting;

import java.io.Serializable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Materials implements Serializable {
    @PrimaryKey
    @NonNull
    public String name;
    public int inInventory;
    public int IMGid;

    public Materials(String name, int IMGid){
        this.name = name;
        this.IMGid = IMGid;
    }
}
