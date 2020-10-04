package com.agus.submission3.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.provider.BaseColumns._ID;
import static com.agus.submission3.DB.FavUserContract.FavUserColumns.USERNAME;
import static com.agus.submission3.DB.FavUserContract.TABLE_NAME;

public class FavUserHelper {
    private static final String DATABASE_TABLE = TABLE_NAME;
    private static DatabaseHelper dataBaseHelper;
    private static FavUserHelper INSTANCE;

    private static SQLiteDatabase database;

    private FavUserHelper(Context context) {
        dataBaseHelper = new DatabaseHelper(context);
    }

    public static FavUserHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new FavUserHelper(context);
                }
            }
        }
        return INSTANCE;
    }

    public void open() throws SQLException {
        database = dataBaseHelper.getWritableDatabase();
    }

    public void close() {
        dataBaseHelper.close();

        if (database.isOpen())
            database.close();
    }

    public Cursor queryAll() {
        return database.query(DATABASE_TABLE,
                null,
                null,
                null,
                null,
                null,
                _ID + " DESC");
    }

    public Cursor queryByUsername(String username) {
        return database.query(DATABASE_TABLE, null
                , USERNAME + " = ?"
                , new String[]{username}
                , null
                , null
                , null
                , null);
    }

    public long insert(ContentValues values) {
        return database.insert(DATABASE_TABLE, null, values);
    }

    public int deleteByUsername(String username) {
        return database.delete(DATABASE_TABLE, USERNAME + " = ?", new String[]{username});
    }
}
