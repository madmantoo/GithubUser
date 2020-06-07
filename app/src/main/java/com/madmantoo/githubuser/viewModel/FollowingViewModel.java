package com.madmantoo.githubuser.viewModel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.madmantoo.githubuser.BuildConfig;
import com.madmantoo.githubuser.model.FollowingItems;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class FollowingViewModel extends ViewModel {
    private MutableLiveData<ArrayList<FollowingItems>> listFollowing = new MutableLiveData<>();

    public void setFolowing(final String username) {
        final ArrayList<FollowingItems> dataFollowing = new ArrayList<>();

        String apiKey = BuildConfig.API_KEY;
        String url = "https://api.github.com/users/" + username + "/following";
        AsyncHttpClient client = new AsyncHttpClient();
        client.addHeader("Authorization", "token " + apiKey);
        client.addHeader("User-Agent", "request");
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONArray responseObject = new JSONArray(result);

                    for (int i = 0; i < responseObject.length(); i++) {
                        JSONObject Following = responseObject.getJSONObject(i);
                        FollowingItems following = new FollowingItems();
                        following.setIdFollowing(Following.getInt("id"));
                        following.setUsernameFollowing(Following.getString("login"));
                        following.setTypeFollowing(Following.getString("type"));
                        following.setAvatarFollowing(Following.getString("avatar_url"));
                        dataFollowing.add(following);
                    }
                    listFollowing.postValue(dataFollowing);
                } catch (Exception e) {
                    Log.d("Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailure", error.getMessage());
            }
        });
    }

    public LiveData<ArrayList<FollowingItems>> getFollowing() {
        return listFollowing;
    }
}
