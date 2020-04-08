package com.acnhcompanion.application.Bugs;
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
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.acnhcompanion.application.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import androidx.cardview.widget.CardView;

import static android.content.ContentValues.TAG;
import static android.content.Context.MODE_PRIVATE;

public class BugAdapter  extends BaseAdapter{
    Context context;
    List<Bug> bugs;
    LayoutInflater inflater;
    BugAdapter.onBugClickedListener mBugListener;
    BugAdapter.onBugLongClickedListener mBugLongListener;
    SharedPreferences sharedPreferences;

    public interface onBugClickedListener {
        void onBugClicked(Bug bug);
    }

    public interface onBugLongClickedListener {
        void onBugLongClicked(Bug bug);
    }

    public BugAdapter(Context context, BugAdapter.onBugClickedListener listener, BugAdapter.onBugLongClickedListener longListener){
        mBugListener = listener;
        mBugLongListener = longListener;
        bugs = new ArrayList<>();
        inflater = (LayoutInflater.from(context));
        sharedPreferences = context.getSharedPreferences("VillagerPrefs", MODE_PRIVATE);
    }

    public void updateBugAdapter(List<Bug> critterData){
        bugs = critterData;
        notifyDataSetChanged();
    }


    /*@NonNull
    @Override
    public BugViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.gridview_item_view_holder, parent, false);
        return new BugViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BugViewHolder holder, int position) {
        holder.bind(bugs.get(position));
    }*/

    @Override
    public int getCount() {
        return bugs.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    /*@Override
    public int getItemCount() {
        return 0;
    }*/

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {

        view = inflater.inflate(R.layout.gridview_item_view_holder, null);
        view.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                mBugListener.onBugClicked(bugs.get(i));
            }
        });
        view.setOnLongClickListener(new View.OnLongClickListener(){
            @Override
            public boolean onLongClick(View v){
                mBugLongListener.onBugLongClicked(bugs.get(i));
                return true;
            }
        });
        CardView cardView = view.findViewById(R.id.cv_grid_icon);
        ImageView tile = (ImageView) view.findViewById(R.id.icon);
        RelativeLayout missing = view.findViewById(R.id.linearLayout_missing);
        RelativeLayout present = view.findViewById(R.id.linearLayout);
        TextView textView = view.findViewById(R.id.missing_text);
        ImageView missingCheck = view.findViewById(R.id.imageView2_missing);
        ImageView presentCheck = view.findViewById(R.id.imageView2);
        ImageView museumCheck = view.findViewById(R.id.imageView3);

        if(bugs.get(i).imgID == R.drawable.missing_image_asset){
            present.setVisibility(View.INVISIBLE);
            tile.setVisibility(View.INVISIBLE);

            textView.setText(bugs.get(i).name);

            missing.setVisibility(View.VISIBLE);
            textView.setVisibility(View.VISIBLE);

            if(bugs.get(i).isCaught){
                missingCheck.setVisibility(View.VISIBLE);
            } else {
                missingCheck.setVisibility(View.INVISIBLE);
            }


        } else {
            tile.setImageResource(bugs.get(i).imgID);
            missing.setVisibility(View.INVISIBLE);
            textView.setVisibility(View.INVISIBLE);
            cardView.setCardBackgroundColor(Color.WHITE);
            String season = bugs.get(i).northernSeason;

            if(sharedPreferences.getBoolean("Hemisphere", true)){
                season = bugs.get(i).northernSeason;;
            } else {
                season = bugs.get(i).southernSeason;
            }

            if (!isCatchable(bugs.get(i).timeWindow, season)) {
                ColorMatrix matrix = new ColorMatrix();
                matrix.setSaturation(0);

                ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
                tile.setColorFilter(filter);
            }

            if(bugs.get(i).isCaught){
                presentCheck.setVisibility(View.VISIBLE);
            } else {
                presentCheck.setVisibility(View.INVISIBLE);
            }

            if(bugs.get(i).isMuseum){
                museumCheck.setVisibility(View.VISIBLE);
            } else {
                museumCheck.setVisibility(View.INVISIBLE);
            }
        }
        return view;
    }


    /*int getRRawId(String critterName){
        switch(critterName){
            case"Common Butterfly ":
                return R.raw.common_butterfly_acnh;
            case"Yellow Butterfly ":
                return R.raw.yellow_butterfly_acnh;
            case"Tiger Butterfly ":
                return R.raw.tiger_butterfly_acnh;
            case"Peacock Butterfly ":
                return R.raw.peacock_butterfly_acnh;
            case"Common Bluebottle ":
                return R.raw.common_bluebottle_acnh;
            case"Paper Kite Butterfly ":
                return R.raw.paper_kite_butterfly_acnh;
            case"Great Purple Emperor ":
                return R.raw.great_purple_emperor_acnh;
            case"Monach Butterfly ":
                return R.raw.monarch_butterfly_acnh;
            case"Emperor Butterfly ":
                return R.raw.emperor_butterfly_acnh;
            case"Agrias Butterfly ":
                return R.raw.agrias_butterfly_acnh;
            case"Raja Brooke's Birdwing ":
                return R.raw.rajah_brookes_birdwing_acnh;
            case"Queen Alexandra's Birdwing ":
                return R.raw.queen_alexandras_birdwing_acnh;
            case"Moth ":
                return R.raw.moth_acnh;
            case"Atlas Moth ":
                return R.raw.atlas_moth_acnh;
            case"Madagascan Sunset Moth ":
                return R.raw.madagascan_sunset_moth_acnh;
            case"Long Locust ":
                return R.raw.long_locust_acnh;
            case"Migratory Locust ":
                return R.raw.migratory_locust_acnh;
            case"Rice Grasshopper ":
                return R.raw.rice_grasshopper_acnh;
            case"Grasshopper ":
                return R.raw.grasshopper_acnh;
            case"Cricket ":
                return R.raw.cricket_acnh;
            case"Bell Cricket ":
                return R.raw.bell_cricket_acnh;
            case"Mantis ":
                return R.raw.mantis_acnh;
            case"Orchid Mantis ":
                return R.raw.orchid_mantis_acnh;
            case"Honeybee ":
                return R.raw.honeybee_acnh;
            case"Wasp ":
                return R.raw.wasp_acnh;
            case"Brown Cicada ":
                return R.raw.brown_cicada_acnh;
            case"Robust Cicada ":
                return R.raw.robust_cicada_acnh;
            case"Giant Cicada ":
                return R.raw.giant_cicada_acnh;
            case"Walker Cicada ":
                return R.raw.walker_cicada_acnh;
            case"Evening Cicada ":
                return R.raw.evening_cicada_acnh;
            case"Cicada Shell ":
                return R.raw.cicada_shell_acnh;
            case"Red Dragonfly ":
                return R.raw.red_dragonfly_acnh;
            case"Darner Dragonfly ":
                return R.raw.darner_dragonfly_acnh;
            case"Banded Dragonfly ":
                return R.raw.banded_dragonfly_acnh;
            case"Damselfly ":
                return R.raw.damselfly_acnh;
            case"Firefly ":
                return R.raw.firefly_acnh;
            case"Mole Cricket ":
                return R.raw.mole_cricket_acnh;
            case"Pondskater ":
                return R.raw.pondskater_acnh;
            case"Diving Beetle ":
                return R.raw.diving_beetle_acnh;
            case"Giant Water Bug ":
                return R.raw.giant_water_bug_acnh;
            case"Stinkbug ":
                return R.raw.stink_bug_acnh;
            case"Man-faced Stink Bug ":
                return R.raw.man_faced_stink_bug_acnh;
            case"Ladybug ":
                return R.raw.ladybug_acnh;
            case"Tiger Beetle ":
                return R.raw.tiger_beetle_acnh;
            case"Jewel Beetle ":
                return R.raw.jewel_beetle_acnh;
            case"Violin Beetle ":
                return R.raw.violin_beetle_acnh;
            case"Citrus Long-horned Beetle ":
                return R.raw.citrus_longhorned_beetle_acnh;
            case"Rosalia Batesi Beetle ":
                return R.raw.rosalia_batesi_beetle_acnh;
            case"Blue Weevil Beetle ":
                return R.raw.blue_weevil_beetle_acnh;
            case"Dung Beetle ":
                return R.raw.dung_beetle_acnh;
            case"Earth-boring Dung Beetle ":
                return R.raw.eath_boring_dung_acnh;
            case"Scarab Beetle ":
                return R.raw.scarab_beetle_acnh;
            case"Drone Beetle ":
                return R.raw.drone_beetle_acnh;
            case"Goliath Beetle ":
                return R.raw.goliath_beetle_acnh;
            case"Saw Stag ":
                return R.raw.saw_stag_acnh;
            case"Miyama Stag ":
                return R.raw.miyama_stag_acnh;
            case"Giant Stag ":
                return R.raw.giant_stag_acnh;
            case"Rainbow Stag ":
                return R.raw.rainbow_stag_acnh;
            case"Cyclommatus Stag ":
                return R.raw.cyclommatus_stag_acnh;
            case"Golden Stag ":
                return R.raw.golden_stag_acnh;
            case"Giraffe Stag ":
                return R.raw.giraffe_stag_acnh;
            case"Horned Dynastid ":
                return R.raw.horned_dynastid_acnh;
            case"Horned Atlas ":
                return R.raw.horned_atlas_acnh;
            case"Horned Elephant ":
                return R.raw.horned_elephant_acnh;
            case"Horned Herucles ":
                return R.raw.horned_hercules_acnh;
            case"Walking Stick ":
                return R.raw.walkingstick_acnh;
            case"Walking Leaf ":
                return R.raw.walking_leaf_acnh;
            case"Bagworm ":
                return R.raw.bagworm_acnh;
            case"Ant ":
                return R.raw.ant_acnh;
            case"Hermit Crab ":
                return R.raw.hermit_crab_acnh;
            case"Wharf Roach ":
                return R.raw.wharf_roach_acnh;
            case"Fly ":
                return R.raw.fly_acnh;
            case"Mosquito ":
                return R.raw.mosquito_acnh;
            case"Flea ":
                return R.raw.flea_acnh;
            case"Snail ":
                return R.raw.snail_acnh;
            case"Pill Bug ":
                return R.raw.pill_bug_acnh;
            case"Centipede ":
                return R.raw.centipede_acnh;
            case"Spider ":
                return R.raw.spider_acnh;
            case"Tarantula ":
                return R.raw.tarantula_acnh;
            case"Scorpion ":
                return R.raw.scorpion_acnh;
        }
        return R.drawable.missing_image_asset;
    }*/

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
        int hours;
        boolean toSet = true;
        hours = (currentHour + sharedPreferences.getInt("HourOff", 0));
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
                if(hours >= lowerBound || hours < upperBound){
                    return true;
                }
            } else {
                if(hours >= lowerBound && hours < upperBound){
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
            BugAdapter.Month upperBoundMonth = BugAdapter.Month.valueOf(upperBoundName);
            BugAdapter.Month lowerboundMonth = BugAdapter.Month.valueOf(lowerBoundName);
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

    /*public class BugViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener, View.OnClickListener {
        private RelativeLayout missing;
        private RelativeLayout present;
        private TextView textView;
        private ImageView tile;
        private ImageView missingCheck;
        private ImageView presentCheck;

        public BugViewHolder(@NonNull View view){
            super(view);
        ImageView tile = (ImageView) view.findViewById(R.id.icon);
        RelativeLayout missing = view.findViewById(R.id.linearLayout_m
        RelativeLayout present = view.findViewById(R.id.linearLayout);
        TextView textView = view.findViewById(R.id.missing_text);
            view.setOnClickListener(this);
            view.setOnLongClickListener(this);
        }

        public void bind(Bug bug){

        }

        @Override
        public void onClick(View view) {

        }

        @Override
        public boolean onLongClick(View view) {
            return false;
        }
    }*/
}
