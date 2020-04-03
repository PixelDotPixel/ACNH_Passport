package com.acnhcompanion.application.ui.main;

import com.acnhcompanion.application.data.CritterData;
import com.acnhcompanion.application.data.CritterDataRepo;

import java.util.List;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

public class PageViewModel extends ViewModel {
    private LiveData<List<CritterData>> critterEntries;
    private CritterDataRepo critterDataRepo;

    private MutableLiveData<Integer> mIndex = new MutableLiveData<>();
    private LiveData<String> mText = Transformations.map(mIndex, new Function<Integer, String>() {
        @Override
        public String apply(Integer input) {
            return input.toString();
        }
    });

    public void setIndex(int index) {
        mIndex.setValue(index);
    }

    public LiveData<String> getText() {
        return mText;
    }
}