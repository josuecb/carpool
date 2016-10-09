package com.example.josue.carpool;

import android.support.annotation.Nullable;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;

/**
 * Created by Josue on 10/8/2016.
 */

public class ApiClient implements Interceptor{
    public static LocationApi locationApi;

    static {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://52.39.145.69:1337")
                .client(getClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        locationApi = retrofit.create(LocationApi.class);

        locationApi.listSchools().enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                if (response.isSuccessful())
                    response.body();
            }

            @Override
            public void onFailure(Call<Data> call, Throwable t) {

            }
        });


    }

    private static OkHttpClient getClient() {
        return new OkHttpClient.Builder()
                .build();
    }

    @Override
    public okhttp3.Response intercept(Chain chain) throws IOException {
        return null;
    }
}
