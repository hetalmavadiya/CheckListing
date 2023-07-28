package com.example.keepnotes.api;

import com.example.keepnotes.Model.FakeModel;
import com.example.keepnotes.Model.MainModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface ServiceApi {

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @GET("todos")
    Call<MainModel> getfakemodel();
}
