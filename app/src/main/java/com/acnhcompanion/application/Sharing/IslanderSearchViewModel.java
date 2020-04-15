package com.acnhcompanion.application.Sharing;

import com.acnhcompanion.application.Networking.Status;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class IslanderSearchViewModel extends ViewModel {
    private IslanderSearchRepository islanderRepo;
    private LiveData<List<Islander>> islanders;
    private LiveData<Status> loadingStatus;

    public IslanderSearchViewModel(){
        islanderRepo = new IslanderSearchRepository();
        islanders = islanderRepo.getIslandersOpen();
        loadingStatus = islanderRepo.getStatus();
    }

    public void loadResults(){
        islanderRepo.loadIslanderSearch();
    }

    public LiveData<List<Islander>> getIslanderResults(){
        return islanders;
    }

    public LiveData<Status> getLoadingStatus() {
        return loadingStatus;
    }
}
