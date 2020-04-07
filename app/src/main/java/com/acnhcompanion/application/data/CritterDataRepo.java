package com.acnhcompanion.application.data;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;
import android.database.sqlite.SQLiteConstraintException;

import com.acnhcompanion.application.Crafting.Materials;
import com.acnhcompanion.application.Crafting.Recipes;
import com.acnhcompanion.application.Bugs.Bug;
import com.acnhcompanion.application.Fossils.Fossil;
import com.acnhcompanion.application.ui.main.PlaceholderFragment;
import com.acnhcompanion.application.Fish.Fish;

import java.util.List;

import androidx.lifecycle.LiveData;

public class CritterDataRepo {
    private SavedVillagerDao savedVillagerDao;

    public CritterDataRepo(Application application){
        AppDatabase db = AppDatabase.getDatabase(application);
        savedVillagerDao = db.savedVillagerDao();
    }

    public void insertFishData(Fish critterData){
        new InsertAsyncTaskCritterData(savedVillagerDao).execute(critterData);
    }

    public void updateFishData(Fish critterData){
        new UpdateAsyncTaskCritterData(savedVillagerDao).execute(critterData);
    }

    public void deleteFishData(Fish critterData){
        new DeleteAsyncTaskCritterData(savedVillagerDao).execute(critterData);
    }

    public LiveData<List<Fish>> getFishData(){
        return savedVillagerDao.getFishData();
    }

    private static class InsertAsyncTaskCritterData extends AsyncTask<Fish, Void, Void>{
        private SavedVillagerDao savedVillagerAsyncDao;
        private String TAG = PlaceholderFragment.class.getSimpleName();

        InsertAsyncTaskCritterData(SavedVillagerDao savedVillagerDao){
            savedVillagerAsyncDao = savedVillagerDao;
        }

        @Override
        protected Void doInBackground(Fish... critterData){
            try {
                savedVillagerAsyncDao.insert(critterData[0]);
            } catch (SQLiteConstraintException e){
                Log.d(TAG, "doInBackground: ERROR with inserting CritterData");
            }
            return null;
        }
    }

    private static class UpdateAsyncTaskCritterData extends AsyncTask<Fish, Void, Void>{
        private SavedVillagerDao savedVillagerAsyncDao;
        private String TAG = PlaceholderFragment.class.getSimpleName();

        UpdateAsyncTaskCritterData(SavedVillagerDao savedVillagerDao){
            savedVillagerAsyncDao = savedVillagerDao;
        }

        @Override
        protected Void doInBackground(Fish... critterData){
            try {
                savedVillagerAsyncDao.update(critterData[0]);
            } catch (SQLiteConstraintException e){
                Log.d(TAG, "doInBackground: ERROR with updating CritterData");
            }
            return null;
        }
    }

    private static class DeleteAsyncTaskCritterData extends AsyncTask<Fish, Void, Void>{
        private SavedVillagerDao savedVillagerAsyncDao;
        private String TAG = PlaceholderFragment.class.getSimpleName();

        DeleteAsyncTaskCritterData(SavedVillagerDao savedVillagerDao) {
            savedVillagerAsyncDao = savedVillagerDao;
        }

        @Override
        protected Void doInBackground(Fish... critterData){
            try {
                savedVillagerAsyncDao.delete(critterData[0]);
            } catch (SQLiteConstraintException e){
                Log.d(TAG, "doInBackground: ERROR with deleting CritterData");
            }
            return null;
        }

    }

    public void insertBugData(Bug critterData){
        new InsertAsyncTaskBugData(savedVillagerDao).execute(critterData);
    }

    public void updateBugData(Bug critterData){
        new UpdateAsyncTaskBugData(savedVillagerDao).execute(critterData);
    }

    public void deleteBugData(Bug critterData){
        new DeleteAsyncTaskBugData(savedVillagerDao).execute(critterData);
    }

    public LiveData<List<Bug>> getBugData(){
        return savedVillagerDao.getBugData();
    }

    private static class InsertAsyncTaskBugData extends AsyncTask<Bug, Void, Void>{
        private SavedVillagerDao savedVillagerAsyncDao;
        private String TAG = PlaceholderFragment.class.getSimpleName();

        InsertAsyncTaskBugData(SavedVillagerDao savedVillagerDao){
            savedVillagerAsyncDao = savedVillagerDao;
        }

        @Override
        protected Void doInBackground(Bug... critterData){
            try {
                savedVillagerAsyncDao.insert(critterData[0]);
            } catch (SQLiteConstraintException e){
                Log.d(TAG, "doInBackground: ERROR with inserting CritterData");
            }
            return null;
        }
    }

    private static class UpdateAsyncTaskBugData extends AsyncTask<Bug, Void, Void>{
        private SavedVillagerDao savedVillagerAsyncDao;
        private String TAG = PlaceholderFragment.class.getSimpleName();

        UpdateAsyncTaskBugData(SavedVillagerDao savedVillagerDao){
            savedVillagerAsyncDao = savedVillagerDao;
        }

        @Override
        protected Void doInBackground(Bug... critterData){
            try {
                savedVillagerAsyncDao.update(critterData[0]);
            } catch (SQLiteConstraintException e){
                Log.d(TAG, "doInBackground: ERROR with updating CritterData");
            }
            return null;
        }
    }

    private static class DeleteAsyncTaskBugData extends AsyncTask<Bug, Void, Void>{
        private SavedVillagerDao savedVillagerAsyncDao;
        private String TAG = PlaceholderFragment.class.getSimpleName();

        DeleteAsyncTaskBugData(SavedVillagerDao savedVillagerDao) {
            savedVillagerAsyncDao = savedVillagerDao;
        }

        @Override
        protected Void doInBackground(Bug... critterData){
            try {
                savedVillagerAsyncDao.delete(critterData[0]);
            } catch (SQLiteConstraintException e){
                Log.d(TAG, "doInBackground: ERROR with deleting CritterData");
            }
            return null;
        }

    }

    public void insertRecipesData(Recipes critterData){
        new InsertAsyncTaskRecipesData(savedVillagerDao).execute(critterData);
    }

    public void updateRecipesData(Recipes critterData){
        new UpdateAsyncTaskRecipesData(savedVillagerDao).execute(critterData);
    }

    public void deleteRecipesData(Recipes critterData){
        new DeleteAsyncTaskRecipesData(savedVillagerDao).execute(critterData);
    }

    public LiveData<List<Recipes>> getRecipesData(){
        return savedVillagerDao.getRecipesData();
    }

    private static class InsertAsyncTaskRecipesData extends AsyncTask<Recipes, Void, Void>{
        private SavedVillagerDao savedVillagerAsyncDao;
        private String TAG = PlaceholderFragment.class.getSimpleName();

        InsertAsyncTaskRecipesData(SavedVillagerDao savedVillagerDao){
            savedVillagerAsyncDao = savedVillagerDao;
        }

        @Override
        protected Void doInBackground(Recipes... critterData){
            try {
                savedVillagerAsyncDao.insert(critterData[0]);
            } catch (SQLiteConstraintException e){
                Log.d(TAG, "doInBackground: ERROR with inserting CritterData");
            }
            return null;
        }
    }

    private static class UpdateAsyncTaskRecipesData extends AsyncTask<Recipes, Void, Void>{
        private SavedVillagerDao savedVillagerAsyncDao;
        private String TAG = PlaceholderFragment.class.getSimpleName();

        UpdateAsyncTaskRecipesData(SavedVillagerDao savedVillagerDao){
            savedVillagerAsyncDao = savedVillagerDao;
        }

        @Override
        protected Void doInBackground(Recipes... critterData){
            try {
                savedVillagerAsyncDao.update(critterData[0]);
            } catch (SQLiteConstraintException e){
                Log.d(TAG, "doInBackground: ERROR with updating CritterData");
            }
            return null;
        }
    }

    private static class DeleteAsyncTaskRecipesData extends AsyncTask<Recipes, Void, Void>{
        private SavedVillagerDao savedVillagerAsyncDao;
        private String TAG = PlaceholderFragment.class.getSimpleName();

        DeleteAsyncTaskRecipesData(SavedVillagerDao savedVillagerDao) {
            savedVillagerAsyncDao = savedVillagerDao;
        }

        @Override
        protected Void doInBackground(Recipes... critterData){
            try {
                savedVillagerAsyncDao.delete(critterData[0]);
            } catch (SQLiteConstraintException e){
                Log.d(TAG, "doInBackground: ERROR with deleting CritterData");
            }
            return null;
        }

    }

    public void insertMaterialsData(Materials critterData){
        new InsertAsyncTaskMaterialsData(savedVillagerDao).execute(critterData);
    }

    public void updateMaterialsData(Materials critterData){
        new UpdateAsyncTaskMaterialsData(savedVillagerDao).execute(critterData);
    }

    public void deleteMaterialsData(Materials critterData){
        new DeleteAsyncTaskMaterialsData(savedVillagerDao).execute(critterData);
    }

    public LiveData<List<Materials>> getMaterialsData(){
        return savedVillagerDao.getMaterialsData();
    }

    private static class InsertAsyncTaskMaterialsData extends AsyncTask<Materials, Void, Void>{
        private SavedVillagerDao savedVillagerAsyncDao;
        private String TAG = PlaceholderFragment.class.getSimpleName();

        InsertAsyncTaskMaterialsData(SavedVillagerDao savedVillagerDao){
            savedVillagerAsyncDao = savedVillagerDao;
        }

        @Override
        protected Void doInBackground(Materials... critterData){
            try {
                savedVillagerAsyncDao.insert(critterData[0]);
            } catch (SQLiteConstraintException e){
                Log.d(TAG, "doInBackground: ERROR with inserting CritterData");
            }
            return null;
        }
    }

    private static class UpdateAsyncTaskMaterialsData extends AsyncTask<Materials, Void, Void>{
        private SavedVillagerDao savedVillagerAsyncDao;
        private String TAG = PlaceholderFragment.class.getSimpleName();

        UpdateAsyncTaskMaterialsData(SavedVillagerDao savedVillagerDao){
            savedVillagerAsyncDao = savedVillagerDao;
        }

        @Override
        protected Void doInBackground(Materials... critterData){
            try {
                savedVillagerAsyncDao.update(critterData[0]);
            } catch (SQLiteConstraintException e){
                Log.d(TAG, "doInBackground: ERROR with updating CritterData");
            }
            return null;
        }
    }

    private static class DeleteAsyncTaskMaterialsData extends AsyncTask<Materials, Void, Void>{
        private SavedVillagerDao savedVillagerAsyncDao;
        private String TAG = PlaceholderFragment.class.getSimpleName();

        DeleteAsyncTaskMaterialsData(SavedVillagerDao savedVillagerDao) {
            savedVillagerAsyncDao = savedVillagerDao;
        }

        @Override
        protected Void doInBackground(Materials... critterData){
            try {
                savedVillagerAsyncDao.delete(critterData[0]);
            } catch (SQLiteConstraintException e){
                Log.d(TAG, "doInBackground: ERROR with deleting CritterData");
            }
            return null;
        }

    }

    public void insertFossilData(Fossil critterData){
        new InsertAsyncTaskFossilData(savedVillagerDao).execute(critterData);
    }

    public void updateFossilData(Fossil critterData){
        new UpdateAsyncTaskFossilData(savedVillagerDao).execute(critterData);
    }

    public void deleteFossilData(Fossil critterData){
        new DeleteAsyncTaskFossilData(savedVillagerDao).execute(critterData);
    }

    public LiveData<List<Fossil>> getFossilData(){
        return savedVillagerDao.getFossilData();
    }

    private static class InsertAsyncTaskFossilData extends AsyncTask<Fossil, Void, Void>{
        private SavedVillagerDao savedVillagerAsyncDao;
        private String TAG = PlaceholderFragment.class.getSimpleName();

        InsertAsyncTaskFossilData(SavedVillagerDao savedVillagerDao){
            savedVillagerAsyncDao = savedVillagerDao;
        }

        @Override
        protected Void doInBackground(Fossil... critterData){
            try {
                savedVillagerAsyncDao.insert(critterData[0]);
            } catch (SQLiteConstraintException e){
                Log.d(TAG, "doInBackground: ERROR with inserting CritterData");
            }
            return null;
        }
    }

    private static class UpdateAsyncTaskFossilData extends AsyncTask<Fossil, Void, Void>{
        private SavedVillagerDao savedVillagerAsyncDao;
        private String TAG = PlaceholderFragment.class.getSimpleName();

        UpdateAsyncTaskFossilData(SavedVillagerDao savedVillagerDao){
            savedVillagerAsyncDao = savedVillagerDao;
        }

        @Override
        protected Void doInBackground(Fossil... critterData){
            try {
                savedVillagerAsyncDao.update(critterData[0]);
            } catch (SQLiteConstraintException e){
                Log.d(TAG, "doInBackground: ERROR with updating CritterData");
            }
            return null;
        }
    }

    private static class DeleteAsyncTaskFossilData extends AsyncTask<Fossil, Void, Void>{
        private SavedVillagerDao savedVillagerAsyncDao;
        private String TAG = PlaceholderFragment.class.getSimpleName();

        DeleteAsyncTaskFossilData(SavedVillagerDao savedVillagerDao) {
            savedVillagerAsyncDao = savedVillagerDao;
        }

        @Override
        protected Void doInBackground(Fossil... critterData){
            try {
                savedVillagerAsyncDao.delete(critterData[0]);
            } catch (SQLiteConstraintException e){
                Log.d(TAG, "doInBackground: ERROR with deleting CritterData");
            }
            return null;
        }

    }


}
