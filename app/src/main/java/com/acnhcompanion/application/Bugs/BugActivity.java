package com.acnhcompanion.application.Bugs;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.acnhcompanion.application.Crafting.CraftingActivity;
import com.acnhcompanion.application.Fish.FishActivity;
import com.acnhcompanion.application.Fossils.FossilActivity;
import com.acnhcompanion.application.R;
import com.acnhcompanion.application.data.CritterDataViewModel;
import com.acnhcompanion.application.ui.main.PlaceholderFragment;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

public class BugActivity extends AppCompatActivity implements BugAdapter.onBugClickedListener, BugAdapter.onBugLongClickedListener {
    private CritterDataViewModel critterDataViewModel;
    SharedPreferences sharedPreferences;

    ColorDrawable green;
    ColorDrawable tan;
    ColorDrawable blue;
    ColorDrawable greenAccent;

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bug_activity);
        sharedPreferences = getSharedPreferences("VillagerPrefs", MODE_PRIVATE);

        green = new ColorDrawable(Color.parseColor("#96e3af"));
        tan = new ColorDrawable(Color.parseColor("#f4ebe6"));
        blue = new ColorDrawable(Color.parseColor("#c2ffff"));
        greenAccent = new ColorDrawable(Color.parseColor("#217d3e"));
        critterDataViewModel = new ViewModelProvider(
                this,
                new ViewModelProvider.AndroidViewModelFactory(getApplication())
        ).get(CritterDataViewModel.class);

        final GridView bugView = findViewById(R.id.gv_bug_list);
        bugView.setBackgroundColor(green.getColor());

        final BugAdapter bugAdapter = new BugAdapter(getApplication(), this, this);
        bugView.setAdapter(bugAdapter);
        critterDataViewModel.getBugs().observe(this, new Observer<List<Bug>>(){
            @Override
            public void onChanged(List<Bug> Bugs){
                bugAdapter.updateBugAdapter(Bugs);
            }
        });

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setBackgroundColor(greenAccent.getColor());
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent;
                switch(item.getItemId()){
                    case R.id.navigation_bugs:
                        break;
                    case R.id.navigation_fish:
                        intent = new Intent(getApplication(), FishActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                        break;
                    case R.id.navigation_crafting:
                        intent = new Intent(getApplication(), FossilActivity.class);
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
    public void onBugClicked(Bug bug) {
        final Bug tBug = bug;
        TextView critterName;
        ImageView critterIcon;
        TextView critterValue;
        TextView critterCatchConditions;
        TextView critterCatchTime;
        TextView critterCatchSeason;
        CheckBox critterCaught;
        CheckBox critterDonated;

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final View customLayout = getLayoutInflater().inflate(R.layout.critter_alert_dialog, null);
        builder.setView(customLayout);

        critterName = customLayout.findViewById(R.id.tv_critter_name_alert);
        critterName.setText(bug.name);

        critterIcon = customLayout.findViewById(R.id.iv_critter_detail_view_alert);
        //critterIcon.setBackgroundColor(Color.BLACK);
        critterIcon.setImageResource(bug.imgID);

        critterValue = customLayout.findViewById(R.id.tv_critter_value_alert);
        critterValue.setText("Bells: "+bug.bells);

        critterCatchConditions = customLayout.findViewById(R.id.tv_critter_catch_location_alert);
        critterCatchConditions.setText("Location: " + bug.location);

        critterCatchTime = customLayout.findViewById(R.id.tv_critter_catch_time_alert);
        critterCatchTime.setText("Time: " + bug.timeWindow);

        critterCatchSeason = customLayout.findViewById(R.id.tv_critter_catch_season_alert);
        critterCatchSeason.setText("Season: " + bug.northernSeason);

        critterCaught = customLayout.findViewById(R.id.cb_critter_caught_alert);
        critterCaught.setChecked(bug.isCaught);
        critterCaught.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tBug.isCaught = !tBug.isCaught;
                critterDataViewModel.updateCritterData(tBug);
            }
        });

        critterDonated = customLayout.findViewById(R.id.cb_critter_donated_alert);
        critterDonated.setChecked(bug.isMuseum);
        critterDonated.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tBug.isMuseum = !tBug.isMuseum;
                critterDataViewModel.updateCritterData(tBug);
            }
        });

        AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    @Override
    public void onBugLongClicked(Bug bug){
        bug.isCaught = true;
        critterDataViewModel.updateCritterData(bug);
    }
}
