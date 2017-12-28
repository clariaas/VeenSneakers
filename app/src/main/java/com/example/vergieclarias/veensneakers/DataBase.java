package com.example.vergieclarias.veensneakers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by VergieClariias on 28/12/2017.
 */

public class DataBase extends SQLiteOpenHelper {

    static final private String Db_NAME = "VeenStation";
    static final private String ID = "_id";
    static final private int Db_VER = 6;

    ////deklarasi nama tabel
    static final private String TB_SEPATU = "Sepatu";//tabel sepatu
    static final private String CREATE_TB_SEPATU = "create table " + TB_SEPATU + "(_id integer primary key autoincrement,nama_sepatu text,harga_sepatu integer);";//tabel sepatu
    static final private String TB_TRANSAKSI = "Transaksi";//tabel transaksi
    static final private String CREATE_TB_TRANSAKSI = "create table " + TB_TRANSAKSI + "(_id integer primary key autoincrement,nama_pelanggan text,nama_sepatu text,ukuran integer,jumlah integer,harga integer,total integer);";//tabel transaksi
    static final private String TB_PESANAN = "Pesanan";//tabel pesanan
    static final private String CREATE_TB_PESANAN = "create table " + TB_PESANAN + "(_id integer primary key autoincrement,nama_pesanan text,ukuran integer,harga integer, total integer);";//tabel pesanan
    Context mycontext;
    SQLiteDatabase myDb;

    public DataBase(Context context) {
        super(context, Db_NAME, null, Db_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TB_SEPATU);
        db.execSQL(CREATE_TB_TRANSAKSI);
        db.execSQL(CREATE_TB_PESANAN);
        Log.i("Database", "Table Created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TB_SEPATU);
        db.execSQL("drop table if exists " + TB_TRANSAKSI);
        db.execSQL("drop table if exists " + TB_PESANAN);
        onCreate(db);
    }

    public void insertDatabarang(String p1, int p2) {
        myDb = getWritableDatabase();
        myDb.execSQL("insert into " + TB_SEPATU + " (nama_sepatu,harga_sepatu) values('" + p1 + "','" + p2 + "');");
    }

    public Cursor readAllbarang() {
        myDb = getWritableDatabase();
        String[] columns = new String[]{"_id", "nama_sepatu", "harga_sepatu"};
        Cursor c = myDb.query(TB_SEPATU, columns, null, null, null, null, ID + " asc");
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    public Cursor selectedbarang(long id) {
        myDb=getWritableDatabase();
        String[] columns = new String[]{"_id","nama_sepatu", "harga_sepatu"};
        Cursor c = myDb.query(TB_SEPATU, columns, ID + "=" + id, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    public void deletebarang(long id) {
        myDb=getWritableDatabase();
        myDb.delete(TB_SEPATU, ID + "=" + id, null);
        myDb.close();
    }

    public void updatebarang(long id, String p1,int p2) {
        myDb=getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nama_sepatu", p1);
        values.put("harga_sepatu",p2);
        myDb.update(TB_SEPATU, values, ID + "=" + id, null);
        close();
    }

    public void insertDatatransaksi(String p1, int p2,int p3,int p4) {
        myDb = getWritableDatabase();
        myDb.execSQL("insert into " + TB_PESANAN + " (nama_pesanan,ukuran ,harga, total) values('" + p1 + "','" + p2 + "','" + p3 + "','" + p4 + "');");
    }

    public Cursor readAlltransaksi() {
        myDb = getWritableDatabase();
        String[] columns = new String[]{"_id", "nama_pesanan","ukuran","harga","total"};
        Cursor c = myDb.query(TB_PESANAN, columns, null, null, null, null, ID + " asc");
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }
}
