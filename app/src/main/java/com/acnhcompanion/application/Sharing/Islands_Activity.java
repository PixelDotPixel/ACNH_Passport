package com.acnhcompanion.application.Sharing;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.acnhcompanion.application.Networking.Status;
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
    private RecyclerView islanderRV;
    private ProgressBar islanderPB;
    private TextView islanderTV_Error;

    IslanderSearchViewModel islanderVM;
    IslanderSearchAdapter islanderSA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.open_islands_api);


        islanderSA = new IslanderSearchAdapter(new ArrayList<Islander>());
        islanderRV = findViewById(R.id.rv_island_list);
        islanderRV.setLayoutManager(new LinearLayoutManager(this));
        islanderRV.setAdapter(islanderSA);
        islanderRV.setVisibility(View.INVISIBLE);

        islanderPB = findViewById(R.id.pb_status_bar);
        islanderPB.setBackgroundColor(Color.WHITE);
        islanderPB.setVisibility(View.VISIBLE);

        islanderTV_Error = findViewById(R.id.tv_error_box_sharing);
        islanderTV_Error.setVisibility(View.INVISIBLE);
        islanderTV_Error.setText("We have encountered an issue!");

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
        islanderVM.getLoadingStatus().observe(this, new Observer<Status>() {
            @Override
            public void onChanged(Status status) {
                if(status == Status.LOADING){
                    islanderPB.setVisibility(View.VISIBLE);
                    islanderRV.setVisibility(View.INVISIBLE);
                    islanderTV_Error.setVisibility(View.INVISIBLE);
                } else if(status == Status.SUCCESS){
                    islanderPB.setVisibility(View.INVISIBLE);
                    islanderRV.setVisibility(View.VISIBLE);
                    islanderTV_Error.setVisibility(View.INVISIBLE);
                } else if(status == Status.ERROR){
                    islanderPB.setVisibility(View.INVISIBLE);
                    islanderRV.setVisibility(View.INVISIBLE);
                    islanderTV_Error.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}
