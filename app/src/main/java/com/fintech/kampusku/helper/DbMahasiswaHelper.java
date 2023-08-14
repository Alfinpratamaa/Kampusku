package com.fintech.kampusku.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbMahasiswaHelper extends SQLiteOpenHelper {
    public static final String dbName = "db_mahasiswa.db";
    public DbMahasiswaHelper(Context context){
        super(context, dbName, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table mahasiswa (npm TEXT PRIMARY KEY, nama TEXT , jenis_kelamin TEXT, tanggal_lahir DATE , alamat TEXT )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table if exists mahasiswa");

    }
    public Cursor getAllMahasiswaData() {
        SQLiteDatabase Mydatabase = this.getReadableDatabase();
        return Mydatabase.rawQuery("SELECT * FROM mahasiswa", null);
    }

    public long insertMahasiswaData(String npm, String nama, String jenisKelamin, String tanggalLahir, String alamat) {
        SQLiteDatabase Mydatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("npm", npm);
        contentValues.put("nama", nama);
        contentValues.put("jenis_kelamin", jenisKelamin);
        contentValues.put("tanggal_lahir", tanggalLahir);
        contentValues.put("alamat", alamat);
        return Mydatabase.insert("mahasiswa", null, contentValues);
    }

    public int deleteMahasiswaData(String npm) {
        SQLiteDatabase Mydatabase = this.getWritableDatabase();
        return Mydatabase.delete("mahasiswa", "npm = ?", new String[]{npm});
    }

    public Cursor getMahasiswaByNPM(String npm) {
        SQLiteDatabase Mydatabase = this.getReadableDatabase();
        return Mydatabase.rawQuery("SELECT * FROM mahasiswa WHERE npm=?", new String[]{npm});
    }



    public int updateMahasiswaData(String npm, String nama, String jenisKelamin, String tanggalLahir, String alamat) {
        SQLiteDatabase Mydatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("nama", nama);
        contentValues.put("jenis_kelamin", jenisKelamin);
        contentValues.put("tanggal_lahir", tanggalLahir);
        contentValues.put("alamat", alamat);
        return Mydatabase.update("mahasiswa", contentValues, "npm = ?", new String[]{npm});
    }
}
