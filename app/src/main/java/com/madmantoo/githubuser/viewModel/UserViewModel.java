package com.madmantoo.githubuser.viewModel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.madmantoo.githubuser.BuildConfig;
import com.madmantoo.githubuser.model.UserItems;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class UserViewModel extends ViewModel {
    private MutableLiveData<ArrayList<UserItems>> listUsers = new MutableLiveData<>();

    public void setUsers(final String users) {
        final ArrayList<UserItems> listItems = new ArrayList<>();

        String apiKey = BuildConfig.API_KEY;
        String url = "https://api.github.com/search/users?q=" + users;
        AsyncHttpClient client = new AsyncHttpClient();
        client.addHeader("Authorization", "token " + apiKey);
        client.addHeader("User-Agent", "request");
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("items");

                    for (int i = 0; i < list.length(); i++) {
                        JSONObject Users = list.getJSONObject(i);
                        UserItems userItems = new UserItems();
                        userItems.setId(Users.getInt("id"));
                        userItems.setUsername(Users.getString("login"));
                        userItems.setType(Users.getString("type"));
                        userItems.setImgPhoto(Users.getString("avatar_url"));
                        listItems.add(userItems);
                    }
                    listUsers.postValue(listItems);
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

    public LiveData<ArrayList<UserItems>> getUsers() {
        return listUsers;
    }
}
