package com.acnhcompanion.application;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.PorterDuff;
import android.graphics.SweepGradient;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.VideoView;

import com.acnhcompanion.application.ui.main.SectionsPagerAdapter;

public class Journal_Activity extends AppCompatActivity {
    private FrameLayout tab;
    private TabLayout tabs;
    private ViewPager viewPager;
    private VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);

        tabs = findViewById(R.id.tabs);
        tabs.setTabTextColors(ColorStateList.valueOf(Color.BLACK));
        tabs.setupWithViewPager(viewPager);
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.d("TAB", "onTabSelected: " + tab.getText());
                switch(tab.getText().toString()){
                    case "Bugs":
                        tabs.setBackgroundResource(R.drawable.banner_bugs_selected);
                        break;
                    case "Fish":
                        tabs.setBackgroundResource(R.drawable.banner_fish_selected);
                        break;
                    case "Crafting":
                        tabs.setBackgroundResource(R.drawable.banner_fossil_selected);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                switch(tab.getText().toString()){
                    case "Bugs":
                        tabs.setBackgroundResource(R.drawable.banner_bugs_selected);
                        break;
                    case "Fish":
                        tabs.setBackgroundResource(R.drawable.banner_fish_selected);
                        break;
                    case "Crafting":
                        tabs.setBackgroundResource(R.drawable.banner_fossil_selected);
                        break;
                }
            }
        });
    }
}