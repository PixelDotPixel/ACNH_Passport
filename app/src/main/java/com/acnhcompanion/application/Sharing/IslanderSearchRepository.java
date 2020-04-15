package com.acnhcompanion.application.Sharing;

import android.util.Log;

import com.acnhcompanion.application.Networking.IslandersUtils;
import com.acnhcompanion.application.Networking.Status;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class IslanderSearchRepository implements IslandsAPI_GETAsync.Callback{
    private static final String TAG = "IslanderSearchRepo";
    private MutableLiveData<List<Islander>> islanders;
    private MutableLiveData<Status> loadingStatus;

    public IslanderSearchRepository(){
        islanders = new MutableLiveData<>();
        islanders.setValue(null);
        loadingStatus = new MutableLiveData<>();
        loadingStatus.setValue(Status.SUCCESS);
    }

    public LiveData<List<Islander>> getIslandersOpen(){
        return islanders;
    }

    public LiveData<Status> getStatus(){
        return loadingStatus;
    }

    public void loadIslanderSearch(){
        if(shouldExecuteSearch()){
            String url = IslandersUtils.buildIslandersURi();
            islanders.setValue(null);
            Log.d(TAG, "loadIslanderSearch: " + "Searching db for islands0");
            loadingStatus.setValue(Status.LOADING);
            new IslandsAPI_GETAsync(this).execute(url);
        } else {
            Log.d(TAG, "loadIslanderSearch: " + "Don't need to execute");
        }
    }

    private boolean shouldExecuteSearch(){
        //TODO Come up with execute conditions or caching
        return true;
    }

    @Override
    public void onReturnedResult(List<Islander> islanders) {
        this.islanders.setValue(islanders);
        if(islanders != null){
            for(int i = 0; i < islanders.size(); i++){
                Log.d(TAG, "Successful return from API: " + islanders.get(i).toString());
            }
            loadingStatus.setValue(Status.SUCCESS);
        } else {
            loadingStatus.setValue(Status.ERROR);
        }
    }
}
