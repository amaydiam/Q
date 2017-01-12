package com.ad.sample.api.client.searchlocation;

import com.ad.sample.api.model.ListLocation;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SearchLocationInterface {

    @GET("/maps/api/place/autocomplete/json")
    Call<ListLocation> getInput(@Query("input") String input, @Query("types") String type, @Query("language") String language, @Query("key") String key);

}