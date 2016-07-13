package com.lss.readcontactmassage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by shuai on 16-7-12.
 */
public class PersonSQLiteOpenHelper extends SQLiteOpenHelper {
    private static final String TAG = "PersonSQLiteOpenHelper";
    public PersonSQLiteOpenHelper(Context context) {
        super(context, "person.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table person (id integer primary key autoincrement,name varchar(20),number varchar(20))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i(TAG, "数据库版本变化了...");
    }
}
