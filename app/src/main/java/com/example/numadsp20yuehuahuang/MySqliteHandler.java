package com.example.numadsp20yuehuahuang;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class MySqliteHandler extends SQLiteOpenHelper {

    //database version
    private static final int DATABASE_VERSION=1;
    // db name
    private static final String DATABASE_NAME="links.db";
    // link table name
    private static final String TABLE_LINK = "links";

    //link table columns
    //private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_URL = "url";
    String CREATE_LINKS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_LINK + "("
             + KEY_NAME + " TEXT,"
            + KEY_URL + " TEXT" + ")";

    public MySqliteHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_LINKS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LINK);
        onCreate(db);
    }

    void addLink(LinkCollectorActivity.DataPair lp) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, lp.getName());
        values.put(KEY_URL, lp.getUrl());
        db.insert(TABLE_LINK, null, values);
        db.close();
    }

    ArrayList<LinkCollectorActivity.DataPair> getAllLinks() {
        ArrayList<LinkCollectorActivity.DataPair> list = new ArrayList<>();
        String selSQL = "SELECT name, url FROM " + TABLE_LINK;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selSQL, null);
        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(0);
                String url = cursor.getString(1);
                list.add(new LinkCollectorActivity.DataPair(name, url));
            } while (cursor.moveToNext());
        }
        db.close();
        cursor.close();
        return list;
    }
    public void clearAllLinks() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_LINK, null, null);
        db.close();
    }



}
