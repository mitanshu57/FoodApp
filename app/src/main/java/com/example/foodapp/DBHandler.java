package com.example.foodapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Currency;

public class DBHandler extends SQLiteOpenHelper {

    final static String DBNAME = "mydatabase.db";
    final static int DBVERSION = 2;

    public DBHandler(@Nullable Context context) {
        super(context, DBNAME, null, DBVERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(
                "CREATE TABLE orders " +
                        "(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "name TEXT, " +
                        "price INTEGER, " +  // Added comma here
                        "image INTEGER, " +   // Added comma here
                        "quantity INTEGER, " +  // Added comma here
                        "description TEXT, " +
                        "foodname TEXT)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS orders");  // Corrected table name here
        onCreate(sqLiteDatabase);
    }

    public boolean insertOrder(int price, int image, String desc, String foodName) {
        SQLiteDatabase database = getWritableDatabase(); // Changed to getWritableDatabase()
        ContentValues values = new ContentValues();
        values.put("price", price);
        values.put("image", image);
        // You're missing "quantity" column, you may want to add it here if needed.
        values.put("description", desc);
        values.put("foodname", foodName);
        long id = database.insert("orders", null, values);
        return id != -1; // Simplified the return statement
    }

    public ArrayList<Order> orders=new ArrayList<>();
    SQLiteDatabase database=this.getWritableDatabase();
    Cursor cursor=database.rawQuery("SELECT * FROM orders",null);
//    if(cursor.moveToFirst()){
//        while (cursor.moveToNext()){
//            Order order=new Order();
//
//        }
//    }
}
