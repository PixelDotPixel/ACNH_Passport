package com.acnhcompanion.application.Crafting;

import android.content.Context;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.acnhcompanion.application.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class CraftingAdapter extends RecyclerView.Adapter<CraftingAdapter.CraftingViewHolder> {
    Context context;
    List<Recipes> recipes;
    LayoutInflater inflater;

    CraftingAdapter.onRecipeClickedListener mRecipeListener;
    CraftingAdapter.onRecipeLongClickedListener mRecipeLongListener;

    public interface onRecipeClickedListener {
        void onRecipeClicked(Recipes recipe);
    }

    public interface onRecipeLongClickedListener {
        void onRecipeLongClicked(Recipes recipe);
    }

    public CraftingAdapter(Context context, List<Recipes> recipes, onRecipeClickedListener onRecipeClicked, onRecipeLongClickedListener onRecipeLongClicked) {
        this.context = context;
        this.recipes = recipes;
        this.mRecipeListener = onRecipeClicked;
        this.mRecipeLongListener = onRecipeLongClicked;
        inflater = (LayoutInflater.from(context));
    }

    public void updateRecipesAdapter(List<Recipes> critterData){
        recipes = critterData;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    @Override
    public CraftingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.fossil_view_model, parent, false);
        return new CraftingViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CraftingViewHolder holder, int position){
        holder.bind(recipes.get(position));
    }

    public class CraftingViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener{
        private ImageView tile_icon;
        private ImageView check;
        private TextView craftableItemDetails;
        private TextView craftableItemTitle;

        public CraftingViewHolder(@NonNull View view){
            super(view);
            tile_icon=view.findViewById(R.id.icon_cvm);
            check = view.findViewById(R.id.imageView2_cvm);
            craftableItemDetails = view.findViewById(R.id.tv_craftable_item_details);
            craftableItemTitle = view.findViewById(R.id.tv_craftable_item_title);
            view.setOnLongClickListener(this);

            //view.setOnClickListener(this);
        }

        public void bind(Recipes recipes){
            if(recipes.isCrafted){
                check.setVisibility(View.VISIBLE);
            } else {
                check.setVisibility(View.INVISIBLE);
            }

            if(recipes.IMGid == R.raw.acnh_b92efb24a514a53376e25682361cce23){
                tile_icon.setImageResource(R.drawable.missing_image_asset);
            } else {
                tile_icon.setImageResource(recipes.IMGid);
            }

            SpannableString spannableString = new SpannableString(recipes.rName);
            spannableString.setSpan(new UnderlineSpan(), 0 , recipes.rName.length(), 0);

            craftableItemTitle.setText(spannableString);
            craftableItemTitle.setTextSize(20);
            String[] materialNames = recipes.materialNames.split("\n");
            String[] materialCounts = recipes.materialCounts.split(",");

            /*Log.d(TAG, "bind Recipe: " + recipes.rName);
            for (int i = 0; i < materialCounts.length; i++) {
                Log.d(TAG, "bind material: " + materialCounts[i] + "\n");
            }*/
            String toBindMaterials = "";
            for(int i = 0; i < materialNames.length; i++){
                if(materialCounts.length == materialNames.length){
                    toBindMaterials += materialNames[i] + "  (x" + materialCounts[i] + ")\n";
                } else {
                    toBindMaterials += materialNames[i] + "\n";
                }

            }

            craftableItemDetails.setText(toBindMaterials);
        }

        @Override
        public boolean onLongClick(View view) {
            return false;
        }
    }
}