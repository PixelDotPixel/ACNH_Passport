package com.acnhcompanion.application;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.List;

public class FossilAdapter extends BaseAdapter{
    Context context;
    List<String> test;
    LayoutInflater inflater;

    public FossilAdapter(Context context, List<String> logos){
        this.context = context;
        this.test = logos;
        inflater = (LayoutInflater.from(context));
    }

    @Override
    public int getCount() {
        return test.size();
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
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.gridview_item_view_holder, null);
        ImageView tile = (ImageView) view.findViewById(R.id.icon);
        return view;
    }
}
