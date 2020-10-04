package com.agus.submission3.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class DatabaseHelper  extends SQLiteOpenHelper {
    public static String DATABASE_NAME = "dbFavUser";
    private static final int DATABASE_VERSION = 1;
    private static final String SQL_CREATE_TABLE_FAV_USER = String.format("CREATE TABLE %s"
                    + " (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s INTEGER NOT NULL," +
                    " %s INTEGER NOT NULL)",
            FavUserContract.TABLE_NAME,
            FavUserContract.FavUserColumns._ID,
            FavUserContract.FavUserColumns.USERNAME,
            FavUserContract.FavUserColumns.AVATAR_URL,
            FavUserContract.FavUserColumns.NAME,
            FavUserContract.FavUserColumns.COMPANY,
            FavUserContract.FavUserColumns.FOLLOWERS,
            FavUserContract.FavUserColumns.FOLLOWING
    );

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_FAV_USER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + FavUserContract.TABLE_NAME);
        onCreate(db);
    }
}
