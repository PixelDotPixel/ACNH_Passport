package com.acnhcompanion.application.Sharing;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.acnhcompanion.application.R;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Islands_Activity extends AppCompatActivity {
    static String TAG = "Islands_Act";
    private RecyclerView islandersRV;
    IslanderSearchViewModel islanderVM;
    IslanderSearchAdapter islanderSA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.open_islands_api);


        islanderSA = new IslanderSearchAdapter(new ArrayList<Islander>());
        islandersRV = findViewById(R.id.rv_island_list);
        islandersRV.setLayoutManager(new LinearLayoutManager(this));
        islandersRV.setAdapter(islanderSA);

        islanderVM = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(IslanderSearchViewModel.class);
        islanderVM.loadResults();
        islanderVM.getIslanderResults().observe(this, new Observer<List<Islander>>() {
            @Override
            public void onChanged(List<Islander> islanders) {
                if(islanders != null) {
                    Log.d(TAG, "onChanged: " + "Results recieved!");
                    islanderSA.updateIslanders(islanders);
                }
            }
        });
    }
}
