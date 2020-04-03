package com.acnhcompanion.application.ui.main;

import com.acnhcompanion.application.Crafting.Recipes;
import com.acnhcompanion.application.CraftingAdapter;
import com.acnhcompanion.application.data.CritterDataViewModel;

import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.acnhcompanion.application.Bugs.BugAdapter;
import com.acnhcompanion.application.Bugs.Bug;
import com.acnhcompanion.application.Fish.Fish;
import com.acnhcompanion.application.Fish.FishAdapter;
import com.acnhcompanion.application.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment implements FishAdapter.onFishClickedListener, FishAdapter.onFishLongClickedListener,
        BugAdapter.onBugClickedListener, BugAdapter.onBugLongClickedListener,
        CraftingAdapter.onRecipeClickedListener, CraftingAdapter.onRecipeLongClickedListener{

    private static final String ARG_SECTION_NUMBER = "section_number";
    private PageViewModel pageViewModel;
    private CritterDataViewModel critterDataViewModel;
    private TabLayout tabLayout;

    ColorDrawable green;
    ColorDrawable tan;
    ColorDrawable blue;



    public static PlaceholderFragment newInstance(int index) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel.class);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        pageViewModel.setIndex(index);
        critterDataViewModel = new ViewModelProvider(
                this,
                new ViewModelProvider.AndroidViewModelFactory(getActivity().getApplication())
        ).get(CritterDataViewModel.class);
        green = new ColorDrawable(Color.parseColor("#96e3af"));
        tan = new ColorDrawable(Color.parseColor("#f4ebe6"));
        blue = new ColorDrawable(Color.parseColor("#c2ffff"));
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_main, container, false);


        final GridView bugView = root.findViewById(R.id.gv_bug_list);
        bugView.setBackgroundColor(green.getColor());
        //bugView.setLayoutManager(new GridLayoutManager(getActivity(), 5));
        final BugAdapter bugAdapter = new BugAdapter(getContext(), PlaceholderFragment.this, PlaceholderFragment.this);
        bugView.setAdapter(bugAdapter);
        critterDataViewModel.getBugs().observe(getViewLifecycleOwner(), new Observer<List<Bug>>(){
            @Override
            public void onChanged(List<Bug> Bugs){
                bugAdapter.updateBugAdapter(Bugs);
            }
        });

        final GridView fishView = root.findViewById(R.id.gv_fish_list);
        fishView.setBackgroundColor(blue.getColor());
        final FishAdapter fishAdapter = new FishAdapter(getContext(), PlaceholderFragment.this, PlaceholderFragment.this);
        fishView.setAdapter(fishAdapter);
        critterDataViewModel.getFish().observe(getViewLifecycleOwner(), new Observer<List<Fish>>(){
            @Override
            public void onChanged(List<Fish> fishes){
                fishAdapter.updateFishAdpater(fishes);
            }
        });

        final RecyclerView fossilView = root.findViewById(R.id.rv_crafting_list);
        fossilView.setBackgroundColor(tan.getColor());
        fossilView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        final CraftingAdapter craftingAdapter = new CraftingAdapter(getContext(), new ArrayList<Recipes>(), PlaceholderFragment.this, PlaceholderFragment.this);
        fossilView.setAdapter(craftingAdapter);
        critterDataViewModel.getRecipes().observe(getViewLifecycleOwner(), new Observer<List<Recipes>>() {
            @Override
            public void onChanged(List<Recipes> recipes) {
                craftingAdapter.updateRecipesAdapter(recipes);
            }
        });

        //final GridView fossilView = root.findViewById(R.id.gv_fossil_list);
        /*FossilAdapter fossilAdapter = new FossilAdapter(root.getContext(), fossils);
        fossilView.setAdapter(fossilAdapter);*/

        pageViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                switch (s){
                    case "0":
                        bugView.setVisibility(View.VISIBLE);
                        fishView.setVisibility(View.INVISIBLE);
                        fossilView.setVisibility(View.INVISIBLE);
                        break;
                    case "1":
                        bugView.setVisibility(View.INVISIBLE);
                        fishView.setVisibility(View.VISIBLE);
                        fossilView.setVisibility(View.INVISIBLE);

                        break;
                    case "2":
                        bugView.setVisibility(View.INVISIBLE);
                        fishView.setVisibility(View.INVISIBLE);
                        fossilView.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });
        return root;
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
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
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

    @Override
    public void onBugClicked(Bug fish) {
        final Bug tFish = fish;
        TextView critterName;
        ImageView critterIcon;
        TextView critterValue;
        TextView critterCatchConditions;
        TextView critterCatchTime;
        TextView critterCatchSeason;
        CheckBox critterCaught;
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final View customLayout = getLayoutInflater().inflate(R.layout.critter_alert_dialog, null);
        builder.setView(customLayout);

        critterName = customLayout.findViewById(R.id.tv_critter_name_alert);
        critterName.setText(fish.name);

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
    public void onBugLongClicked(Bug bug){
        bug.isCaught = true;
        critterDataViewModel.updateCritterData(bug);
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