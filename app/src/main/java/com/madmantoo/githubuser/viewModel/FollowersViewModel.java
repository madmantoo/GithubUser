package com.madmantoo.githubuser.viewModel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.madmantoo.githubuser.BuildConfig;
import com.madmantoo.githubuser.model.FollowerItems;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class FollowersViewModel extends ViewModel {

    private MutableLiveData<ArrayList<FollowerItems>> listFollowers = new MutableLiveData<>();

    public void setFollowers(final String username) {
        final ArrayList<FollowerItems> dataFollowers = new ArrayList<>();
        String apiKey = BuildConfig.API_KEY;

        String url = "https://api.github.com/users/" + username + "/followers";
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
                        FollowerItems followers = new FollowerItems();
                        followers.setIdFollower(Following.getInt("id"));
                        followers.setUsernameFollower(Following.getString("login"));
                        followers.setTypeFollower(Following.getString("type"));
                        followers.setAvatarFollower(Following.getString("avatar_url"));
                        dataFollowers.add(followers);
                    }
                    listFollowers.postValue(dataFollowers);
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

    public LiveData<ArrayList<FollowerItems>> getFollowers() {
        return listFollowers;
    }
}
