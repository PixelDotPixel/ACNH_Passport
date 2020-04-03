package com.acnhcompanion.application.Fish;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.acnhcompanion.application.Bugs.BugActivity;
import com.acnhcompanion.application.Crafting.CraftingActivity;
import com.acnhcompanion.application.R;
import com.acnhcompanion.application.data.CritterDataViewModel;
import com.acnhcompanion.application.ui.main.PlaceholderFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

public class FishActivity extends AppCompatActivity implements FishAdapter.onFishClickedListener, FishAdapter.onFishLongClickedListener {
    private CritterDataViewModel critterDataViewModel;
    ColorDrawable green;
    ColorDrawable tan;
    ColorDrawable blue;

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fish_activity);

        green = new ColorDrawable(Color.parseColor("#96e3af"));
        tan = new ColorDrawable(Color.parseColor("#f4ebe6"));
        blue = new ColorDrawable(Color.parseColor("#c2ffff"));
        critterDataViewModel = new ViewModelProvider(
                this,
                new ViewModelProvider.AndroidViewModelFactory(getApplication())
        ).get(CritterDataViewModel.class);

        final GridView fishView = findViewById(R.id.gv_fish_list);
        fishView.setBackgroundColor(blue.getColor());

        final FishAdapter fishAdapter = new FishAdapter(getApplication(), this, this);
        fishView.setAdapter(fishAdapter);
        critterDataViewModel.getFish().observe(this, new Observer<List<Fish>>(){
            @Override
            public void onChanged(List<Fish> fishes){
                fishAdapter.updateFishAdpater(fishes);
            }
        });

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent;
                switch(item.getItemId()){
                    case R.id.navigation_bugs:
                        intent = new Intent(getApplication(), BugActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                        break;
                    case R.id.navigation_fish:
                        break;
                    case R.id.navigation_crafting:
                        intent = new Intent(getApplication(), CraftingActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                        break;
                }
                return false;
            }
        });
    }

    @Override
    public void onFishClicked(Fish fish) {
        final Fish tFish = fish;
        TextView critterName;
        ImageView critterIcon;
        TextView critterValue;
        TextView critterCatchConditions;
        TextView critterCatchTime;
        TextView critterCatchSeason;
        CheckBox critterCaught;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final View customLayout = getLayoutInflater().inflate(R.layout.critter_alert_dialog, null);
        customLayout.setBackgroundColor(Color.TRANSPARENT);
        builder.setView(customLayout);

        critterName = customLayout.findViewById(R.id.tv_critter_name_alert);
        critterName.setText(fish.name);
        critterName.setTextSize(20);

        critterIcon = customLayout.findViewById(R.id.iv_critter_detail_view_alert);
        //critterIcon.setBackgroundColor(Color.BLACK);
        critterIcon.setImageResource(fish.imgID);

        critterValue = customLayout.findViewById(R.id.tv_critter_value_alert);
        critterValue.setText("Bells: "+fish.bells);

        critterCatchConditions = customLayout.findViewById(R.id.tv_critter_catch_location_alert);
        critterCatchConditions.setText("Location: " + fish.location);

        critterCatchTime = customLayout.findViewById(R.id.tv_critter_catch_time_alert);
        critterCatchTime.setText("Time: " + fish.timeWindow);

        critterCatchSeason = customLayout.findViewById(R.id.tv_critter_catch_season_alert);
        critterCatchSeason.setText("Season: " + fish.northernSeason);

        critterCaught = customLayout.findViewById(R.id.cb_critter_caught_alert);
        critterCaught.setChecked(fish.isCaught);
        critterCaught.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tFish.isCaught = !tFish.isCaught;
                critterDataViewModel.updateCritterData(tFish);
            }
        });

        AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    @Override
    public void onFishLongClicked(Fish fish){
        fish.isCaught = true;
        critterDataViewModel.updateCritterData(fish);
    }
}
