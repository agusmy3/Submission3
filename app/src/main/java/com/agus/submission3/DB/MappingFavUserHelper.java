package com.agus.submission3.DB;

import android.database.Cursor;

import com.agus.submission3.Entity.FavUser;

import java.util.ArrayList;

public class MappingFavUserHelper {
    public static ArrayList<FavUser> mapCursorToArrayList(Cursor FavUserCursor) {
        ArrayList<FavUser> favuserList = new ArrayList<>();
        while (FavUserCursor.moveToNext()) {
            int id = FavUserCursor.getInt(FavUserCursor.getColumnIndexOrThrow(FavUserContract.FavUserColumns._ID));
            String username = FavUserCursor.getString(FavUserCursor.getColumnIndexOrThrow(FavUserContract.FavUserColumns.USERNAME));
            String avatar_url = FavUserCursor.getString(FavUserCursor.getColumnIndexOrThrow(FavUserContract.FavUserColumns.AVATAR_URL));
            String name = FavUserCursor.getString(FavUserCursor.getColumnIndexOrThrow(FavUserContract.FavUserColumns.NAME));
            String company = FavUserCursor.getString(FavUserCursor.getColumnIndexOrThrow(FavUserContract.FavUserColumns.COMPANY));
            int followers = FavUserCursor.getInt(FavUserCursor.getColumnIndexOrThrow(FavUserContract.FavUserColumns.FOLLOWERS));
            int following = FavUserCursor.getInt(FavUserCursor.getColumnIndexOrThrow(FavUserContract.FavUserColumns.FOLLOWING));
            favuserList.add(new FavUser(id, username, name, avatar_url, company, followers, following));
        }
        return favuserList;
    }
}
