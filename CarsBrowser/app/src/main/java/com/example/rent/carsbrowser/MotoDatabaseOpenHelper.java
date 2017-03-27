package com.example.rent.carsbrowser;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by RENT on 2017-03-25.
 */

public class MotoDatabaseOpenHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "moto.db";
    private static int DATABASE_VERSION = 2;


    public MotoDatabaseOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private static String SQL_CREATE_TABLE = "CREATE TABLE "+ CarsTableContract.TABLE_NAME + " ("+
            CarsTableContract._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
            CarsTableContract.COLUMN_MAKE + " TEXT, "+
            CarsTableContract.COLUMN_MODEL + " TEXT, "+
            CarsTableContract.COLUMN_IMAGE + " TEXT, "+
            CarsTableContract.COLUMN_YEAR + " INT)";

    private static String SQL_DROP_TABLE = "DROP TABLE IF EXISTS " + CarsTableContract.TABLE_NAME;

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE);
    }

    public boolean insertCar(Car car) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CarsTableContract.COLUMN_MAKE, car.getMake());
        contentValues.put(CarsTableContract.COLUMN_MODEL, car.getModel());
        contentValues.put(CarsTableContract.COLUMN_IMAGE, car.getImage());
        contentValues.put(CarsTableContract.COLUMN_YEAR, car.getYear());

        long value = getWritableDatabase().insert(CarsTableContract.TABLE_NAME, null, contentValues);
        return value != -1;
    }

    public Cursor getAllItem(){
        Cursor cursor = getReadableDatabase().query(CarsTableContract.TABLE_NAME,
                new String[]
                {
                        CarsTableContract._ID,
                CarsTableContract.COLUMN_MAKE,
                CarsTableContract.COLUMN_MODEL,
                CarsTableContract.COLUMN_IMAGE,
                CarsTableContract.COLUMN_YEAR
        },
                null, null, null, null, null);

        return cursor;
    }

    public Cursor searchQuery(CharSequence constraint) {
        Cursor cursor = getReadableDatabase().query(CarsTableContract.TABLE_NAME,
                new String[]
                        {
                                CarsTableContract._ID,
                                CarsTableContract.COLUMN_MAKE,
                                CarsTableContract.COLUMN_MODEL,
                                CarsTableContract.COLUMN_IMAGE,
                                CarsTableContract.COLUMN_YEAR
                        }, CarsTableContract.COLUMN_MAKE + " LIKE ?", new String[]{
                        constraint.toString() + "%"
                }, null, null, null);

        return cursor;

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion>oldVersion) {
            db.execSQL(SQL_DROP_TABLE);
            onCreate(db);
        }

    }

}
