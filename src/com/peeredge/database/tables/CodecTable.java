package com.peeredge.database.tables;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by root on 3/27/17.
 */

public class CodecTable {

    public static final String TABLE_NAME = "codec_table";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_PRIORITY_ID = "codec_priority";
    public static final String COLUMN_NAME = "codec_name";
    public static final String COLUMN_CODEC_PROVIDER_ID = "codec_provider_id";

    public static final String CREATE_TABLE = "create table if not exists  "
            + TABLE_NAME
            + " ( "
            + COLUMN_ID
            + " integer primary key autoincrement, "
            + COLUMN_NAME
            + " text not null, "
            + COLUMN_PRIORITY_ID
            + " integer not null, "
            + COLUMN_CODEC_PROVIDER_ID
            + " integer, "
            + " FOREIGN KEY ( "
            +  COLUMN_CODEC_PROVIDER_ID
            + " ) REFERENCES "
            + ProviderTable.TABLE_NAME
            + "("
            + ProviderTable.COLUMN_ID
            + ")" + ");";



//            CREATE TABLE track(
//            trackid     INTEGER,
//            trackname   TEXT,
//            trackartist INTEGER,
//            FOREIGN KEY(trackartist) REFERENCES artist(artistid)
//            );


    public static void onCreate(SQLiteDatabase database)
    {
        database.execSQL(CREATE_TABLE);
    }


}
