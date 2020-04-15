package com.acnhcompanion.application.Fossils;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;

import com.acnhcompanion.application.Bugs.BugActivity;
import com.acnhcompanion.application.Fish.FishActivity;
import com.acnhcompanion.application.R;
import com.acnhcompanion.application.data.CritterDataViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class FossilActivity extends AppCompatActivity implements FossilAdapter.onFossilLongClickedListener {
    private CritterDataViewModel critterDataViewModel;
    ColorDrawable green;
    ColorDrawable tan;
    ColorDrawable blue;
    ColorDrawable tanAccent;

    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fossil_activity);
        green = new ColorDrawable(Color.parseColor("#96e3af"));
        tan = new ColorDrawable(Color.parseColor("#f4ebe6"));
        blue = new ColorDrawable(Color.parseColor("#c2ffff"));
        tanAccent = new ColorDrawable(Color.parseColor("#c29075"));
        critterDataViewModel = new ViewModelProvider(
                this,
                new ViewModelProvider.AndroidViewModelFactory(getApplication())
        ).get(CritterDataViewModel.class);

        final RecyclerView fossilView = findViewById(R.id.rv_crafting_list);
        fossilView.setBackgroundColor(tan.getColor());
        fossilView.setLayoutManager(new GridLayoutManager(this, 1));
        final FossilAdapter fossilAdapter = new FossilAdapter(this, new ArrayList<Fossil>(), this);
        fossilView.setAdapter(fossilAdapter);
        critterDataViewModel.getFossilData().observe(this, new Observer<List<Fossil>>() {
            @Override
            public void onChanged(List<Fossil> fossils) {
                fossilAdapter.updateFossilAdapter(fossils);
            }
        });

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setBackgroundColor(tanAccent.getColor());
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
                        intent = new Intent(getApplication(), FishActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                        break;
                    case R.id.navigation_crafting:
                        break;
                }
                return false;
            }
        });
    }

    @Override
    public void onFossilLongClicked(Fossil fossil){
        fossil.isMuseum = !fossil.isMuseum;
        critterDataViewModel.updateCritterData(fossil);
    }


}
