package com.acnhcompanion.application.data;

import com.acnhcompanion.application.Crafting.Materials;
import com.acnhcompanion.application.Crafting.Recipes;
import com.acnhcompanion.application.Bugs.Bug;
import com.acnhcompanion.application.Fish.Fish;
import com.acnhcompanion.application.Fossils.Fossil;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface SavedVillagerDao {

    //----FISH START----
    @Insert
    void insert(Fish critterData);

    @Insert
    void insertAll(Fish... critterData);

    @Update
    void update(Fish critterData);

    @Delete
    void delete(Fish critterData);

    @Query("SELECT * FROM Fish")
    public LiveData<List<Fish>> getFishData();
    //-----FISH END-----

    //----BUGS START----
    @Insert
    void insert(Bug critterData);

    @Insert
    void insertAll(Bug... critterData);

    @Update
    void update(Bug critterData);

    @Delete
    void delete(Bug critterData);

    @Query("SELECT * FROM Bug")
    public LiveData<List<Bug>> getBugData();
    //-----BUGS END-----

    //----RECIPES START----
    @Insert
    void insert(Recipes critterData);

    @Insert
    void insertAll(Recipes... critterData);

    @Update
    void update(Recipes critterData);

    @Delete
    void delete(Recipes critterData);

    @Query("SELECT * FROM Recipes")
    public LiveData<List<Recipes>> getRecipesData();
    //----RECIPES END----

    //----MATERIALS START----
    @Insert
    void insert(Materials critterData);

    @Insert
    void insertAll(Materials... critterData);

    @Update
    void update(Materials critterData);

    @Delete
    void delete(Materials critterData);

    @Query("SELECT * FROM Materials")
    public LiveData<List<Materials>> getMaterialsData();
    //----MATERIALS END----

    //----FOSSILS START----
    @Insert
    void insert(Fossil critterData);

    @Insert
    void insertAll(Fossil... critterData);

    @Update
    void update(Fossil critterData);

    @Delete
    void delete(Fossil critterData);

    @Query("SELECT * FROM Fossil")
    public LiveData<List<Fossil>> getFossilData();
    //----FOSSILS END----








}
