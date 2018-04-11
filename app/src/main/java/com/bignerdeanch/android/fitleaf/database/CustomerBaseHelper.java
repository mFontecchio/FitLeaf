package com.bignerdeanch.android.fitleaf.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.bignerdeanch.android.fitleaf.database.CustomerDBSchema.CustomerTable;

/**
 * Created by MFontecchio on 4/10/2018.
 */
public class CustomerBaseHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "customerBase.db";

    public CustomerBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table " + CustomerTable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                CustomerTable.Columns.UUID + ", " +
                CustomerTable.Columns.CUSTOMER + ", " +
                CustomerTable.Columns.SESSION + ", " +
                CustomerTable.Columns.PHOTO + ", " +
                CustomerTable.Columns.COMPLETED + ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
