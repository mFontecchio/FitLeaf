package com.bignerdeanch.android.fitleaf.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.bignerdeanch.android.fitleaf.database.SessionsDBSchema.SessionTable;

/**
 * Created by MFontecchio on 4/10/2018.
 */
public class SessionBaseHelper extends SQLiteOpenHelper{

    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "SessionBase.db";

    public SessionBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + SessionTable.NAME);

        db.execSQL("create table " + SessionTable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                SessionTable.Columns.UUID + ", " +
                SessionTable.Columns.CUSTOMER_ID + ", " +
                SessionTable.Columns.DATE + ", " +
                SessionTable.Columns.COMPLETE + ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
