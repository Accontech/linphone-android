package com.peeredge.http.interfaces;

import com.peeredge.http.models.ProviderDbItem;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by root on 3/27/17.
 */

public interface ProviderList {

   @GET("api/v1/provider/list")
   public Call<ArrayList<ProviderDbItem>> getAllProviders();

}
