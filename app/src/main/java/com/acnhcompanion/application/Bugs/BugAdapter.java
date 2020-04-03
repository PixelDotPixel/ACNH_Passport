package com.acnhcompanion.application.Bugs;
import android.content.Context;
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

import com.acnhcompanion.application.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import androidx.cardview.widget.CardView;

import static android.content.ContentValues.TAG;

public class BugAdapter  extends BaseAdapter{
    Context context;
    List<Bug> bugs;
    LayoutInflater inflater;
    BugAdapter.onBugClickedListener mBugListener;
    BugAdapter.onBugLongClickedListener mBugLongListener;

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
            if (!isCatchable(bugs.get(i).timeWindow, bugs.get(i).northernSeason)) {
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
        }
        return view;
    }

    boolean isCatchable(String toCheckTimes, String toCheckSeasons){
        return isWithinTimeWindow(toCheckTimes) && isInSeason(toCheckSeasons);
    }

    boolean isWithinTimeWindow(String toCheck){
        Date curentTime = Calendar.getInstance().getTime();
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(curentTime);
        boolean toSet = true;
        int hours = calendar.get(Calendar.HOUR_OF_DAY);
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
        Date curentTime = Calendar.getInstance().getTime();
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(curentTime);
        String temp = toCheck.split(" \\(Northern\\)| \\(Northern and Southern\\)")[0];
        if(temp.contains("Year-round")){
            return true;
        }
        String[] bounds = temp.split("-| ");
        int month = calendar.get(Calendar.MONTH);
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
