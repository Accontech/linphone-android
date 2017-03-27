package com.peeredge.database.tables;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by root on 3/27/17.
 */

public class Provider {

    public static final String TABLE_NAME = "provider_table";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_PROVIDER_ID = "provider_id";
    public static final String COLUMN_NAME = "provider_name";
    public static final String COLUMN_SIP_ADDRESS = "provider_sip_address";
    public static final String COLUMN_PROXY_ADDRESS = "provider_proxy_address";
    public static final String COLUMN_LOGO = "provider_logo";
    public static final String COLUMN_CODEC_NAME = "provider_codec_name";
    public static final String COLUMN_CODEC_PRIORITY = "provider_codec_priority";

    public static final String CREATE_TABLE = "create table if not exists  "
            + TABLE_NAME
            + " ( "
            + COLUMN_ID
            + " integer primary key autoincrement, "
            + COLUMN_PROVIDER_ID
            + " text not null, "
            + COLUMN_NAME
            + " text not null, "
            + COLUMN_SIP_ADDRESS
            + " text not null, "
            + COLUMN_PROXY_ADDRESS
            + " text not null, "
            + COLUMN_LOGO
            + " text not null, "
            + COLUMN_CODEC_NAME
            + " text not null, "
            + COLUMN_CODEC_PRIORITY
            + " integer not null"
            + " ); ";

    public static void onCreate(SQLiteDatabase database)
    {
        database.execSQL(CREATE_TABLE);
    }


}
