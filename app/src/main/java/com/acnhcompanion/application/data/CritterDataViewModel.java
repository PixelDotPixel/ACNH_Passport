package com.acnhcompanion.application.data;

import android.app.Application;

import com.acnhcompanion.application.Crafting.Materials;
import com.acnhcompanion.application.Crafting.Recipes;
import com.acnhcompanion.application.Bugs.Bug;
import com.acnhcompanion.application.Fish.Fish;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class CritterDataViewModel extends AndroidViewModel {
    private LiveData<List<CritterData>> critterEntries;
    private CritterDataRepo critterDataRepo;

    public CritterDataViewModel(@NonNull Application application) {
        super(application);
        critterDataRepo = new CritterDataRepo(application);
    }


    //----Fish opperations
    public void insertCritterData(Fish critterData){
        critterDataRepo.insertFishData(critterData);
    }

    public void updateCritterData(Fish critterData){
        critterDataRepo.updateFishData(critterData);
    }

    public void deleteCritterData(Fish critterData){
        critterDataRepo.deleteFishData(critterData);
    }

    public LiveData<List<Fish>> getFish(){
        return critterDataRepo.getFishData();
    }
    //----End Fish Opperations

    //----Bug opperations
    public void insertCritterData(Bug critterData){
        critterDataRepo.insertBugData(critterData);
    }

    public void updateCritterData(Bug critterData){
        critterDataRepo.updateBugData(critterData);
    }

    public void deleteCritterData(Bug critterData){
        critterDataRepo.deleteBugData(critterData);
    }

    public LiveData<List<Bug>> getBugs(){
        return critterDataRepo.getBugData();
    }
    //----End Bug Opperations

    //----Recipes opperations
    public void insertCritterData(Recipes critterData){
        critterDataRepo.insertRecipesData(critterData);
    }

    public void updateCritterData(Recipes critterData){
        critterDataRepo.updateRecipesData(critterData);
    }

    public void deleteCritterData(Recipes critterData){
        critterDataRepo.deleteRecipesData(critterData);
    }

    public LiveData<List<Recipes>> getRecipes(){
        return critterDataRepo.getRecipesData();
    }
    //----End Recipes Opperations

    //----Materials opperations
    public void insertCritterData(Materials critterData){
        critterDataRepo.insertMaterialsData(critterData);
    }

    public void updateCritterData(Materials critterData){
        critterDataRepo.updateMaterialsData(critterData);
    }

    public void deleteCritterData(Materials critterData){
        critterDataRepo.deleteMaterialsData(critterData);
    }

    public LiveData<List<Materials>> getMaterials(){
        return critterDataRepo.getMaterialsData();
    }
    //----End Materials Opperations


}
