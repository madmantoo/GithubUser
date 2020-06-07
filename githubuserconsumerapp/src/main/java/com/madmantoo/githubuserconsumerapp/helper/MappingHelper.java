package com.madmantoo.githubuserconsumerapp.helper;

import android.database.Cursor;

import com.madmantoo.githubuserconsumerapp.db.DatabaseContract;
import com.madmantoo.githubuserconsumerapp.model.UserItems;

import java.util.ArrayList;

public class MappingHelper {
    public static ArrayList<UserItems> mapCursorToArrayList(Cursor favoritCursor) {
        ArrayList<UserItems> favList = new ArrayList<>();

        while (favoritCursor.moveToNext()) {
            int id = favoritCursor.getInt(favoritCursor.getColumnIndexOrThrow(DatabaseContract.UserColumns._ID));
            String username = favoritCursor.getString(favoritCursor.getColumnIndexOrThrow(DatabaseContract.UserColumns.USER_NAME));
            String name = favoritCursor.getString(favoritCursor.getColumnIndexOrThrow(DatabaseContract.UserColumns.NAME));
            String location = favoritCursor.getString(favoritCursor.getColumnIndexOrThrow(DatabaseContract.UserColumns.LOCATION));
            String avatar = favoritCursor.getString(favoritCursor.getColumnIndexOrThrow(DatabaseContract.UserColumns.AVATAR));
            String type = favoritCursor.getString(favoritCursor.getColumnIndexOrThrow(DatabaseContract.UserColumns.TYPE));
            favList.add(new UserItems(id, username, name, type, avatar, location));
        }

        return favList;
    }

    public static UserItems mapCursorToObject(Cursor userCursor) {
        userCursor.moveToFirst();
        int id = userCursor.getInt(userCursor.getColumnIndexOrThrow(DatabaseContract.UserColumns._ID));
        String username = userCursor.getString(userCursor.getColumnIndexOrThrow(DatabaseContract.UserColumns.USER_NAME));
        String name = userCursor.getString(userCursor.getColumnIndexOrThrow(DatabaseContract.UserColumns.NAME));
        String location = userCursor.getString(userCursor.getColumnIndexOrThrow(DatabaseContract.UserColumns.LOCATION));
        String avatar = userCursor.getString(userCursor.getColumnIndexOrThrow(DatabaseContract.UserColumns.AVATAR));
        String type = userCursor.getString(userCursor.getColumnIndexOrThrow(DatabaseContract.UserColumns.TYPE));

        return new UserItems(id, username, name, type, avatar, location);
    }

}
