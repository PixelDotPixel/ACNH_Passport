package com.acnhcompanion.application;

import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.acnhcompanion.application.R;
import com.acnhcompanion.application.Search_Item;

import java.util.Comparator;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SortedList;

public class Search_Item_Adapter extends RecyclerView.Adapter<Search_Item_Adapter.Search_Item_ViewHolder> {
    public List<Search_Item> search_Items;
    public List<Search_Item> sorted_search_items;
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
        holder.bind(sortedList.get(position));
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
}
