package com.acnhcompanion.application.Sharing;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.acnhcompanion.application.R;

import java.io.ByteArrayOutputStream;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class IslanderSearchAdapter extends RecyclerView.Adapter<IslanderSearchAdapter.IslanderSearchViewHolder>{
    private List<Islander> islanderResults;
    ColorDrawable green;
    ColorDrawable tan;
    ColorDrawable blue;


    public IslanderSearchAdapter(List<Islander> islanders) {
        green = new ColorDrawable(Color.parseColor("#96e3af"));
        tan = new ColorDrawable(Color.parseColor("#f4ebe6"));
        blue = new ColorDrawable(Color.parseColor("#c2ffff"));
    }

    public void updateIslanders(List<Islander> islanders) {
        this.islanderResults = islanders;
        for(int i = 0; i < islanders.size(); i++){
            if(islanders!=null){
                Log.d(TAG, "updateIslanders: " + islanders.get(i).Islander + islanders.get(i).Island + islanders.get(i).Friend_code + islanders.get(i).Message);
            }
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public IslanderSearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.open_islands_view_holder, parent, false);
        return new IslanderSearchViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull IslanderSearchViewHolder holder, int position) {
        holder.bind(islanderResults.get(position), position);
    }

    @Override
    public int getItemCount() {
        if(islanderResults != null){
            return islanderResults.size();
        } else {
            return 0;
        }
    }

    public class IslanderSearchViewHolder extends RecyclerView.ViewHolder {
        private ImageView tile_icon;
        private CardView islandItemCard;
        private TextView islandItemDetails;
        private TextView islandItemTitle;

        public IslanderSearchViewHolder(@NonNull View itemView) {
            super(itemView);
            tile_icon = itemView.findViewById(R.id.icon_cvm_sharing);
            islandItemDetails = itemView.findViewById(R.id.tv_island_details_sharing);
            islandItemTitle = itemView.findViewById(R.id.tv_island_title_sharing);
            islandItemCard = itemView.findViewById(R.id.cv_id_card_sharing);
        }

        void bind(Islander islander, int position){
            if((position % 4) == 0){
                islandItemCard.setCardBackgroundColor(blue.getColor());
            } else if((position % 3) == 1){
                islandItemCard.setCardBackgroundColor(tan.getColor());
            } else if((position % 3) == 2){
                islandItemCard.setCardBackgroundColor(green.getColor());
            } else {
                islandItemCard.setCardBackgroundColor(tan.getColor());
            }
            SpannableString spannableString = new SpannableString(islander.Island);
            spannableString.setSpan(new UnderlineSpan(), 0 , islander.Island.length(), 0);
            if(islander.Image != ""){
                Bitmap image = decodeBase64(islander.Image);
                tile_icon.setImageBitmap(image);
            } else {
                tile_icon.setImageResource(R.drawable.missing_image_asset);
            }
            islandItemTitle.setText(spannableString);
            islandItemDetails.setText("Friend Code: " + islander.Friend_code + "\n\n" + "Islander: " + islander.Islander + "\n\n" + islander.Message);
        }


        public Bitmap decodeBase64(String toDecode){
            //https://acpatterns.com/api.php?recent=1&nsfc=0&letsgetdangerous=0
            byte[] decodedByte = Base64.decode(toDecode, 0);
            return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
        }
    }
}
