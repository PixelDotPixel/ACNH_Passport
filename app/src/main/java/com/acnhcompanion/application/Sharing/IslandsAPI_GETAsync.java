package com.acnhcompanion.application.Sharing;
import com.acnhcompanion.application.Networking.IslandersUtils;
import com.acnhcompanion.application.Networking.NetworkUtils;

import android.os.AsyncTask;
import android.util.Log;
import java.io.IOException;
import java.util.List;

import javax.security.auth.callback.Callback;

public class IslandsAPI_GETAsync extends AsyncTask<String,Void,String>{
    String TAG = "IslandsAPIAsync";
    private Callback callback;

    public interface Callback {
        void onReturnedResult(List<Islander> islanders);
    }

    public IslandsAPI_GETAsync(Callback callback){
        this.callback = callback;
    }

    @Override
    protected String doInBackground(String... strings) {
        String url = strings[0];
        String result = null;
        try {
            result = NetworkUtils.doHttpGet(url);
        } catch (IOException e){
            e.printStackTrace();
        }
        return result;
    }

    @Override
    protected void onPostExecute(String s){
        super.onPostExecute(s);
        List<Islander> islanders = null;
        if(s != null){
            islanders = IslandersUtils.parseIslanders(s);
        }
        callback.onReturnedResult(islanders);
    }
}
