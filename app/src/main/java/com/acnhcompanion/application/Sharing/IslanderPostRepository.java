package com.acnhcompanion.application.Sharing;

import android.util.Log;

import com.acnhcompanion.application.Networking.IslandersUtils;
import com.acnhcompanion.application.Networking.Status;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import okhttp3.RequestBody;

public class IslanderPostRepository implements IslandsAPI_POSTAsync.Callback {
    private static final String TAG = "IslanderPostRepo";
    private MutableLiveData<String> apiPostResponse;
    public String uri_image;
    public String vName;
    public String vIsland;
    public String vFriendCode;
    public String vMessage;

    public IslanderPostRepository(String vName, String vIsland, String vFriendCode, String vImage, String vMessage){
        apiPostResponse = new MutableLiveData<>();
        apiPostResponse.setValue("init");
        this.vName = vName;
        this.vIsland = vIsland;
        this.vFriendCode = vFriendCode;
        this.uri_image = vImage;
        this.vMessage = vMessage;
    }

    public LiveData<String> getStatus(){
        return apiPostResponse;
    }

    public void postIslanderData(){
        if(shouldExecutePost()){
            String url = IslandersUtils.buildIslandersURi();
            RequestBody requestBody = IslandersUtils.buildIslanderPOSTBody(vFriendCode, vIsland, vName, uri_image, vMessage);
            Log.d(TAG, "Posting Islanders: ");
            new IslandsAPI_POSTAsync(this, requestBody).execute(url);
        } else {
            Log.d(TAG, "loadIslanderSearch: " + "Don't need to execute");
        }
    }

    private boolean shouldExecutePost(){
        return true;
    }

    @Override
    public void onReturnedResult(String response) {
        this.apiPostResponse.setValue(response);
    }
}
