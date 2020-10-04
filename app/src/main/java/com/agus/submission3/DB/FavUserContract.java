package com.agus.submission3.DB;

import android.provider.BaseColumns;

public class FavUserContract {
    static String TABLE_NAME = "favorite_user";
    public static final class FavUserColumns implements BaseColumns {
        public static String USERNAME = "username";
        public static String NAME = "name";
        public static String AVATAR_URL = "avatar_url";
        public static String COMPANY = "company";
        public static String FOLLOWERS = "followers";
        public static String FOLLOWING = "following";
    }
}
