package com.agus.submission3.Rest;

import com.agus.submission3.Model.GetItemUser;
import com.agus.submission3.Model.GetSearchUser;
import com.agus.submission3.Model.GetSingleUser;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("search/users") Call<GetSearchUser> getUser(@Query("q") String username);
    @GET("users/{username}") Call<GetSingleUser> getDetailUser(@Path("username") String username);
    @GET("users/{username}/followers") Call<List<GetItemUser>> getFollowersUser(@Path("username") String username);
    @GET("users/{username}/following") Call<List<GetItemUser>> getFollowingUser(@Path("username") String username);
}
