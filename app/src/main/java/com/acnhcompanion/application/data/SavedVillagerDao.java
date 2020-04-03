package com.acnhcompanion.application.data;

import com.acnhcompanion.application.Crafting.Materials;
import com.acnhcompanion.application.Crafting.Recipes;
import com.acnhcompanion.application.Bugs.Bug;
import com.acnhcompanion.application.Fish.Fish;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface SavedVillagerDao {

    @Insert
    void insert(Fish critterData);

    @Insert
    void insertAll(Fish... critterData);

    @Update
    void update(Fish critterData);

    @Delete
    void delete(Fish critterData);

    @Insert
    void insert(Bug critterData);

    @Insert
    void insertAll(Bug... critterData);

    @Update
    void update(Bug critterData);

    @Delete
    void delete(Bug critterData);

    @Insert
    void insert(Recipes critterData);

    @Insert
    void insertAll(Recipes... critterData);

    @Update
    void update(Recipes critterData);

    @Delete
    void delete(Recipes critterData);

    @Insert
    void insert(Materials critterData);

    @Insert
    void insertAll(Materials... critterData);

    @Update
    void update(Materials critterData);

    @Delete
    void delete(Materials critterData);

    @Query("SELECT * FROM Fish")
    public LiveData<List<Fish>> getFishData();

    @Query("SELECT * FROM Bug")
    public LiveData<List<Bug>> getBugData();

    @Query("SELECT * FROM Recipes")
    public LiveData<List<Recipes>> getRecipesData();

    @Query("SELECT * FROM Materials")
    public LiveData<List<Materials>> getMaterialsData();

}
