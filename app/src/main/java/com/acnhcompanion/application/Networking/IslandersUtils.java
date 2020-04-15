package com.acnhcompanion.application.Networking;

import android.net.Uri;
import android.util.Log;

import com.acnhcompanion.application.Sharing.Islander;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.RequestBody;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class IslandersUtils {
    private final static String ISLANDERS_DB_BASE_URL = "http://76.115.157.57:2048/ACNHPassport/api.php/";
    private final static String ISLANDERS_DB_SEARCH = "/acnh_sharing";

    static class IslanderResults {
        IslanderItems[] results;
    }

    static class IslanderItems{
        String Friend_code;
        String Image;
        String Message;
        String Islander;
        String Island;
    }

    public static String buildIslandersURi(){
        return Uri.parse(ISLANDERS_DB_BASE_URL + ISLANDERS_DB_SEARCH).buildUpon().build().toString();
    }

    public static RequestBody buildIslanderPOSTBody(String Friend_code, String Island, String Islander, String Image, String Message){

        RequestBody formBody = new FormBody.Builder()
                .add("Island", "\"" + Island + "\"")
                .add("Islander", "\"" + Islander + "\"")
                .add("Friend_code", "\"" + Friend_code + "\"")
                .add("Message", "\"" + Message + "\"")
                .add("Image", "\"" + Image + "\"")
                .build();

        return formBody;
    }

    public static ArrayList<Islander> parseIslanders(String islandersJSON){
        Gson gson = new Gson();
        Log.d(TAG, "parseIslanders: " + islandersJSON);
        //islandersJSON = "{\"results\" : [{\"Friend_code\":\"SW-6078-5519-3791\",\"Image\":\"\",\"Message\":\"Testing!\",\"Islander\":\"Natalie\",\"Island\":\"Pineapples\"},{\"Friend_code\":\"SW-6078-5519-3791\",\"Image\":\"\",\"Message\":\"Testing!\",\"Islander\":\"Natalie\",\"Island\":\"Pineapples\"},{\"Friend_code\":\"SW-6078-5519-3791\",\"Image\":\"\",\"Message\":\"Testing!\",\"Islander\":\"Natalie\",\"Island\":\"Pineapples\"}]}";
        IslanderResults results = null;
        try{
            results = gson.fromJson(islandersJSON, IslanderResults.class);
        } catch(Exception e) {
            Log.e(TAG, "parseIslanders: " + "Exception " + e.toString());
        }
        if(results != null && results.results != null){
            ArrayList<Islander> islandersAL = new ArrayList<>();
            for(IslanderItems item : results.results){
                //String Friend_code, String Image, String Message, String Islander, String Island
                Islander islanderTemp = new Islander(item.Friend_code, item.Image, item.Message, item.Islander, item.Island);
                islandersAL.add(islanderTemp);
            }
            return islandersAL;
        }
        return null;
    }
}
