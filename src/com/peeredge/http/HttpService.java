package com.peeredge.http;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.peeredge.database.DbQuery;
import com.peeredge.http.interfaces.ProviderList;
import com.peeredge.http.models.ProviderDbItem;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by root on 3/27/17.
 */

public class HttpService extends Service {

    private IBinder myBinder = new MyServiceBinder();
    private Retrofit mRetrofit;
    private ProviderList providerList;
    private DbQuery dbQuery;


    @Override
    public void onCreate() {
        super.onCreate();


        createRetrofite();
        providerList = mRetrofit.create(ProviderList.class);
        dbQuery = new DbQuery(this);

    }


    private void createRetrofite()
    {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                Request.Builder requestBuilder = original.newBuilder();
                requestBuilder.header("Content-Type", "application/json");
                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        });
        OkHttpClient client = httpClient.build();
         mRetrofit = new Retrofit.Builder()
                .baseUrl("http://162.209.77.132:9000/")
                .addConverterFactory(GsonConverterFactory.create())
                /*.client(client)*/
                .build();


    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return myBinder;
    }



    public class MyServiceBinder extends Binder
    {
        public HttpService getService()
        {
            return HttpService.this;
        }


    }

    public void getProviders()
    {
        Call<ArrayList<ProviderDbItem>> call = providerList.getAllProviders();

        Callback<ArrayList<ProviderDbItem>> callback = new Callback<ArrayList<ProviderDbItem>>() {
            @Override
            public void onResponse(Call<ArrayList<ProviderDbItem>> call, Response<ArrayList<ProviderDbItem>> response) {
                if(response.isSuccessful())
                {
                    for (ProviderDbItem item: response.body()
                         ) {
                        dbQuery.insertProviders(item);
                    }
                }
                Log.d("test","success");
            }

            @Override
            public void onFailure(Call<ArrayList<ProviderDbItem>> call, Throwable t) {
                Log.d("test","failed");
            }
        };

        call.enqueue(callback);
    }




}
