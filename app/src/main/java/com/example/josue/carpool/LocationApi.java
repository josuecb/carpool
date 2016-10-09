package com.example.josue.carpool;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;

/**
 * Created by Josue on 10/8/2016.
 */

public interface LocationApi {

    @GET("/schools")
    Call<Data> listSchools();

    @POST("/login") //
    Call<UserPost> sendRequest(@Body final UserCredentialsLogin data);

    @PUT("/update") // name email, phone
    Call<UserPost> updateRequest(@Body final UserProfileData data);


    @PUT("/update") // name email, phone
    Call<UserPost> updateRequest(@Body final UserProfileData2 data);


    @PUT("/update") // name email, phone
    Call<UserPost> updateRequest(@Body final UserProfileData3 data);

    @POST("/acceptride")
        // points // rider id
    Call<UserPost> acceptRideRequest(@Body final UserCredentialsLogin data);

    @GET("/allcarpools")
    Call<UserPost> getCarPoints(@Body final UserCredentialsLogin data);

    @POST("/signup") //
    Call<UserPost> registerRequest(@Body final UserRegistrationDetails details);
}
