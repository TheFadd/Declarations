package com.example.myapplication.network;


import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {

    @GET("/v1/declaration/?q")
    Call<HumanList> getHumans();

}
