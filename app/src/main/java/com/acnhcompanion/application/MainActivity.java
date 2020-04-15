package com.acnhcompanion.application;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.acnhcompanion.application.Bugs.BugActivity;
import com.acnhcompanion.application.Sharing.IslanderPostRepository;
import com.acnhcompanion.application.Sharing.Islands_Activity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.regex.Pattern;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;

public class MainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{
    //Functional Elements
    SharedPreferences sharedPreferences;
    IslanderPostRepository islanderPostRepository;

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
    CardView cvCrafting;
    CardView cvTailor;

    //Variables
    ColorDrawable green;
    ColorDrawable tan;
    ColorDrawable tanIcon;
    ColorDrawable blue;




    private static final String TAG = MainActivity.class.getSimpleName();
    private int PICK_IMAGE_REQUEST = 1;

    @RequiresApi(api = Build.VERSION_CODES.M)
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

        islanderPostRepository = new IslanderPostRepository(vName, vIsland, vFriendCode, uri_image, "HELLO FROM THE APP");
        islanderPostRepository.getStatus().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if(s != "init") {
                    Toast toShow;
                    Log.d(TAG, "onChanged: " + s);
                    if(s == "0") {
                        toShow = Toast.makeText(getApplicationContext(), "Friend Code is Live!", Toast.LENGTH_LONG);
                    } else {
                        toShow = Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG);
                    }
                    toShow.show();
                }
            }
        });

        if(uri_image != ""){
            Bitmap image = decodeBase64(uri_image);
            ImageView imageView = findViewById(R.id.iv_id);
            imageView.setImageBitmap(image);
        }

        cvClock = findViewById(R.id.cv_smallClock);
        cvClock.setCardBackgroundColor(tan.getColor());
        cvClock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Build.VERSION.SDK_INT >= 26) {
                    alertDialogTime26();
                } else {
                    alertDialogTimeLegacy();;
                }

            }
        });

        cvCrafting = findViewById(R.id.cv_crafting);
        cvCrafting.setCardBackgroundColor(tan.getColor());
        cvCrafting.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                /*String url = "https://nookpedia.com/designs";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);*/
                Intent intent = new Intent(MainActivity.this, Islands_Activity.class);
                startActivity(intent);
            }
        });

        cvBanner = findViewById(R.id.cv_banner_title);
        cvBanner.setBackground(new ColorDrawable(Color.TRANSPARENT));

        cvTop = findViewById(R.id.cv_top);
        cvTop.setCardBackgroundColor(green.getColor());

        //cvBot = findViewById(R.id.cv_bottom);

        //cvLong = findViewById(R.id.cv_tall);
        //cvLong.setCardBackgroundColor(blue.getColor());


        cvTailor = findViewById(R.id.cv_smallTailor);
        cvTailor.setCardBackgroundColor(tan.getColor());
        cvTailor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postAlertDialog();
            }
        });

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
        cardViewImage.setCardBackgroundColor(new ColorDrawable(Color.parseColor("#faeebb")).getColor());
        cardViewImage.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                chooseImage();
                return false;
            }
        });

        CardView journalCard = findViewById(R.id.journal_card);
        journalCard.setCardBackgroundColor(tan.getColor());
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

    void postAlertDialog(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
        alertDialog.setTitle("Sharing Passport!");

        LinearLayout layout = new LinearLayout(MainActivity.this);
        layout.setOrientation(LinearLayout.VERTICAL);

        final EditText input = new EditText(MainActivity.this);
        input.setHint("Write an Invitation to others!");
        InputFilter[] filter = new InputFilter[1];
        filter[0] = new InputFilter.LengthFilter(180);
        input.setFilters(filter);

        layout.addView(input);
        alertDialog.setView(layout);

        alertDialog.setPositiveButton("Post", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                islanderPostRepository.vMessage = input.getText().toString();
                islanderPostRepository.postIslanderData();
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

    void alertDialogTime26(){
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        Date currentTime = Calendar.getInstance().getTime();
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(currentTime);
        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        int currentMinute = calendar.get(Calendar.MINUTE);
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
        int currentMonth = calendar.get(Calendar.MONTH);
        int currentYear = calendar.get(Calendar.YEAR);
        int gameMinute, gameHour;
        int gameMonth, gameYear, gameDay;

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
        alertDialog.setTitle("Island Data");

        LinearLayout layout = new LinearLayout(MainActivity.this);
        layout.setOrientation(LinearLayout.VERTICAL);

        final EditText input1 = new EditText(MainActivity.this);
        input1.setHint("Time");
        gameMinute = (currentMinute + sharedPreferences.getInt("MinuteOff", 0));
        gameHour = (currentHour + sharedPreferences.getInt("HourOff", 0));

        if(gameHour != 0) {
            input1.setText(gameHour + ":" + gameMinute);
        }
        input1.setFocusable(false);
        input1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(MainActivity.this);
                final View customLayout = getLayoutInflater().inflate(R.layout.time_dialog, null);
                TimePicker clock = customLayout.findViewById(R.id.dialog_time_picker);
                clock.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void onTimeChanged(TimePicker timePicker, int i, int i1) {
                        String minutes, hours;
                        if(timePicker.getMinute() < 10){
                            minutes = "0" + timePicker.getMinute();
                        } else {
                            minutes = "" + timePicker.getMinute();
                        }
                        if(timePicker.getHour() < 10){
                            hours = "0" + timePicker.getHour();
                        } else {
                            hours = "" + timePicker.getHour();
                        }
                        input1.setText(hours+":"+minutes);
                    }
                });
                builder.setView(customLayout);
                android.app.AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        layout.addView(input1);

        final EditText input2 = new EditText(MainActivity.this);
        input2.setHint("Date");
        gameDay = (currentDay + sharedPreferences.getInt("DayOff", 0));
        gameMonth = (currentMonth + sharedPreferences.getInt("MonthOff", 0));
        gameYear = (currentYear + sharedPreferences.getInt("YearOff", 0));
        if(gameDay != 0 && gameMonth != 0 && gameYear != 0){
            input2.setText(gameMonth + "/" + gameDay + "/" + gameYear);
        }


        input2.setFocusable(false);
        input2.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(MainActivity.this);
                final View customLayout = getLayoutInflater().inflate(R.layout.date_dialog, null);
                final DatePicker calander = customLayout.findViewById(R.id.dialog_date_date_picker);
                calander.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
                    @Override
                    public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
                        input2.setText( datePicker.getMonth() + 1 + "/" + datePicker.getDayOfMonth() + "/" + datePicker.getYear()); }
                });
                builder.setView(customLayout);
                android.app.AlertDialog dialog = builder.create();
                dialog.show();
                }
            });


        layout.addView(input2);

        final ToggleButton hemisphere = new ToggleButton(MainActivity.this);
        hemisphere.setTextOn("Northern");
        hemisphere.setTextOff("Southern");
        hemisphere.setChecked(true);

        layout.addView(hemisphere);

        alertDialog.setView(layout);

        alertDialog.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(Pattern.matches("[0-1][0-9]:[0-5][0-9]|2[0-4]:[0-5][0-9]",input1.getText().toString()) && Pattern.matches(
                        "0[13578]/[0-2][0-9]/[0-9]{4}|0[13578]/3[0-1]/[0-9]{4}|" +
                                "1[02]/[0-2][0-9]/[0-9]{4}|1[02]/3[0-1]/[0-9]{4}|" +
                                "0[469]/[0-2][0-9]/[0-9]{4}|0[469]/30/[0-9]{4}|" +
                                "11/[0-2][0-9]/[0-9]{4}|11/30/[0-9]{4}|" +
                                "02/[0-1][0-9]/[0-9]{4}|02/2[0-8]/[0-9]{4}",input2.getText().toString())) {
                    int hour, minute, day, month, year;
                    Date currentTime = Calendar.getInstance().getTime();
                    Calendar calendar = GregorianCalendar.getInstance();
                    calendar.setTime(currentTime);
                    int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
                    int currentMinute = calendar.get(Calendar.MINUTE);
                    int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
                    int currentMonth = calendar.get(Calendar.MONTH);
                    int currentYear = calendar.get(Calendar.YEAR);
                    boolean hemisphereVal = hemisphere.isChecked();

                    String temp;
                    String[] tempArr;

                    temp = input1.getText().toString();
                    tempArr = temp.split(":");
                    hour = Integer.parseInt(tempArr[0]);
                    minute = Integer.parseInt(tempArr[1]);
                    editor.putInt("Hour", hour);
                    editor.putInt("Minute", minute);
                    editor.putInt("HourOff", hour - currentHour);
                    editor.putInt("MinuteOff", minute - currentMinute);

                    temp = input2.getText().toString();
                    tempArr = temp.split("/");
                    month = Integer.parseInt(tempArr[0]);
                    day = Integer.parseInt(tempArr[1]);
                    year = Integer.parseInt(tempArr[2]);
                    editor.putInt("Day", day);
                    editor.putInt("Month", month);
                    editor.putInt("Year", year);
                    editor.putInt("DayOff", day - currentDay);
                    editor.putInt("MonthOff", month - currentMonth);
                    editor.putInt("YearOff", year - currentYear);
                    editor.putBoolean("Hemisphere", hemisphereVal);


                    editor.commit();
                }
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

    void alertDialogTimeLegacy(){
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        Date currentTime = Calendar.getInstance().getTime();
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(currentTime);
        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        int currentMinute = calendar.get(Calendar.MINUTE);
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
        int currentMonth = calendar.get(Calendar.MONTH);
        int currentYear = calendar.get(Calendar.YEAR);
        int gameMinute, gameHour;
        int gameMonth, gameYear, gameDay;

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
        alertDialog.setTitle("Island Data");

        LinearLayout layout = new LinearLayout(MainActivity.this);
        layout.setOrientation(LinearLayout.VERTICAL);

        final EditText input1 = new EditText(MainActivity.this);
        input1.setHint("Time (24hr format)");
        input1.setInputType(InputType.TYPE_CLASS_DATETIME);
        input1.addTextChangedListener(new TextWatcher() {
            private static final int TOTAL_SYMBOLS = 5;
            private static final int TOTAL_DIGITS = 4;
            private static final int DIVIDER_MODULO = 2;
            private static final int DIVIDER_POSITION = DIVIDER_MODULO - 1;
            private static final char DIVIDER = ':';

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // noop
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // noop
            }

            @Override
            public void afterTextChanged(Editable editable) {
                /*if(!isInputCorrect(editable, TOTAL_SYMBOLS, DIVIDER_MODULO, DIVIDER)){
                    editable.replace(0, editable.length(), buildCorrectString(editable.getChars();, DIVIDER_POSITION, DIVIDER));
                }*/
                String text = editable.toString();
                int length = text.length();
                if(length == 1 && !Pattern.matches("[0-2]", text)){
                    editable.delete(length-1,length);
                } else if(length == 2 && !Pattern.matches("[0-1][0-9]|[2][0-3]", text)){
                    editable.delete(length-1,length);
                } else if(length == 2 && Pattern.matches("[0-2][0-9]", text)){
                    editable.append(":");
                } else if(length == 4 && !Pattern.matches("[0-2][0-9]:[0-5]", text)){
                    editable.delete(length-1,length);
                } else if(length == 5 && !Pattern.matches("[0-2][0-9]:[0-5][0-9]", text)){
                    editable.delete(length-1,length);
                } else if(length > 5){
                    editable.delete(length-1, length);
                }
            }
        });
        gameMinute = (currentMinute + sharedPreferences.getInt("MinuteOff", -1));
        gameHour = (currentHour + sharedPreferences.getInt("HourOff", -1));
        String sMinute = Integer.toString(gameMinute);
        String sHour = Integer.toString(gameHour);
        if(gameMinute < 10){
            sMinute = "0" + Integer.toString(gameMinute);
        }
        if(gameHour<10){
            sHour = "0" + Integer.toString(gameHour);
        }
        if(gameHour != -1 || gameMinute != -1) {
            input1.setText(sHour + ":" + sMinute);
        }
        layout.addView(input1);

        final EditText input2 = new EditText(MainActivity.this);
        input2.setHint("Date");
        input2.setInputType(InputType.TYPE_CLASS_DATETIME);
        input2.addTextChangedListener(new TextWatcher() {

            public static final int MAX_FORMAT_LENGTH = 8;
            public static final int MIN_FORMAT_LENGTH = 3;

            private String updatedText;
            private boolean editing;

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence text, int i, int i1, int i2) {

                if (text.toString().equals(updatedText) || editing) return;

                String digitsOnly = text.toString().replaceAll("\\D", "");
                int digitLen = digitsOnly.length();

                if (digitLen < MIN_FORMAT_LENGTH || digitLen > MAX_FORMAT_LENGTH) {
                    updatedText = digitsOnly;
                    return;
                }

                if (digitLen <= 4) {
                    String month = digitsOnly.substring(0, 2);
                    String day = digitsOnly.substring(2);

                    updatedText = String.format(Locale.US, "%s/%s", month, day);
                }
                else {
                    String month = digitsOnly.substring(0, 2);
                    String day = digitsOnly.substring(2, 4);
                    String year = digitsOnly.substring(4);

                    updatedText = String.format(Locale.US, "%s/%s/%s", month, day, year);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.length() > 10){
                    editable.delete(editable.length()-1,editable.length());
                }
                if (editing)
                    return;
                editing = true;
                editable.clear();
                editable.insert(0, updatedText);
                editing = false;
            }
        });


        gameDay = (currentDay + sharedPreferences.getInt("DayOff", 0));
        gameMonth = (currentMonth + sharedPreferences.getInt("MonthOff", 0));
        gameYear = (currentYear + sharedPreferences.getInt("YearOff", 0));
        if(gameDay != 0 && gameMonth != 0 && gameYear != 0){
            String sMonth = Integer.toString(gameMonth);
            String sDay =Integer.toString(gameDay);
            String sYear = Integer.toString(gameYear);
            if(gameMonth < 10){
                sMonth =  "0" + Integer.toString(gameMonth);
            }
            if(gameDay < 10){
                sDay =  "0" + Integer.toString(gameDay);
            }
            if(gameYear < 10){
                sYear=  "000" + Integer.toString(gameYear);
            }
            if(gameYear < 100){
                sYear=  "00" + Integer.toString(gameYear);
            }
            if(gameYear < 1000){
                sYear=  "0" + Integer.toString(gameYear);
            }

            input2.setText(sMonth + "/" + sDay + "/" + sYear);
        }
        layout.addView(input2);

        final ToggleButton hemisphere = new ToggleButton(MainActivity.this);
        hemisphere.setTextOn("Northern");
        hemisphere.setTextOff("Southern");
        hemisphere.setChecked(true);

        layout.addView(hemisphere);

        alertDialog.setView(layout);

        alertDialog.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(Pattern.matches("[0-1][0-9]:[0-5][0-9]|2[0-4]:[0-5][0-9]",input1.getText().toString()) && Pattern.matches(
                        "0[13578]/[0-2][0-9]/[0-9]{4}|0[13578]/3[0-1]/[0-9]{4}|" +
                        "1[02]/[0-2][0-9]/[0-9]{4}|1[02]/3[0-1]/[0-9]{4}|" +
                        "0[469]/[0-2][0-9]/[0-9]{4}|0[469]/30/[0-9]{4}|" +
                        "11/[0-2][0-9]/[0-9]{4}|11/30/[0-9]{4}|" +
                        "02/[0-1][0-9]/[0-9]{4}|02/2[0-8]/[0-9]{4}",input2.getText().toString())) {
                    int hour, minute, day, month, year;
                    Date currentTime = Calendar.getInstance().getTime();
                    Calendar calendar = GregorianCalendar.getInstance();
                    calendar.setTime(currentTime);
                    int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
                    int currentMinute = calendar.get(Calendar.MINUTE);
                    int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
                    int currentMonth = calendar.get(Calendar.MONTH);
                    int currentYear = calendar.get(Calendar.YEAR);
                    boolean hemisphereVal = hemisphere.isChecked();

                    String temp;
                    String[] tempArr;

                    temp = input1.getText().toString();
                    tempArr = temp.split(":");
                    hour = Integer.parseInt(tempArr[0]);
                    minute = Integer.parseInt(tempArr[1]);
                    /*editor.putInt("Hour", hour);
                    editor.putInt("Minute", minute);*/
                    editor.putInt("HourOff", hour - currentHour);
                    editor.putInt("MinuteOff", minute - currentMinute);

                    temp = input2.getText().toString();
                    tempArr = temp.split("/");
                    month = Integer.parseInt(tempArr[0]);
                    day = Integer.parseInt(tempArr[1]);
                    year = Integer.parseInt(tempArr[2]);
                    /*editor.putInt("Day", day);
                    editor.putInt("Month", month);
                    editor.putInt("Year", year);*/
                    editor.putInt("DayOff", day - currentDay);
                    editor.putInt("MonthOff", month - currentMonth);
                    editor.putInt("YearOff", year - currentYear);
                    editor.putBoolean("Hemisphere", hemisphereVal);

                    editor.commit();
                } else {

                }
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
                    islanderPostRepository.vFriendCode = "SW"+ "-" + mask1 + "-" + mask2 + "-" + mask3;
                    islanderPostRepository.vIsland = input3.getText().toString();
                    islanderPostRepository.vName = input1.getText().toString();
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

    private String getFriendCodeFormatted(String unformattedFriendCode){
        return "";
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
            islanderPostRepository.uri_image = encodeToBase64(bitmap);
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
        Log.d(TAG, "encodeToBase64: " + imageEncoded);

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
