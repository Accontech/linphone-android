package com.peeredge.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.peeredge.database.tables.CodecTable;
import com.peeredge.database.tables.ProviderTable;
import com.peeredge.http.models.Codec;
import com.peeredge.http.models.ProviderDbItem;

import java.util.ArrayList;

/**
 * Created by root on 3/27/17.
 */

public class DbQuery {


    private Context mContext;

    public DbQuery(Context mContext) {
        this.mContext = mContext;
    }


    public void insertProviders(ProviderDbItem dbItem)
    {
        DbHelper dbHelper = new DbHelper(mContext);
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(ProviderTable.COLUMN_PROVIDER_ID,dbItem.get_id());
        cv.put(ProviderTable.COLUMN_NAME,dbItem.getName());
        cv.put(ProviderTable.COLUMN_SIP_ADDRESS,dbItem.getSipServer());
        cv.put(ProviderTable.COLUMN_PROXY_ADDRESS,dbItem.getProxy());
        cv.put(ProviderTable.COLUMN_LOGO,dbItem.getLogo());

        long provider_id = database.insert(ProviderTable.TABLE_NAME,null,cv);

        ArrayList<Codec> codecs = dbItem.getCodecs();
        for (Codec codec:codecs
             ) {
            ContentValues values = new ContentValues();
            values.put(CodecTable.COLUMN_NAME,codec.getName());
            values.put(CodecTable.COLUMN_PRIORITY_ID,codec.getPriority());
            values.put(CodecTable.COLUMN_CODEC_PROVIDER_ID,(int)provider_id);
            long codec_id = database.insert(CodecTable.TABLE_NAME,null,values);
        }

        database.close();

//        cv.put(ProviderTable.COLUMN_CODEC_NAME,dbItem.get());
//        cv.put(ProviderTable.COLUMN_PROVIDER_ID,dbItem.get_id());

    }

    public void deleteProviders()
    {
        DbHelper dbHelper = new DbHelper(mContext);
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        database.delete(ProviderTable.TABLE_NAME,null,null);
        database.delete(CodecTable.TABLE_NAME,null,null);

        database.close();
    }


}
