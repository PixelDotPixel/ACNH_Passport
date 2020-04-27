package com.acnhcompanion.application;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.acnhcompanion.application.Bugs.BugAdapter;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SortedList;

import static android.content.ContentValues.TAG;
import static android.content.Context.MODE_PRIVATE;

public class Search_Item_Adapter extends RecyclerView.Adapter<Search_Item_Adapter.Search_Item_ViewHolder> {
    public List<Search_Item> search_Items;
    SharedPreferences sharedPreferences;
    //private final Comparator<Search_Item> mComparator;
    public Context context;
    LayoutInflater inflater;
    onSearchLongClickedListener mSearchLongListener;

    public interface onSearchLongClickedListener {
        void onSearchItemLongClicked(Search_Item item);
    }

    public Search_Item_Adapter(Context context, List<Search_Item> search_items, onSearchLongClickedListener listener/*, Comparator<Search_Item> comparator*/){
        //this.mComparator = comparator;
        this.context = context;
        this.search_Items = search_items;
        //this.mSearchLongListener = listener;
        sharedPreferences = context.getSharedPreferences("VillagerPrefs", MODE_PRIVATE);
        inflater = (LayoutInflater.from(context));
    }

    @NonNull
    @Override
    public Search_Item_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.search_item_view_model, parent, false);
        return new Search_Item_ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull Search_Item_ViewHolder holder, int position) {
        if(sortedList.get(position).critterType == "fish" || sortedList.get(position).critterType == "bug") {
            String season = sortedList.get(position).northernSeason;
            if (sharedPreferences.getBoolean("Hemisphere", true)) {
                season = sortedList.get(position).northernSeason;
            } else {
                season = sortedList.get(position).southernSeason;
            }
            colorState(!isCatchable(sortedList.get(position).timeWindow, season), holder.iv_search_item);
        } else {
            colorState(false, holder.iv_search_item);
        }
        holder.bind(sortedList.get(position));
    }

    private void colorState(Boolean value, ImageView imageView){
        if(value){
            setColor(imageView);
        } else {
            nullColor(imageView);
        }
    }

    private void setColor(ImageView imageView){
        ColorMatrix matrix = new ColorMatrix();
        matrix.setSaturation(0);
        ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
        imageView.setColorFilter(filter);
    }

    private void nullColor(ImageView imageView){
        imageView.setColorFilter(null);
    }

    public void updateSearchItemAdapter(List<Search_Item> Search_Items){
        this.search_Items = Search_Items;
        notifyDataSetChanged();
    }

    public void addSearchItems(List<Search_Item> Search_Items){
        this.search_Items.addAll(search_Items);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return sortedList.size();
    }

    public class Search_Item_ViewHolder extends RecyclerView.ViewHolder {
        private ImageView iv_search_item;
        private TextView tv_search_item;
        private CardView cv_search_item;

        public Search_Item_ViewHolder(@NonNull View view) {
            super(view);
            iv_search_item = view.findViewById(R.id.iv_search_item);
            tv_search_item = view.findViewById(R.id.tv_search_item);
            cv_search_item = view.findViewById(R.id.cv_search_item);
            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    //TODO onclicklistener
                    return false;
                }
            });
        }

        public void bind(Search_Item search_item){
            iv_search_item.setImageResource(search_item.imgID);
            tv_search_item.setText(search_item.critterName);
            cv_search_item.setCardBackgroundColor(Color.blue(1));
        }


    }

    //Search Functionality
    final SortedList<Search_Item> sortedList = new SortedList<>(Search_Item.class, new SortedList.Callback<Search_Item>() {

        @Override
        public void onInserted(int position, int count) {
            notifyItemRangeInserted(position, count);
        }

        @Override
        public void onRemoved(int position, int count) {
            notifyItemRangeRemoved(position, count);
        }

        @Override
        public void onMoved(int fromPosition, int toPosition) {
            notifyItemMoved(fromPosition, toPosition);
        }

        @Override
        public int compare(Search_Item o1, Search_Item o2) {
            return o1.critterName.compareTo(o2.critterName);
        }

        @Override
        public void onChanged(int position, int count) {
            notifyItemRangeChanged(position, count);
        }

        @Override
        public boolean areContentsTheSame(Search_Item oldItem, Search_Item newItem) {
            return oldItem.equals(newItem);
        }

        @Override
        public boolean areItemsTheSame(Search_Item item1, Search_Item item2) {
            return (item1.critterName == item2.critterName);
        }
    });

    public void add(Search_Item item){
        sortedList.add(item);
    }

    public void remove(Search_Item item){
        sortedList.remove(item);
    }

    public void add(List<Search_Item> items){
        sortedList.addAll(items);
    }

    public void remove(List<Search_Item> items){
        sortedList.beginBatchedUpdates();
        for(Search_Item item : items){
            sortedList.remove(item);
        }
        sortedList.endBatchedUpdates();
    }

    public void replaceAll(List<Search_Item> items){
        sortedList.beginBatchedUpdates();
        for(int i = sortedList.size() - 1; i >= 0; i--){
            final Search_Item toGet = sortedList.get(i);
            if(!items.contains(toGet)) {
                sortedList.remove(toGet);
            }
        }
        sortedList.addAll(items);
        sortedList.endBatchedUpdates();
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
}
