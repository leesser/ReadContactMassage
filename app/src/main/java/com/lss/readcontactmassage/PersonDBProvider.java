package com.lss.readcontactmassage;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * Created by shuai on 16-7-12.
 */
public class PersonDBProvider extends ContentProvider {
    private static UriMatcher matcher=new UriMatcher(UriMatcher.NO_MATCH);
    private static final int INSERT=1;
    private static final int DELETE=2;
    private static final int UPDATE=3;
    private static final int QUERY=4;
    private static final int QUERYONE=5;

    private PersonSQLiteOpenHelper helper;

    static {
        matcher.addURI("com.lss.readcontactmassage.PersonDBProvider","insert",INSERT);
        matcher.addURI("com.lss.readcontactmassage.PersonDBProvider","delete",DELETE);
        matcher.addURI("com.lss.readcontactmassage.PersonDBProvider","update",UPDATE);
        matcher.addURI("com.lss.readcontactmassage.PersonDBProvider","query",QUERY);
        //#为通配符,所有符合"query/"的都返回queryone的返回码
        matcher.addURI("com.lss.readcontactmassage.PersonDBProvider","query/#",QUERYONE);
    }
    @Override
    public boolean onCreate() {
        helper=new PersonSQLiteOpenHelper(getContext());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        if (matcher.match(uri)==QUERY){
            SQLiteDatabase db = helper.getReadableDatabase();
            Cursor cursor = db.query("person", projection, selection, selectionArgs, null, null, sortOrder);
            return cursor;
        }else if (matcher.match(uri)==QUERYONE){
            long id = ContentUris.parseId(uri);
            SQLiteDatabase db = helper.getReadableDatabase();
            Cursor cursor = db.query("person", projection, "id=?", new String[]{id + ""}, null, null, sortOrder);
            return cursor;
        }else {
            throw new IllegalArgumentException("路径不匹配");
        }
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        if (matcher.match(uri)==QUERY){
            return "vnd.android.cursor.dir/person";
        }else if (matcher.match(uri)==QUERYONE){
            return "vnd.android.cursor.item/person";
        }
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        if (matcher.match(uri)==INSERT){
            SQLiteDatabase db = helper.getWritableDatabase();
            db.insert("person",null,values);
        }else {
            throw new IllegalArgumentException("路径不匹配");
        }
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int delete;
        if (matcher.match(uri)==DELETE){
            SQLiteDatabase db = helper.getWritableDatabase();
            delete = db.delete("person", selection, selectionArgs);
        }else {
            throw new IllegalArgumentException("路径不匹配");
        }
        return delete;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        //局部变量要有默认值,不然会报错,是因为，局部变量，都是存在栈中的。而栈往往是会快速重复的大量使用，如果每次使用都初始化，开销会比较大。不如，直接让程序员来手动初始化。
        int update;
        if(matcher.match(uri)==UPDATE){
            SQLiteDatabase db = helper.getWritableDatabase();
            update = db.update("person", values, selection, selectionArgs);
        }else {
            throw new IllegalArgumentException("路径不匹配");
        }
        return update;
    }
}
