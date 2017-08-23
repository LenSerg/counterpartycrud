package com.example.sergey.counterpartycrud.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.sergey.counterpartycrud.entities.Counterparty;

import java.util.ArrayList;

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

    public Counterparty getCounterparty(int counterpartyId) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        Cursor cursor = sqLiteDatabase.query(TABLE_COUNTERPARTIES,
                new String[] {COLUMN_ID, COLUMN_PHOTO, COLUMN_NAME, COLUMN_ADDRESS, COLUMN_PHONE, COLUMN_EMAIL, COLUMN_WEBSITE, COLUMN_DESCRIPTION},
                COLUMN_ID + " = ? ", new String[] {String.valueOf(counterpartyId)}, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        Counterparty counterparty = buildCounterparty(cursor);

        cursor.close();
        sqLiteDatabase.close();

        return counterparty;
    }

    public ArrayList<Counterparty> getCounterpartyList() {
        ArrayList<Counterparty> counterpartyList = new ArrayList<>();

        String sqlQuery = "SELECT * FROM " + TABLE_COUNTERPARTIES;

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(sqlQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Counterparty counterparty = buildCounterparty(cursor);
                counterpartyList.add(counterparty);
            } while (cursor.moveToNext());

            cursor.close();
            sqLiteDatabase.close();
        }

        return counterpartyList;
    }

    public long updateCounterparty(Counterparty counterparty) {
        long updatedRow = -1;

        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PHOTO, counterparty.getPhoto());
        values.put(COLUMN_NAME, counterparty.getName());
        values.put(COLUMN_ADDRESS, counterparty.getAddress());
        values.put(COLUMN_PHONE, counterparty.getPhone());
        values.put(COLUMN_EMAIL, counterparty.getEmail());
        values.put(COLUMN_WEBSITE, counterparty.getWebsite());
        values.put(COLUMN_DESCRIPTION, counterparty.getWebsite());

        updatedRow = database.update(TABLE_COUNTERPARTIES, values, COLUMN_ID + " = ?",
                new String[] {String.valueOf(counterparty.getId())});

        return updatedRow;
    }

    public int deleteCounterparty(int counterpartyId) {
        int deletedRow = -1;

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        deletedRow = sqLiteDatabase.delete(TABLE_COUNTERPARTIES, COLUMN_ID + " = ? ",
                new String[]{String.valueOf(counterpartyId)});
        sqLiteDatabase.close();

        return deletedRow;
    }

    private Counterparty buildCounterparty(Cursor cursor) {
        return new Counterparty(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_ID))),
                cursor.getString(cursor.getColumnIndex(COLUMN_PHOTO)),
                cursor.getString(cursor.getColumnIndex(COLUMN_NAME)),
                cursor.getString(cursor.getColumnIndex(COLUMN_ADDRESS)),
                cursor.getString(cursor.getColumnIndex(COLUMN_PHONE)),
                cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL)),
                cursor.getString(cursor.getColumnIndex(COLUMN_WEBSITE)),
                cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION)));
    }
}
