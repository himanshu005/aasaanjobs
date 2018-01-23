package com.android.assignment.service.repository;

import com.android.assignment.service.model.City;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface AasaanJobsService {
    @GET("api/v4/city/")
    Call<City> getCity();

    @GET("api/v4/city/")
    Call<City> getNextCity( @Query("limit") Integer limit,
                            @Query("offset") Integer offset);
    @GET("api/v4/city/")
    Call<City> getPreviousCity( @Query("limit") Integer limit);
}
