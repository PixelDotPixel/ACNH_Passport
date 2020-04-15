package com.acnhcompanion.application.Fossils;

import android.content.Context;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.acnhcompanion.application.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FossilAdapter extends RecyclerView.Adapter<FossilAdapter.FossilViewHolder> {
    public List<Fossil> fossils;
    public Context context;
    LayoutInflater inflater;
    FossilAdapter.onFossilLongClickedListener mFossilLongListener;

    public interface onFossilLongClickedListener {
        void onFossilLongClicked(Fossil fossil);
    }

    public FossilAdapter(Context context, List<Fossil> fossils, onFossilLongClickedListener listener){
        this.context = context;
        this.fossils = fossils;
        this.mFossilLongListener = listener;
        inflater = (LayoutInflater.from(context));
    }

    @NonNull
    @Override
    public FossilAdapter.FossilViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.fossil_view_model, parent, false);
        return new FossilViewHolder(itemView);
    }

    public void updateFossilAdapter(List<Fossil> fossils){
        this.fossils = fossils;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull FossilAdapter.FossilViewHolder holder, int position) {
        holder.bind(fossils.get(position));
    }

    @Override
    public int getItemCount() {
        return fossils.size();
    }

    public class FossilViewHolder extends RecyclerView.ViewHolder {
        private ImageView tile_icon;
        private ImageView checkMuseum;
        private TextView fossilItemDetails;
        private TextView fossilItemTitle;

        public FossilViewHolder(@NonNull View view) {
            super(view);
            tile_icon=view.findViewById(R.id.icon_cvm);
            checkMuseum = view.findViewById(R.id.imageView2_cvm);
            fossilItemDetails = view.findViewById(R.id.tv_craftable_item_details);
            fossilItemTitle = view.findViewById(R.id.tv_craftable_item_title);
            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    mFossilLongListener.onFossilLongClicked(fossils.get(getAdapterPosition()));
                    return false;
                }
            });
        }

        public void bind(Fossil fossil){
            if(fossil.isMuseum){
                checkMuseum.setVisibility(View.VISIBLE);
            } else {
                checkMuseum.setVisibility(View.INVISIBLE);
            }

            tile_icon.setImageResource(fossil.imgID);

            SpannableString spannableString = new SpannableString(fossil.name);
            spannableString.setSpan(new UnderlineSpan(), 0 , fossil.name.length(), 0);

            fossilItemTitle.setText(spannableString);
            fossilItemTitle.setTextSize(20);

            if(fossil.parentStructure != "NOSET") {
                fossilItemDetails.setText("Value: " + fossil.bells + " Bells\n" + "Set: " + fossil.parentStructure);
            } else {
                fossilItemDetails.setText("Value: " + fossil.bells + " Bells\n");
            }

        }
    }
}
