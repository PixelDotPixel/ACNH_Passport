package com.acnhcompanion.application;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.acnhcompanion.application.Fish.Fish;
import com.acnhcompanion.application.data.CritterData;
import com.acnhcompanion.application.data.CritterDataViewModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

public class CritterDetailView extends AppCompatActivity {
    public static final String EXTRA_FISH = "Fish";
    private CritterDataViewModel critterDataViewModel;

    private TextView critterName;
    private ImageView critterIcon;
    private TextView critterValue;
    private TextView critterCatchConditions;
    private CheckBox critterCaught;

    private Fish fish;
    private CritterData critterData;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_detail_view);

        critterData= new CritterData();
        critterDataViewModel = new ViewModelProvider(
                this,
                new ViewModelProvider.AndroidViewModelFactory(getApplication())
        ).get(CritterDataViewModel.class);

        Intent intent = getIntent();
        if(intent != null && intent.hasExtra(EXTRA_FISH)) {
            fish = (Fish)intent.getSerializableExtra(EXTRA_FISH);
            updateFishEntry(fish);

            critterName = findViewById(R.id.tv_critter_name);
            critterName.setText(fish.name);

            critterIcon = findViewById(R.id.iv_critter_detail_view);
            critterIcon.setBackgroundColor(Color.BLACK);
            critterIcon.setImageResource(fish.imgID);

            critterValue = findViewById(R.id.tv_critter_value);
            critterValue.setText("Bells: "+fish.bells);

            critterCatchConditions = findViewById(R.id.tv_critter_catch_conditions);
            critterCatchConditions.setText("Location: " + fish.location + "\n" + "Time: " + fish.timeWindow + "\n Season: " + fish.northernSeason);

            critterCaught = findViewById(R.id.cb_critter_caught);
            critterCaught.setChecked(fish.isCaught);
        }
    }

    public void updateFishEntry(Fish fish){
        critterData.critterType="Fish";
        critterData.id=fish.id;
        critterData.isCaught=false;
    }

    public void onCompletionStatusClicked(View view){
        fish.isCaught = !fish.isCaught;
        critterDataViewModel.updateCritterData(fish);
    }

}
