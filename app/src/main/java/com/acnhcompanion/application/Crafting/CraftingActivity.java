package com.acnhcompanion.application.Crafting;

import android.app.ActivityManager;
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

public class CraftingActivity extends AppCompatActivity implements CraftingAdapter.onRecipeClickedListener, CraftingAdapter.onRecipeLongClickedListener{
    private CritterDataViewModel critterDataViewModel;
    ColorDrawable green;
    ColorDrawable tan;
    ColorDrawable blue;

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crafting_activity);

        green = new ColorDrawable(Color.parseColor("#96e3af"));
        tan = new ColorDrawable(Color.parseColor("#f4ebe6"));
        blue = new ColorDrawable(Color.parseColor("#c2ffff"));
        critterDataViewModel = new ViewModelProvider(
                this,
                new ViewModelProvider.AndroidViewModelFactory(getApplication())
        ).get(CritterDataViewModel.class);

        final RecyclerView fossilView = findViewById(R.id.rv_crafting_list);
        fossilView.setBackgroundColor(tan.getColor());
        fossilView.setLayoutManager(new GridLayoutManager(this, 1));

        final CraftingAdapter craftingAdapter = new CraftingAdapter(this, new ArrayList<Recipes>(), this, this);
        fossilView.setAdapter(craftingAdapter);
        critterDataViewModel.getRecipes().observe(this, new Observer<List<Recipes>>() {
            @Override
            public void onChanged(List<Recipes> recipes) {
                craftingAdapter.updateRecipesAdapter(recipes);
            }
        });

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent;
                switch(item.getItemId()){
                    case R.id.navigation_bugs:
                        intent = new Intent(CraftingActivity.this, BugActivity.class);
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
    public void onRecipeClicked(Recipes recipe) {

    }

    @Override
    public void onRecipeLongClicked(Recipes recipe) {
        recipe.isCrafted = true;
        critterDataViewModel.updateCritterData(recipe);
    }

}
