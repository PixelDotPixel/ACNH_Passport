package com.acnhcompanion.application.data;

import java.io.Serializable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class CritterData implements Serializable{
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    public int id;

    @ColumnInfo(name = "critter_type")
    public String critterType;

    @ColumnInfo(name = "is_caught")
    public boolean isCaught;

    @ColumnInfo(name = "log")
    public String log;
}
