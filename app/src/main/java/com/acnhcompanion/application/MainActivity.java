package com.acnhcompanion.application;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputFilter;
import android.text.InputType;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.widget.AnalogClock;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.graphics.Matrix;

import com.acnhcompanion.application.Bugs.BugActivity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{
    //Functional Elements
    SharedPreferences sharedPreferences;

    //Stored Variables
    String uri_image;
    String vName;
    String vIsland;
    String vFriendCode;

    //Visual Elements/widgets
    CardView cvBanner;
    CardView cvTop;
    CardView cvBot;
    CardView cvLong;
    CardView cvSmall2;
    CardView cvClock;
    CardView cvCalander;
    CardView cvTailor;

    //Variables
    ColorDrawable green;
    ColorDrawable tan;
    ColorDrawable tanIcon;
    ColorDrawable blue;




    private static final String TAG = MainActivity.class.getSimpleName();
    private int PICK_IMAGE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        green = new ColorDrawable(Color.parseColor("#96e3af"));
        tan = new ColorDrawable(Color.parseColor("#f4ebe6"));
        blue = new ColorDrawable(Color.parseColor("#c2ffff"));

        /*getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#014421")));*/
        sharedPreferences = getSharedPreferences("VillagerPrefs", MODE_PRIVATE);

        vName = sharedPreferences.getString("Villager", "");
        vIsland = sharedPreferences.getString("Islandname", "");
        vFriendCode = sharedPreferences.getString("Friendcode", "");
        uri_image = sharedPreferences.getString("imgPath", "");

        if(uri_image != ""){
            Bitmap image = decodeBase64(uri_image);
            ImageView imageView = findViewById(R.id.iv_id);
            imageView.setImageBitmap(image);

        }

        cvClock = findViewById(R.id.cv_smallclock);
        cvClock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(MainActivity.this);
                final View customLayout = getLayoutInflater().inflate(R.layout.time_dialog, null);
                builder.setView(customLayout);
                android.app.AlertDialog dialog = builder.create();
                dialog.show();

            }
        });

        cvCalander = findViewById(R.id.cv_smallCalander);
        cvCalander.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(MainActivity.this);
                final View customLayout = getLayoutInflater().inflate(R.layout.date_dialog, null);
                builder.setView(customLayout);
                android.app.AlertDialog dialog = builder.create();
                dialog.show();

            }
        });

        cvBanner = findViewById(R.id.cv_banner_title);
        cvBanner.setBackground(new ColorDrawable(Color.TRANSPARENT));

        cvTop = findViewById(R.id.cv_top);
        cvTop.setCardBackgroundColor(green.getColor());

        //cvBot = findViewById(R.id.cv_bottom);

        cvLong = findViewById(R.id.cv_tall);
        cvLong.setCardBackgroundColor(blue.getColor());


        cvTailor = findViewById(R.id.cv_small1);
        cvTailor.setCardBackgroundColor(tan.getColor());
        cvTailor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://nookpedia.com/designs";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });

        cvSmall2 = findViewById(R.id.journal_card);
        cvSmall2.setCardBackgroundColor(tan.getColor());

        TextView textViewIDCARD = findViewById(R.id.tv_id);
        textViewIDCARD.setText("Name: " + vName + "\n\nIsland: " + vIsland + "\n\nFriend Code: SW-" + vFriendCode);

        final CardView cardView = findViewById(R.id.tv_id_card);
        cardView.setCardBackgroundColor(tan.getColor());
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);

                String sharedText = getSharedText();

                intent.putExtra(Intent.EXTRA_TEXT, sharedText);
                intent.setType("text/plain");

                Intent shareIntent = Intent.createChooser(intent, null);
                startActivity(shareIntent);
            }
        });
        cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                alertDialogEdit();
                return false;
            }
        });

        CardView cardViewImage = findViewById(R.id.iv_id_card);
        cardViewImage.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                chooseImage();
                return false;
            }
        });

        CardView journalCard = findViewById(R.id.journal_card);
        journalCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, BugActivity.class);
                startActivity(intent);
            }
        });
    }

    String getSharedText(){
        String toReturn = "Villager: " +  sharedPreferences.getString("Villager", "") + "\n";
        toReturn +="Island: " + sharedPreferences.getString("Islandname", "") + "\n";
        toReturn +="Friendcode: " + sharedPreferences.getString("Friendcode", "") + "\n";
        return toReturn;
    }

    void alertDialogEdit(){
        final SharedPreferences.Editor editor = sharedPreferences.edit();

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
        alertDialog.setTitle("Villager Data");

        LinearLayout layout = new LinearLayout(MainActivity.this);
        layout.setOrientation(LinearLayout.VERTICAL);

        final EditText input1 = new EditText(MainActivity.this);
        input1.setHint("Villager Name");
        input1.setText(vName);

        layout.addView(input1);

        final EditText input2 = new EditText(MainActivity.this);
        input2.setHint("Friend Code");
        InputFilter[] filter = new InputFilter[1];
        filter[0] = new InputFilter.LengthFilter(12);
        input2.setFilters(filter);
        input2.setInputType(InputType.TYPE_CLASS_NUMBER);
        input2.setText(vFriendCode.replaceAll("-",""));


        layout.addView(input2);

        final EditText input3 = new EditText(MainActivity.this);
        input3.setHint("Island Name");
        input3.setText(vIsland);

        layout.addView(input3);

        alertDialog.setView(layout);

        alertDialog.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String mask1, mask2, mask3, temp;
                temp = input2.getText().toString().replaceAll("-","");
                TextView textViewIDCARD = findViewById(R.id.tv_id);

                if(temp.length() == 12) {
                    mask1 = temp.substring(0,4);
                    mask2 = temp.substring(4,8);
                    mask3 = temp.substring(8,12);
                    editor.putString("Friendcode", mask1 + "-" + mask2 + "-" + mask3);
                    textViewIDCARD.setText("Name: " + input1.getText().toString() + "\n\nIsland: " + input3.getText().toString() + "\n\nFriend Code: SW-" + mask1 + "-" + mask2 + "-" + mask3);
                } else {
                    editor.putString("Friendcode", input2.getText().toString());
                    textViewIDCARD.setText("Name: " + input1.getText().toString() + "\n\nIsland: " + input3.getText().toString() + "\n\nFriend Code: SW-" + input2.getText().toString());
                }
                editor.putString("Villager", input1.getText().toString());
                editor.putString("Islandname", input3.getText().toString());
                editor.commit();



            }
        });
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        alertDialog.show();
    }

    private void chooseImage(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            final SharedPreferences.Editor editor = sharedPreferences.edit();
            Uri imageUri = data.getData();

            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }

            editor.putString("imgPath",encodeToBase64(bitmap));
            editor.commit();



            ImageView imageView = findViewById(R.id.iv_id);
            imageView.setImageURI(imageUri);
        }
    }

    public static String encodeToBase64(Bitmap image){
        Bitmap immage = image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immage.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);

        Log.d("Image Log:", imageEncoded);
        return imageEncoded;
    }

    public static Bitmap decodeBase64(String toDecode){
        //https://acpatterns.com/api.php?recent=1&nsfc=0&letsgetdangerous=0
        byte[] decodedByte = Base64.decode(toDecode, 0);
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

    }
}
