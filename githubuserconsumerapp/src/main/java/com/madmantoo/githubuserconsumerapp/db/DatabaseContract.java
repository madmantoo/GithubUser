package com.madmantoo.githubuserconsumerapp.db;

import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {

    public static final String AUTHORITY = "com.madmantoo.githubuser";
    private static final String SCHEME = "content";

    private DatabaseContract() {

    }

    public static String TABLE_NAME = "user";

    public static final class UserColumns implements BaseColumns {
        public static String USER_NAME = "username";
        public static String NAME = "name";
        public static String TYPE = "type";
        public static String AVATAR = "avatar";
        public static String LOCATION = "location";

        public static final Uri CONTENT_URI = new Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build();
    }
}
