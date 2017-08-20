package com.example.sergey.counterpartycrud;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by sergey on 20.08.17.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "counterpartycrud_db";

    private static final String TABLE_COUNTERPARTIES = "counterparties";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_PHOTO = "photo";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_ADDRESS = "address";
    private static final String COLUMN_PHONE = "phone";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_WEBSITE = "website";
    private static final String COLUMN_DESCRIPTION = "description";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sqlQuery = "CREATE TABLE " + TABLE_COUNTERPARTIES + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_PHOTO + " TEXT," +
                COLUMN_NAME + " TEXT," +
                COLUMN_ADDRESS + " TEXT," +
                COLUMN_PHONE + " TEXT," +
                COLUMN_EMAIL + " TEXT," +
                COLUMN_WEBSITE + " TEXT," +
                COLUMN_DESCRIPTION + " TEXT)";
        sqLiteDatabase.execSQL(sqlQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        String sqlQuery = "DROP TABLE IF EXISTS " + TABLE_COUNTERPARTIES;
        sqLiteDatabase.execSQL(sqlQuery);
        onCreate(sqLiteDatabase);
    }

    public long addCounterparty(Counterparty counterparty) {
        long insertId = -1;
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_PHOTO, counterparty.getPhoto());
        contentValues.put(COLUMN_NAME, counterparty.getName());
        contentValues.put(COLUMN_ADDRESS, counterparty.getAddress());
        contentValues.put(COLUMN_PHONE, counterparty.getPhone());
        contentValues.put(COLUMN_EMAIL, counterparty.getEmail());
        contentValues.put(COLUMN_WEBSITE, counterparty.getWebsite());
        contentValues.put(COLUMN_DESCRIPTION, counterparty.getDescription());

        insertId = sqLiteDatabase.insert(TABLE_COUNTERPARTIES, null, contentValues);
        sqLiteDatabase.close();
        return insertId;
    }
}
