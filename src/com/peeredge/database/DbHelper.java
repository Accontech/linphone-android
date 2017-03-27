package com.peeredge.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.peeredge.database.tables.CodecTable;
import com.peeredge.database.tables.ProviderTable;

/**
 * Created by root on 3/27/17.
 */

public class DbHelper extends SQLiteOpenHelper {

    public static  String DATABASE_NAME = "peeredge.db";
    private static final int DATABASE_VERSION = 1;
    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        ProviderTable.onCreate(sqLiteDatabase);
        CodecTable.onCreate(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
