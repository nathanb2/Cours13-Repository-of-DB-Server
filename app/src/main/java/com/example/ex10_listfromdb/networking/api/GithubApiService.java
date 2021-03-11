package com.example.ex10_listfromdb.networking.api;

import com.example.ex10_listfromdb.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GithubApiService {

    @GET("users")
    Call<List<User>> getGithubUsers();

}
