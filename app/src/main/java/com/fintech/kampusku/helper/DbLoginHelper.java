package com.fintech.kampusku.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbLoginHelper extends SQLiteOpenHelper {
    private static final String DABASE_NAME = "Signup.db";
    public DbLoginHelper(@Nullable Context context) {
        super(context, DABASE_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase myDatabase) {
        myDatabase.execSQL("create table allusers(username text primary key,password text)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase myDatabase, int i, int i1) {
        myDatabase.execSQL("drop table if exists allusers");
    }
    public boolean insertDataUsers(String username, String password){
        SQLiteDatabase Mydatabase = this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put("username",username);
        contentValues.put("password",password);
        long result = Mydatabase.insert("allusers",null,contentValues);
        if (result == -1){
            return false;
        }else {
            return true;
        }
    }

    public boolean checkUser(String user){
        SQLiteDatabase Mydatabase = this.getWritableDatabase();
        Cursor cursor = Mydatabase.rawQuery("select * from allusers where username = ?",new String[]{user});

        if(cursor.getCount() > 0){
            return true;
        }else {
            return false;
        }
    }
    public boolean checkUsernamePassword(String user,String password){
        SQLiteDatabase Mydatabase = this.getWritableDatabase();
        Cursor cursor = Mydatabase.rawQuery("select * from allusers where username = ? and password =?",new String[]{user,password});

        if (cursor.getCount() > 0){
            return true;
        }else {
            return false;
        }
    }



}
