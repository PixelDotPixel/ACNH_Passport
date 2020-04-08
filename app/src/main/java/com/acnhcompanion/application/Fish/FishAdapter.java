package com.acnhcompanion.application.Fish;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.Calendar;

import com.acnhcompanion.application.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import androidx.cardview.widget.CardView;

import static android.content.ContentValues.TAG;
import static android.content.Context.MODE_PRIVATE;

public class FishAdapter extends BaseAdapter{
    Context context;
    List<Fish> fishes;
    LayoutInflater inflater;
    onFishClickedListener mFishListener;
    FishAdapter.onFishLongClickedListener mFishLongListener;
    SharedPreferences sharedPreferences;

    public interface onFishClickedListener {
        void onFishClicked(Fish fish);
    }

    public interface onFishLongClickedListener {
        void onFishLongClicked(Fish bug);
    }

    public FishAdapter(Context context, onFishClickedListener listener, onFishLongClickedListener longClickedListener){
        mFishListener = listener;
        mFishLongListener = longClickedListener;
        fishes = new ArrayList<>();
        inflater = (LayoutInflater.from(context));
        sharedPreferences = context.getSharedPreferences("VillagerPrefs", MODE_PRIVATE);
    }

    public void updateFishAdpater(List<Fish> critterData){
        fishes = critterData;
        //updateFishIDs(fishes);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return fishes.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.gridview_item_view_holder, null);
        view.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                mFishListener.onFishClicked(fishes.get(i));
            }
        });
        view.setOnLongClickListener(new View.OnLongClickListener(){
            @Override
            public boolean onLongClick(View v){
                mFishLongListener.onFishLongClicked(fishes.get(i));
                return true;
            }
        });

        ImageView tile = (ImageView) view.findViewById(R.id.icon);
        RelativeLayout missing = view.findViewById(R.id.linearLayout_missing);
        RelativeLayout present = view.findViewById(R.id.linearLayout);
        TextView textView = view.findViewById(R.id.missing_text);
        ImageView missingCheck = view.findViewById(R.id.imageView2_missing);
        ImageView presentCheck = view.findViewById(R.id.imageView2);
        ImageView museumCheck = view.findViewById(R.id.imageView3);
        CardView cardView = view.findViewById(R.id.cv_grid_icon);
        if(fishes.get(i).imgID == R.drawable.missing_image_asset){
            present.setVisibility(View.INVISIBLE);
            tile.setVisibility(View.INVISIBLE);

            textView.setText(fishes.get(i).name);

            missing.setVisibility(View.VISIBLE);
            textView.setVisibility(View.VISIBLE);

            if(fishes.get(i).isCaught){
                missingCheck.setVisibility(View.VISIBLE);
            } else {
                missingCheck.setVisibility(View.INVISIBLE);
            }

        } else {
            tile.setImageResource(fishes.get(i).imgID);
            missing.setVisibility(View.INVISIBLE);
            textView.setVisibility(View.INVISIBLE);
            cardView.setCardBackgroundColor(Color.WHITE);
            String season = fishes.get(i).northernSeason;

            if(sharedPreferences.getBoolean("Hemisphere", true)){
                season = fishes.get(i).northernSeason;;
            } else {
                season = fishes.get(i).southernSeason;
            }

            if (!isCatchable(fishes.get(i).timeWindow, season)) {
                ColorMatrix matrix = new ColorMatrix();
                matrix.setSaturation(0);

                ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
                tile.setColorFilter(filter);
            }

            if(fishes.get(i).isCaught){
                presentCheck.setVisibility(View.VISIBLE);
            } else {
                presentCheck.setVisibility(View.INVISIBLE);
            }

            if(fishes.get(i).isMuseum){
                museumCheck.setVisibility(View.VISIBLE);
            } else {
                museumCheck.setVisibility(View.INVISIBLE);
            }
        }
        return view;
    }

    int getRRawId(String critterName) {
        switch (critterName) {

        }
        return R.drawable.missing_image_asset;
    }

    boolean isCatchable(String toCheckTimes, String toCheckSeasons){
        return isWithinTimeWindow(toCheckTimes) && isInSeason(toCheckSeasons);
    }

    boolean isWithinTimeWindow(String toCheck){
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        Date currentTime = Calendar.getInstance().getTime();
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(currentTime);
        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        int currentMinute = calendar.get(Calendar.MINUTE);
        int gameHour;
        boolean toSet = true;
        gameHour = (currentHour + sharedPreferences.getInt("HourOff", 0));

        if(toCheck.contains("All day")){
            return true;
        }

        String[] bounds = toCheck.split("-");
        for(int i = 0; i < bounds.length-1; i+=2){
            int adderUpper = 0;
            int adderLower = 0;
            if(bounds[i].contains("p.m.")){
                adderLower += 12;
            }
            if(bounds[i+1].contains("p.m.")){
                adderUpper += 12;
            }
            String boundsUpper = bounds[i+1].replaceAll("\\D+", "");
            String boundsLower = bounds[i].replaceAll("\\D+", "");
            int upperBound = Integer.parseInt(boundsUpper) + adderUpper;
            int lowerBound = Integer.parseInt(boundsLower) + adderLower;
            if(upperBound < lowerBound){
                if(gameHour >= lowerBound || gameHour < upperBound){
                    return true;
                }
            } else {
                if(gameHour >= lowerBound && gameHour < upperBound){
                    return true;
                }
            }
        }
        return false;
    }

    boolean isInSeason(String toCheck){
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        Date currentTime = Calendar.getInstance().getTime();
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(currentTime);
        int currentMonth = calendar.get(Calendar.MONTH);
        int month;
        month = (currentMonth + sharedPreferences.getInt("MonthOff", 0));

        String temp = toCheck.split(" \\(Northern\\)| \\(Northern and Southern\\)| \\(Southern\\)")[0];
        if(temp.contains("Year-round")){
            return true;
        }
        String[] bounds = temp.split("-| ");
        for(int i = 0; i < bounds.length-1; i += 2) {
            String lowerBoundName = bounds[i];
            String upperBoundName = bounds[i + 1];
            Log.d(TAG, "isInSeason: " + upperBoundName + " " + lowerBoundName);
                Month upperBoundMonth = Month.valueOf(upperBoundName);
                Month lowerboundMonth = Month.valueOf(lowerBoundName);
            Log.d(TAG, "isInSeason: ub: " + upperBoundMonth.ordinal() + " lb:" + lowerboundMonth.ordinal() + "\ncurrent month: " + month);
                if (upperBoundMonth.ordinal() < lowerboundMonth.ordinal()) {
                    if (month >= lowerboundMonth.ordinal() || month < upperBoundMonth.ordinal()) {
                        return true;
                    }
                } else {
                    if (month >= lowerboundMonth.ordinal() && month < upperBoundMonth.ordinal()) {
                        return true;
                    }
                }
            }
        return false;
    }

    public enum Month{
        January,
        February,
        March,
        April,
        May,
        June,
        July,
        August,
        September,
        October,
        November,
        December
    }
}
