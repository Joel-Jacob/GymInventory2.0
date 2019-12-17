package com.example.gyminventory20.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.gyminventory20.model.Inventory;

public class InventoryDatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "item.db";
    public static final String TABLE_NAME = "inventory";
    private static int DATABASE_VERSION = 1;

    public static final String COLUMN_ITEM_ID = "item_id";
    public static final String COLUMN_ITEM_TITLE = "item_title";
    public static final String COLUMN_ITEM_QUANTITY = "item_quantity";

    public InventoryDatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
        Log.d("TAG_X", "DB CONSTRUCTOR");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createStatement = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ITEM_ID + " INTEGER PRIMARY KEY, " +
                COLUMN_ITEM_TITLE + " TEXT, " +
                COLUMN_ITEM_QUANTITY + " INTEGER" +
                ")";

        Log.d("TAG_X", createStatement);

        db.execSQL(createStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("TAG_X", "onUpgrade");
        DATABASE_VERSION = newVersion;

        String updateQuery = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(updateQuery);

        onCreate(db);
    }

    public void insertItems(Inventory item){
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_ITEM_TITLE, item.getItemName());
        contentValues.put(COLUMN_ITEM_QUANTITY, item.getItemQuantity());

        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_NAME, null, contentValues);

        Log.d("TAG_X", "entered: " + item.getItemName() + " " +item.getItemQuantity());
    }

    public Cursor retreiveReceipts(){
        String selectStatement = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = getReadableDatabase();

        Cursor items = db.rawQuery(selectStatement, null);

        //db.close();

        return items;
    }
}
