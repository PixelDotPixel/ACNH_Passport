package com.acnhcompanion.application.Sharing;
import com.acnhcompanion.application.Networking.IslandersUtils;
import com.acnhcompanion.application.Networking.NetworkUtils;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import java.io.IOException;
import java.util.List;

import javax.security.auth.callback.Callback;

import okhttp3.RequestBody;

import static android.content.Context.MODE_PRIVATE;

public class IslandsAPI_POSTAsync extends AsyncTask<String,Void,String>{
    String TAG = "IslandsAPIAsync";
    private RequestBody requestBody;
    private Callback callback;

    public interface Callback {
        void onReturnedResult(String resultantString);
    }

    public IslandsAPI_POSTAsync(Callback callback, RequestBody requestBody){
        this.callback = callback;
        this.requestBody = requestBody;
    }

    @Override
    protected String doInBackground(String... strings) {
        String url = strings[0];
        String result = null;

        //(String Friend_code, String Island, String Islander, String Image, String Message)
        //RequestBody requestBody = IslandersUtils.buildIslanderPOSTBody(vFriendCode, vIsland, vName, uri_image, vMessage);
        try {
            result = NetworkUtils.doHttpPost(url, requestBody);
        } catch (IOException e){
            e.printStackTrace();
        }
        return result;
    }

    @Override
    protected void onPostExecute(String s){
        super.onPostExecute(s);
        List<Islander> islanders = null;
        callback.onReturnedResult(s);
    }
}
