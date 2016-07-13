package com.lss.readcontactmassage;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by shuai on 16-7-12.
 */
public class PersonDao {
    private PersonSQLiteOpenHelper helper;
    public PersonDao(Context context){
        helper=new PersonSQLiteOpenHelper(context);
    }
    public long add(String name,String number,int money){
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name",name);
        values.put("number",number);
        long id = db.insert("person", null, values);
        db.close();
        return id;
    }
}
